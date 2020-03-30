package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CROPTRAMPLE extends BetterLifeCommands implements CommandExecutor {
	private Main m;

	public CROPTRAMPLE(Main m, String permission, CommandSender cs, Command c, String label, String[] args) {
		super(m, permission, cs, c, label, args);
		this.m = m;
		this.onCommand(cs, c, label, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		toggleCropTrample(sender);
		return true;
	}

	private void toggleCropTrample(CommandSender sender) {
		boolean hasCropTrampleEnabled = m.getMainConfiguration().getCustomConfig().getBoolean("events.croptrample");
		m.getMainConfiguration().getCustomConfig().set("events.croptrample", !hasCropTrampleEnabled);
		m.getMainConfiguration().saveCustomConfig();
		if (!hasCropTrampleEnabled) {
			sendBannerMessage(sender, ChatColor.GRAY + "Anti-Crop-Trample has been " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + "!");
		}
		else {
			sendBannerMessage(sender, ChatColor.GRAY + "Anti-Crop-Trample has been " + ChatColor.RED + "disabled" + ChatColor.GRAY + "!");
		}
	}
}
