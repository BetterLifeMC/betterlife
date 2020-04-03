package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.ChatColor;
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
				if (!hasToggleSprintEnabled) {
					sendBannerMessage(p, ChatColor.GRAY + "Roadboost has been " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + "!");
				} else {
					sendBannerMessage(p, ChatColor.GRAY + "Roadboost has been " + ChatColor.RED + "disabled" + ChatColor.GRAY + "!");
				}
			}
		} else {
			sendBannerMessage(sender, ChatColor.DARK_RED + "This must be done from in game!");
		}
		return true;
	}
}
