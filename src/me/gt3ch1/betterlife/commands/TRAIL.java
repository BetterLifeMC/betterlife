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

	/**
	 * Creates a new instance of the trail command, used for adding
	 * and modifying the players trail
	 * @param permission
	 * @param cs
	 * @param c
	 * @param label
	 * @param args
	 */
	public TRAIL(String permission, CommandSender cs, Command c, String label, String[] args) {
		super(permission, cs, c, label, args);
		this.onCommand(cs, c, label, args);
	}

	/**
	 * Runs when the command is /trail
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		// Get the help messages for trail
		LinkedHashMap<String, String> helpHash = HelpHelper.getAHelpHash("trail");
		// If the CommandSender is a player
		if (sender instanceof Player) {
			// Cast it
			Player player = (Player) sender;
			// Get all the particles in the main config
			List<String> allowedParticles = (List<String>) CommandUtils.getMainConfiguration().getCustomConfig().getList("trail.enabledParticles");
			// Test the lengths of the command passed.
			switch(args.length) {
				// If it is 0, you screwed up.
				case 0:
					sendHelpMessage(player, "trail", helpHash);
					return true;
				// If it is 1, then there are a few options
				case 1:
					// Test the arguments passed
					switch(args[0]) {
						// If it is list...
						case "list":
							// Send the list of all the enabled trails.
							sendListTrails(player, allowedParticles);
							return true;
						// If it is toggle
						case "toggle":
							// If the player has permission..
							if (player.hasPermission("betterlife.trail.toggle")) {
								// Toggle the current state of the trail.
								boolean trailsEnabled = CommandUtils.getPlayerConfiguration().getCustomConfig().getBoolean("player." + player.getUniqueId().toString() + ".trails.enabled");
								CommandUtils.getPlayerConfiguration().getCustomConfig().set("player." + player.getUniqueId().toString() + ".trails.enabled", !trailsEnabled);
								String toggleState = trailsEnabled ? "&cdisabled" : "&aenabled";
								CommandUtils.getPlayerConfiguration().saveCustomConfig();
								sendBannerMessage(player, "&7Trail " + toggleState + "&7!");
								return true;
							}
						// If it is help
						case "help":
							// Print the help message
							sendHelpMessage(player, "trail", helpHash);
							return true;
						// All else fails... invalid command
						default:
							sendBannerMessage(player, "&4That's not a valid command. Try /trail help.");
							return true;
					}
				// If there are TWO arguments...
				case 2:
					// Test the arguments
					switch(args[0]) {
						// If it is set
						case "set":
							// If the particle is in our enabled list, and the player has permission to use the particle.
							if (allowedParticles.contains(args[1].toUpperCase()) && (player.hasPermission("betterlife.trail.particle." + args[1].toLowerCase()) || player.hasPermission("betterlife.trail.particle.*"))) {
								// Enable it and set it in the configuration.
								CommandUtils.getPlayerConfiguration().getCustomConfig().set("player." + player.getUniqueId().toString() + ".trail", args[1].toUpperCase());
								CommandUtils.getPlayerConfiguration().saveCustomConfig();
								sendBannerMessage(player, "&7Your trail is now set to: &6" + args[1].toUpperCase());
								return true;
								
							} else if (!player.hasPermission("betterlife.trail.particle." + args[1].toLowerCase())) {
								// If you lack permission, tough luck.
								sendBannerMessage(player, "&4You need permission to use that trail!");
								return true;
							} else if (!allowedParticles.contains(args[1].toUpperCase())) {
								// If the plugin isn't enabled, oh well.
								sendBannerMessage(player, "&4That particle is disabled on this server! Try /trail list to see the list of available trails.");
								return true;
							}
						// If it is add
						case "add":
							// And the player has the permission to administrate trails
							if (player.hasPermission("betterlife.trail.admin")) {
								// If the particle is enabled, what are you doing already?
								if (allowedParticles.contains(args[1].toUpperCase())) {
									sendBannerMessage(player, "&4This effect is already enabled!");
									return true;
								} else {
								// If it ISN'T enabled...
									try {
										// Try to enable it if it is an actual particle.
										if (Particle.valueOf(args[1].toUpperCase()) != null) {
											allowedParticles.add(args[1].toUpperCase());
											CommandUtils.getMainConfiguration().getCustomConfig().set("trail.enabledParticles", allowedParticles);
											CommandUtils.getMainConfiguration().saveCustomConfig();
											sendBannerMessage(player, "&7Effect &6" + args[1].toUpperCase() + " &7has been &aenabled&7!");
											return true;
										}
									// Please don't enable invalid particles.  It doesn't work.
									} catch (IllegalArgumentException e) {
										sendBannerMessage(player, "&4Invalid particle effect!");
										return true;
									}
								}
							}
						// If it is rm
						case "rm":
							// If the player has permission to administrate trails
							if (player.hasPermission("betterlife.trail.admin")) {
								// If it doesn't exist in the enabled particles, why are you trying to remove it?
								if (!allowedParticles.contains(args[1].toUpperCase())) {
									sendBannerMessage(player, "&4This effect is already disabled!");
									return true;
								} else {
								// But if it isn't...
									try {
										// Try to disable it if it isn't an invalid particle
										if (Particle.valueOf(args[1].toUpperCase()) != null) {
											allowedParticles.remove(args[1].toUpperCase());
											CommandUtils.getMainConfiguration().getCustomConfig().set("trail.enabledParticles", allowedParticles);
											CommandUtils.getMainConfiguration().saveCustomConfig();
											sendBannerMessage(player, "&7Effect &6" + args[1].toUpperCase() + " &7has been &cdisabled&7!");
											return true;
										}
									// Again, you can't remove/add invalid particles.
									} catch (IllegalArgumentException e) {
										sendBannerMessage(player, "&4Invalid particle effect!");
										return true;
									}
								}
							}
						// Big yikes if you screwed it up.
						default:
							sendBannerMessage(player, "&4That's not a valid command! Try /trail help.");
							return true;
					}
				// "/trail add BARRIER 3" doesn't work.
				default:
					sendBannerMessage(player, "&4Too many arguments! Try /trail help.");
					return true;
				}
			} else {
			// Unfortunately, because the console isn't a player, they can't use it.
			sendBannerMessage(sender, "&4You must be a player to use this command!");
			return true;
		}
	}

	/**
	 * Sends a list of currently activated trails to the player.
	 * @param player
	 * @param allowedParticles
	 */
	private void sendListTrails(Player player, List<String> allowedParticles) {
		sendBannerMessage(player, "&7The currently enabled trails are: ");

		for (int i = 0; i < allowedParticles.size(); i++) {
			if (player.hasPermission("betterlife.trails.particle." + allowedParticles.get(i).toLowerCase())) {
				sendBannerMessage(player, "&d" + allowedParticles.get(i), false);
			}
		}
	}
}
