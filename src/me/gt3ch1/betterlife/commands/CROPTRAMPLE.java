package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CROPTRAMPLE extends BetterLifeCommands implements CommandExecutor {

	public CROPTRAMPLE(String permission, CommandSender cs, Command c, String label, String[] args) {
		super(permission, cs, c, label, args);
		this.onCommand(cs, c, label, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		boolean hasCropTrampleEnabled = CommandUtils.getMainConfiguration().getCustomConfig().getBoolean("events.croptrample");
		CommandUtils.getMainConfiguration().getCustomConfig().set("events.croptrample", !hasCropTrampleEnabled);
		CommandUtils.getMainConfiguration().saveCustomConfig();
		if (!hasCropTrampleEnabled) {
			sendBannerMessage(sender, ChatColor.GRAY + "Crop Trample Protection has been " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + "!");
		} else {
			sendBannerMessage(sender, ChatColor.GRAY + "Crop Trample Protection has been " + ChatColor.RED + "disabled" + ChatColor.GRAY + "!");
		}
		return true;
	}
}
