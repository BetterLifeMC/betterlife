package me.gt3ch1.betterlife.commandhelpers;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class CommandUtils {
	public static Main m = Main.m;
	public static MainConfigurationHandler ch;
	public static PlayerConfigurationHandler pch;
	public static String betterLifeBanner = ChatColor.translateAlternateColorCodes('&', "&c[[&9BetterLife&c]] ") + ChatColor.RESET;
	public static String[] enabledTabCommands = {"toggledownfall","trail","bl"};

	public static void sendBannerMessage(CommandSender sender, String message1) {
		String coloredMessage = ChatColor.translateAlternateColorCodes('&', message1);
		if (sender instanceof Player) {
			sender.sendMessage(betterLifeBanner + coloredMessage);
		} else {
			sender.sendMessage(ChatColor.stripColor(coloredMessage));
		}
	}

	public static void sendBannerMessage(CommandSender sender, String message1, boolean banner) {
		String coloredMessage = ChatColor.translateAlternateColorCodes('&', message1);
		if (sender instanceof Player) {
			sender.sendMessage(coloredMessage);
		} else {
			sender.sendMessage(ChatColor.stripColor(coloredMessage));
		}
	}

	public static void sendHelpMessage(CommandSender sender, String commandName, LinkedHashMap<String, String> args) {
		sendBannerMessage(sender, "&b&lby GT3CH1");
		sendBannerMessage(sender, "&6--== [ Available commands ] ==--", false);
		args.forEach((cmd, desc) -> {
			sendBannerMessage(sender, "&d/" + commandName + " " + cmd + " &r| " + "&6" + desc, false);
		});
	}

    public static void enableConfiguration(Main m) {
		ch = new MainConfigurationHandler(m);
		pch = new PlayerConfigurationHandler(m);
		m.saveDefaultConfig();
		pch.getCustomConfig();
		pch.saveCustomConfig();
    }

	public static void disableConfiguration(Main m) {
		ch = null;
		pch = null;
	}

	public static MainConfigurationHandler getMainConfiguration() {
		return ch;
	}

	public static PlayerConfigurationHandler getPlayerConfiguration() {
		return pch;
	}

	public static void reloadConfiguration(Main m) {
		m.reloadConfig();
		pch.reloadCustomConfig();
	}

	public static String[] getEnabledTabCommands() {
		return enabledTabCommands;
	}
}
