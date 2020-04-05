package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ROADBOOST extends BetterLifeCommands implements CommandExecutor {

	/**
	 * Runs the command /roadboost
	 * @param permission
	 * @param cs
	 * @param c
	 * @param label
	 * @param args
	 */
	public ROADBOOST(String permission, CommandSender cs, Command c, String label, String[] args) {
		super(permission, cs, c, label, args);
		this.onCommand(cs, c, label, args);
	}

	/**
	 * Toggles whether or not the player can be fast
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		// If we have a player
		if (sender instanceof Player) {
			// Cast it to player
			Player p = (Player) sender;
			// And if they have permission
			if (p.hasPermission(this.getPermission())) {
				// Make them FAST
				boolean hasToggleSprintEnabled = CommandUtils.getPlayerConfiguration().getCustomConfig().getBoolean("player."+p.getUniqueId().toString()+".pathboost");
				CommandUtils.getPlayerConfiguration().getCustomConfig().set("player."+p.getUniqueId().toString()+".pathboost", !hasToggleSprintEnabled);
				CommandUtils.getPlayerConfiguration().saveCustomConfig();
				String toggleState = hasToggleSprintEnabled ? "&cdisabled" : "&aenabled";
				sendBannerMessage(p, "&7Roadboost has been " + toggleState + "&7!");
			}
		} else {
			// TODO: Add second arg for targeting a player
			sendBannerMessage(sender, "&4This command can only be done in-game!");
		}
		return true;
	}
}
