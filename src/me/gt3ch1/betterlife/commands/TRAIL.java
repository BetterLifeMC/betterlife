package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.commandhelpers.HelpHelper;
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
		List<String> allowedParticles = (List<String>) CommandUtils.getMainConfiguration().getCustomConfig().getList("trail.enabledParticles");
		switch(args[0]) {
			case "list":
				if (sender instanceof Player) {
					Player player = (Player) sender;
					sendBannerMessage(player, "&7The currently enabled trails are: ");
					for (int i = 0; i < allowedParticles.size(); i++) {
						if (player.hasPermission("betterlife.trails.particle." + allowedParticles.get(i).toLowerCase())) {
							sendBannerMessage(player, "&d" + allowedParticles.get(i), false);
						}
					}
				} else {
					sendBannerMessage(sender, "&7The currently enabled trails are: ");
					for (int i = 0; i < allowedParticles.size(); i++) {
						sendBannerMessage(sender, "&d" + allowedParticles.get(i), false);
					}
				}
				return true;
			case "toggle":
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (player.hasPermission("betterlife.trail.toggle")) {
						boolean trailsEnabled = CommandUtils.getPlayerConfiguration().getCustomConfig().getBoolean("player." + player.getUniqueId().toString() + ".trails.enabled");
						CommandUtils.getPlayerConfiguration().getCustomConfig().set("player." + player.getUniqueId().toString() + ".trails.enabled", !trailsEnabled);
						String toggleState = trailsEnabled ? "&cdisabled" : "&aenabled";
						CommandUtils.getPlayerConfiguration().saveCustomConfig();
						sendBannerMessage(player, "&7Trail " + toggleState + "&7!");
						return true;
					}
				} else {
					// TODO: Add console support for attaching a username target as arg[1]
					sendBannerMessage(sender, "&4You must be a player to use this command!");
					return true;
				}
				return true;
			case "set":
				if (sender instanceof Player) {
					Player player = (Player) sender;
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
				} else {
					// TODO: Add console support for attaching a username target as arg[2]
					sendBannerMessage(sender, "&4You must be a player to use this command!");
					return true;
				}
				return true;
				// TODO: Add a case for arg[0] not existing!
			default:
				sendHelpMessage(sender, "trail", helpHash);
				return true;
		}
	}
}
