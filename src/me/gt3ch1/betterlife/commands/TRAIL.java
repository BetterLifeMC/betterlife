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

	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (c.getName().equalsIgnoreCase("trail")) {
				List<String> allowedParticles = (List<String>) m.getMainConfiguration().getCustomConfig().getList("enabledParticles");
				switch(args.length) {
					case 0:
						sendHelp(player);
						return true;
					case 1:
						switch(args[0]) {
							case "list":
								sendListTrails(player, allowedParticles);
								return true;
							case "toggle":
								if (player.hasPermission("betterlife.command.trail.toggle")) {
									boolean trailsEnabled = m.getPlayerConfiguration().getCustomConfig().getBoolean("player." + player.getUniqueId().toString() + ".trails.enabled");
									m.getPlayerConfiguration().getCustomConfig().set("player." + player.getUniqueId().toString() + ".trails.enabled", !trailsEnabled);
									if (!trailsEnabled)
										sendBannerMessage(player, ChatColor.GRAY + "Trails " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + "!");
									else
										sendBannerMessage(player, ChatColor.GRAY + "Trails " + ChatColor.RED + "disabled" + ChatColor.GRAY + "!");
									m.getPlayerConfiguration().saveCustomConfig();
									return true;
								}
							case "help":
								sendHelp(player);
								return true;
							default:
								sendBannerMessage(player, "That's not a valid command. Try /trail help.");
								return true;
						}
					case 2:
						switch(args[0]) {
							case "set":
								if (allowedParticles.contains(args[1].toUpperCase()) && (player.hasPermission("betterlife.command.trail.particle." + args[1].toLowerCase()) || player.hasPermission("betterlife.command.trail.particle.*"))) {
									m.getPlayerConfiguration().getCustomConfig().set("player." + player.getUniqueId().toString() + ".trail", args[1].toUpperCase());
									m.getPlayerConfiguration().saveCustomConfig();
									sendBannerMessage(player, ChatColor.GRAY + "Your trail is now set to: " + ChatColor.GOLD + args[1].toUpperCase());
									return true;
								} else if (!player.hasPermission("betterlife.command.trail.particle." + args[1].toLowerCase())) {
									sendBannerMessage(player, ChatColor.DARK_RED + "You need permission to use that trail!");
									return true;
								} else if (!allowedParticles.contains(args[1].toUpperCase())) {
									sendBannerMessage(player, ChatColor.DARK_RED + "That particle is disabled on this server! Try /trail list to see the list of available trails.");
									return true;
								}
							case "add":
								if (player.hasPermission("betterlife.command.trail.admin")) {
									if (allowedParticles.contains(args[1].toUpperCase())) {
										sendBannerMessage(player, ChatColor.DARK_RED + "This effect is already enabled!");
										return true;
									} else {
										try {
											if (Particle.valueOf(args[1].toUpperCase()) != null) {
												allowedParticles.add(args[1].toUpperCase());
												m.getMainConfiguration().getCustomConfig().set("trail.enabledParticles", allowedParticles);
												m.getMainConfiguration().saveCustomConfig();
												sendBannerMessage(player, ChatColor.GRAY + "Effect " + ChatColor.GOLD + args[1].toUpperCase() + ChatColor.GRAY + " has been " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + "!");
												return true;
											}
										} catch (IllegalArgumentException e) {
											sendBannerMessage(player, ChatColor.DARK_RED + "Invalid particle effect!");
											return true;
										}
									}
								}
							case "rm":
								if (player.hasPermission("betterlife.command.trail.admin")) {
									if (!allowedParticles.contains(args[1].toUpperCase())) {
										sendBannerMessage(player, ChatColor.DARK_RED + "This effect is already disabled!");
										return true;
									} else {
										try {
											if (Particle.valueOf(args[1].toUpperCase()) != null) {
												allowedParticles.remove(args[1].toUpperCase());
												m.getMainConfiguration().getCustomConfig().set("trail.enabledParticles", allowedParticles);
												m.getMainConfiguration().saveCustomConfig();
												sendBannerMessage(player, ChatColor.GRAY + "Effect " + ChatColor.GOLD + args[1].toUpperCase() + ChatColor.GRAY + " has been " + ChatColor.RED + "disabled" + ChatColor.GRAY + "!");
												return true;
											}
										} catch (IllegalArgumentException e) {
											sendBannerMessage(player, ChatColor.DARK_RED + "Invalid particle effect!");
											return true;
										}
									}
								}
							default:
								sendBannerMessage(player, "That's not a valid command! Try /trail help.");
								return true;
						}
					default:
						sendBannerMessage(player, "Too many arguments! Try /trail help.");
						return true;
					}
				}
			} else {
			sendBannerMessage(sender, "You must be a player to use this command!");
			return true;
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
