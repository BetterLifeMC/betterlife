package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;

public class TRAIL extends BetterLifeCommands implements CommandExecutor {
	Main m;

	public TRAIL(Main m, String permission, CommandSender cs, Command c, String label, String[] args) {
		super(m, permission, cs, c, label, args);

		this.m = m;
		this.onCommand(cs, c, label, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		List<String> allowedParticles = (List<String>) m.getMainConfiguration().getCustomConfig().getList("enabledParticles");
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (c.getName().equalsIgnoreCase("trail")) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("list")) {
						sendListTrails(player, allowedParticles);
					} else if (args[0].equalsIgnoreCase("set")) {
						if (args.length > 1) {
							System.out.println(args[1]);
							if (allowedParticles.contains(args[1].toUpperCase()) && (player.hasPermission("betterlife.command.trail.particle." + args[1].toLowerCase()) || player.hasPermission("betterlife.command.trail.particle.*"))) {
								m.getPlayerConfiguration().getCustomConfig().set("player." + player.getUniqueId().toString() + ".trail", args[1].toUpperCase());
								m.getPlayerConfiguration().saveCustomConfig();
								sendBannerMessage(player, ChatColor.GRAY + "Your trail is now set to: " + ChatColor.GOLD + args[1]);
								return true;
							} else if (!player.hasPermission("betterlife.command.trail.particle." + args[1].toLowerCase())) {
								sendBannerMessage(player, ChatColor.DARK_RED + "You do not have permission!");
								return true;
							} else {
								sendBannerMessage(player, ChatColor.DARK_RED + "Sorry, you can't do that!");
								sendListTrails(player, allowedParticles);
								return true;
							}

						} else {
							sendHelp(player);
							return true;
						}
					} else if (args[0].equalsIgnoreCase("add") && player.hasPermission("betterlife.command.trail.admin")) {
						if (allowedParticles.contains(args[1])) {
							sendBannerMessage(player, ChatColor.DARK_RED + "This effect is already enabled!");
							return true;
						} else {
							try {
								if (Particle.valueOf(args[1]) != null) {
									allowedParticles.add(args[1]);
									m.getMainConfiguration().getCustomConfig().set("trail.enabledParticles", allowedParticles);
									m.getMainConfiguration().saveCustomConfig();
									sendBannerMessage(player, ChatColor.GRAY + "Effect " + ChatColor.GOLD + args + ChatColor.GRAY + " has been " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + "!");
									return true;
								}
							} catch (IllegalArgumentException e) {
								sendBannerMessage(player, ChatColor.DARK_RED + "Invalid particle effect!");
								return true;
							}
						}
					} else if (args[0].equalsIgnoreCase("rm") && player.hasPermission("betterlife.command.trail.admin")) {
						if (allowedParticles.contains(args[1])) {
							try {
								if (Particle.valueOf(args[1]) != null) {
									allowedParticles.remove(args[1]);
									m.getMainConfiguration().getCustomConfig().set("trail.enabledParticles", allowedParticles);
									m.getMainConfiguration().saveCustomConfig();
									sendBannerMessage(player, ChatColor.GRAY + "Effect " + ChatColor.GOLD + args + ChatColor.GRAY + " has been " + ChatColor.RED + "disabled" + ChatColor.GRAY + "!");
									return true;
								}
							} catch (IllegalArgumentException e) {
								sendBannerMessage(player, ChatColor.DARK_RED + "Invalid particle effect!");
								return true;
							}
						} else {
							sendBannerMessage(player, ChatColor.DARK_RED + "This effect is already disabled!");
							return true;
						}
					} else if (args[0].equalsIgnoreCase("toggle")  && player.hasPermission("betterlife.command.trail.toggle")) {
						boolean trailsEnabled = m.getPlayerConfiguration().getCustomConfig()
								.getBoolean("player." + player.getUniqueId().toString() + ".trails.enabled");
						m.getPlayerConfiguration().getCustomConfig()
								.set("player." + player.getUniqueId().toString() + ".trails.enabled", !trailsEnabled);
						if (!trailsEnabled)
							sendBannerMessage(player, ChatColor.GRAY + "Trails " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + "!");
						else
							sendBannerMessage(player, ChatColor.GRAY + "Trails " + ChatColor.RED + "disabled" + ChatColor.GRAY + "!");
						m.getPlayerConfiguration().saveCustomConfig();
					} else {
						sendHelp(player);
						return true;
					}
				} else {
					invalidMessage(player);
					return true;
				}
			}
		} else {
			sendBannerMessage(sender, "You must be a player to use this command!");
		}
		return true;
	}

	private void sendListTrails(Player player, List<String> allowedParticles) {
		sendBannerMessage(player, ChatColor.GRAY + "The currently enabled trails are: ");

		for (int i = 0; i < allowedParticles.size(); i++) {
			if (player.hasPermission("betterlife.trails.particle." + allowedParticles.get(i).toLowerCase())) {
				player.sendMessage(ChatColor.LIGHT_PURPLE + allowedParticles.get(i));
			}
		}
	}

	private void sendHelp(CommandSender sender) {
		LinkedHashMap<String, String> helpHash = new LinkedHashMap<>();
		helpHash.put("set <trail>", "Sets the current player's trail");
		helpHash.put("add <trail>", "Adds the trail to the config");
		helpHash.put("rm <trail>", "Removes the trail from the config");
		helpHash.put("list", "Lists the enabled trails");
		helpHash.put("toggle", "Toggles your trail");

		sendHelpMessage(sender, "trail", helpHash);
	}

	private void invalidMessage(CommandSender sender) {
		sendInvalidMessage(sender, getClass().getSimpleName());

	}
}
