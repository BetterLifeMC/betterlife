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
	public static String betterLifeBanner = ChatColor.RED + "[[" + ChatColor.BLUE + "BetterLife" + ChatColor.RED + "]] " + ChatColor.RESET;
	public static String[] enabledTabCommands = {"toggledownfall","trail"};

	public static void sendBannerMessage(CommandSender sender, String message1) {
		if (sender instanceof Player) {
			sender.sendMessage(betterLifeBanner + message1);
		} else {
			sender.sendMessage(ChatColor.stripColor(message1));
		}
	}

	public static void sendBannerMessage(CommandSender sender, String message1, String message2) {
		if (sender instanceof Player) {
			sender.sendMessage(betterLifeBanner + message1 + ChatColor.RESET + " | " + message2);
		} else {
			sender.sendMessage(ChatColor.stripColor(message1 + " | " + message2));
		}
	}

	public static void sendHelpMessage(CommandSender sender, String commandName, LinkedHashMap<String, String> args) {
		sendBannerMessage(sender, ChatColor.AQUA + "by GT3CH1");
		sendBannerMessage(sender, ChatColor.GOLD + "--== [ Available commands ] ==--");
		args.forEach((cmd, desc) -> {
			sendBannerMessage(sender, ChatColor.LIGHT_PURPLE + "/" + commandName + " " + cmd, ChatColor.GOLD + desc);
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
