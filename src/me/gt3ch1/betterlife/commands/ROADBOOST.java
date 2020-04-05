package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ROADBOOST extends BetterLifeCommands implements CommandExecutor {

	public ROADBOOST(String permission, CommandSender cs, Command c, String label, String[] args) {
		super(permission, cs, c, label, args);
		this.onCommand(cs, c, label, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission(this.getPermission())) {
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
