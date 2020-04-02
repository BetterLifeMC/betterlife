package me.gt3ch1.betterlife.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;

public class ROADBOOST extends BetterLifeCommands implements CommandExecutor {
	private Main m;

	public ROADBOOST(Main m, String permission, CommandSender cs, Command c, String label, String[] args) {
		super(m, permission, cs, c, label, args);
		this.m = m;
		this.onCommand(cs, c, label, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission(this.getPermission())) {
				boolean hasToggleSprintEnabled = m.getPlayerConfiguration().getCustomConfig().getBoolean("player."+p.getUniqueId().toString()+".pathboost");
				m.getPlayerConfiguration().getCustomConfig().set("player."+p.getUniqueId().toString()+".pathboost", !hasToggleSprintEnabled);
				m.getPlayerConfiguration().saveCustomConfig();
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
