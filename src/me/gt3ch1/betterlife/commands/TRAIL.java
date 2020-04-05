package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.commandhelpers.HelpHelper;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.List;

public class TRAIL extends BetterLifeCommands implements CommandExecutor {

	public TRAIL(String permission, CommandSender cs, Command c, String label, String[] args) {
		super(permission, cs, c, label, args);
		this.onCommand(cs, c, label, args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		LinkedHashMap<String, String> helpHash = HelpHelper.getAHelpHash("trail");
		if (sender instanceof Player) {
			Player player = (Player) sender;
			List<String> allowedParticles = (List<String>) CommandUtils.getMainConfiguration().getCustomConfig().getList("trail.enabledParticles");
			switch(args.length) {
				case 0:
					sendHelpMessage(player, "trail", helpHash);
					return true;
				case 1:
					switch(args[0]) {
						case "list":
							sendListTrails(player, allowedParticles);
							return true;
						case "toggle":
							if (player.hasPermission("betterlife.trail.toggle")) {
								boolean trailsEnabled = CommandUtils.getPlayerConfiguration().getCustomConfig().getBoolean("player." + player.getUniqueId().toString() + ".trails.enabled");
								CommandUtils.getPlayerConfiguration().getCustomConfig().set("player." + player.getUniqueId().toString() + ".trails.enabled", !trailsEnabled);
								String toggleState = trailsEnabled ? "&cdisabled" : "&aenabled";
								CommandUtils.getPlayerConfiguration().saveCustomConfig();
								sendBannerMessage(player, "&7Trail " + toggleState + "&7!");
								return true;
							}
						case "help":
							sendHelpMessage(player, "trail", helpHash);
							return true;
						default:
							sendBannerMessage(player, "&4That's not a valid command. Try /trail help.");
							return true;
					}
				case 2:
					switch(args[0]) {
						case "set":
							if (allowedParticles.contains(args[1].toUpperCase()) && (player.hasPermission("betterlife.trail.particle." + args[1].toLowerCase()) || player.hasPermission("betterlife.trail.particle.*"))) {
								CommandUtils.getPlayerConfiguration().getCustomConfig().set("player." + player.getUniqueId().toString() + ".trail", args[1].toUpperCase());
								CommandUtils.getPlayerConfiguration().saveCustomConfig();
								sendBannerMessage(player, "&7Your trail is now set to: &6" + args[1].toUpperCase());
								return true;
							} else if (!player.hasPermission("betterlife.trail.particle." + args[1].toLowerCase())) {
								sendBannerMessage(player, "&4You need permission to use that trail!");
								return true;
							} else if (!allowedParticles.contains(args[1].toUpperCase())) {
								sendBannerMessage(player, "&4That particle is disabled on this server! Try /trail list to see the list of available trails.");
								return true;
							}
						case "add":
							if (player.hasPermission("betterlife.trail.admin")) {
								if (allowedParticles.contains(args[1].toUpperCase())) {
									sendBannerMessage(player, "&4This effect is already enabled!");
									return true;
								} else {
									try {
										if (Particle.valueOf(args[1].toUpperCase()) != null) {
											allowedParticles.add(args[1].toUpperCase());
											CommandUtils.getMainConfiguration().getCustomConfig().set("trail.enabledParticles", allowedParticles);
											CommandUtils.getMainConfiguration().saveCustomConfig();
											sendBannerMessage(player, "&7Effect &6" + args[1].toUpperCase() + " &7has been &aenabled&7!");
											return true;
										}
									} catch (IllegalArgumentException e) {
										sendBannerMessage(player, "&4Invalid particle effect!");
										return true;
									}
								}
							}
						case "rm":
							if (player.hasPermission("betterlife.trail.admin")) {
								if (!allowedParticles.contains(args[1].toUpperCase())) {
									sendBannerMessage(player, "&4This effect is already disabled!");
									return true;
								} else {
									try {
										if (Particle.valueOf(args[1].toUpperCase()) != null) {
											allowedParticles.remove(args[1].toUpperCase());
											CommandUtils.getMainConfiguration().getCustomConfig().set("trail.enabledParticles", allowedParticles);
											CommandUtils.getMainConfiguration().saveCustomConfig();
											sendBannerMessage(player, "&7Effect &6" + args[1].toUpperCase() + " &7has been &cdisabled&7!");
											return true;
										}
									} catch (IllegalArgumentException e) {
										sendBannerMessage(player, "&4Invalid particle effect!");
										return true;
									}
								}
							}
						default:
							sendBannerMessage(player, "&4That's not a valid command! Try /trail help.");
							return true;
					}
				default:
					sendBannerMessage(player, "&4Too many arguments! Try /trail help.");
					return true;
				}
			} else {
			sendBannerMessage(sender, "&4You must be a player to use this command!");
			return true;
		}
	}

	private void sendListTrails(Player player, List<String> allowedParticles) {
		sendBannerMessage(player, "&7The currently enabled trails are: ");

		for (int i = 0; i < allowedParticles.size(); i++) {
			if (player.hasPermission("betterlife.trails.particle." + allowedParticles.get(i).toLowerCase())) {
				sendBannerMessage(player, "&d" + allowedParticles.get(i), false);
			}
		}
	}
}
