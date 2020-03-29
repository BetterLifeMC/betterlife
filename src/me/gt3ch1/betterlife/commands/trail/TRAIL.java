package me.gt3ch1.betterlife.commands.trail;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commands.BetterLifeCommands;
import me.gt3ch1.betterlife.commands.CommandTemplate;

public class TRAIL extends BetterLifeCommands implements CommandExecutor {
	Main m;

	public TRAIL(Main m, String permission, CommandSender cs, Command c, String label, String[] args) {
		super(m, permission, cs, c, label, args);

		this.m = m;
		this.onCommand(cs, c, label, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		List<String> allowedParticles = (List<String>) m.getMainConfiguration().getCustomConfig()
				.getList("enabledParticles");
		Player player = (Player) sender;
		if (c.getName().equalsIgnoreCase("trail")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("list")) {
					sendListTrails(player, allowedParticles);
				} else if (args[0].equalsIgnoreCase("set")) {
					if (args.length > 1) {
						System.out.println(args[1]);
						if (allowedParticles.contains(args[1].toUpperCase())
								&& (player.hasPermission("betterlife.trails.particle." + args[1].toLowerCase())
										|| player.hasPermission("betterlife.trails.particle.*"))) {
							m.getPlayerConfiguration().getCustomConfig()
									.set("player." + player.getUniqueId().toString() + ".trail", args[1].toUpperCase());
							m.getPlayerConfiguration().saveCustomConfig();
							CommandTemplate.sendMessageToPlayer(player,
									"Your trail is now set to: " + ChatColor.GOLD + args[1], ChatColor.YELLOW);
							return true;
						} else if (!player.hasPermission("betterlife.trails.particle." + args[1].toLowerCase())) {
							CommandTemplate.sendMessageToPlayer(player, "You do not have permission!",
									ChatColor.DARK_RED);
							return true;
						} else {
							CommandTemplate.sendMessageToPlayer(player, "Sorry, you can't do that!", ChatColor.RED);
							sendListTrails(player, allowedParticles);
							return true;
						}

					} else {
						sendHelpMessage(player);
						return true;
					}
				} else if (args[0].equalsIgnoreCase("add") && player.hasPermission("betterlife.trails.admin")) {
					if (allowedParticles.contains(args[1])) {
						CommandTemplate.sendMessageToPlayer(player, "This effect is already enabled!",
								ChatColor.DARK_RED);
						return true;
					} else {
						try {
							if (Particle.valueOf(args[1]) != null) {
								allowedParticles.add(args[1]);
								m.getMainConfiguration().getCustomConfig().set("trail.enabledParticles",
										allowedParticles);
								m.saveConfig();
								CommandTemplate.sendMessageToPlayer(player, "Effect " + ChatColor.GOLD + args
										+ ChatColor.LIGHT_PURPLE + " has been enabled!", ChatColor.LIGHT_PURPLE);
								return true;
							}
						} catch (IllegalArgumentException e) {
							CommandTemplate.sendMessageToPlayer(player, "Invalid particle effect!", ChatColor.RED);
							return true;
						}
					}
				} else if (args[0].equalsIgnoreCase("rm") && player.hasPermission("betterlife.trails.admin")) {
					if (allowedParticles.contains(args[1])) {
						try {
							if (Particle.valueOf(args[1]) != null) {
								allowedParticles.remove(args[1]);
								m.getMainConfiguration().getCustomConfig().set("trail.enabledParticles",
										allowedParticles);
								m.saveConfig();
								CommandTemplate.sendMessageToPlayer(player, "Effect " + ChatColor.GOLD + args
										+ ChatColor.LIGHT_PURPLE + " has been disabled!", ChatColor.LIGHT_PURPLE);
								return true;
							}
						} catch (IllegalArgumentException e) {
							CommandTemplate.sendMessageToPlayer(player, "Invalid particle effect!", ChatColor.RED);
							return true;
						}
					} else {
						CommandTemplate.sendMessageToPlayer(player, "This effect is already disabled!",
								ChatColor.DARK_RED);
						return true;
					}
				} else {
					sendHelpMessage(player);
					return true;
				}
			} else {
				invalidMessage(player);
				return true;
			}
		}
		return true;
	}

	private void sendListTrails(Player player, List<String> allowedParticles) {
		CommandTemplate.sendMessageToPlayer(player, "The currently enabled trails are: ", ChatColor.RED);

		for (int i = 0; i < allowedParticles.size(); i++) {
			if (player.hasPermission("betterlife.trails.particle." + allowedParticles.get(i).toLowerCase())) {
				player.sendMessage(ChatColor.LIGHT_PURPLE + allowedParticles.get(i));
			}
		}
	}

	private void sendHelpMessage(Player player) {
		CommandTemplate.sendMessageToPlayer(player, "by GT3CH1", ChatColor.LIGHT_PURPLE);
		CommandTemplate.sendMessageToPlayer(player, "--== [ Available commands ] ==--", ChatColor.GOLD);
		CommandTemplate.sendMessageToPlayer(player, "/trail set <trail>", "Set's the current players trail",
				ChatColor.LIGHT_PURPLE, ChatColor.YELLOW);
		CommandTemplate.sendMessageToPlayer(player, "/trail add <trail>", "Adds the trail to the config",
				ChatColor.LIGHT_PURPLE, ChatColor.YELLOW);
		CommandTemplate.sendMessageToPlayer(player, "/trail rm <trail>", "Removes the trail from the config",
				ChatColor.LIGHT_PURPLE, ChatColor.YELLOW);
		CommandTemplate.sendMessageToPlayer(player, "/trail list", "List the enabled trails", ChatColor.LIGHT_PURPLE,
				ChatColor.YELLOW);
		CommandTemplate.sendMessageToPlayer(player, "/trail rl", "Reloads the configuration file",
				ChatColor.LIGHT_PURPLE, ChatColor.YELLOW);
	}

	static private void invalidMessage(Player pl) {
		CommandTemplate.sendMessageToPlayer(pl, "Invalid usage! Try /trail help", ChatColor.DARK_RED);

	}
}
