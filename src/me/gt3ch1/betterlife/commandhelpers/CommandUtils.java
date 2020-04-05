package me.gt3ch1.betterlife.commandhelpers;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class CommandUtils {
	// Initializes some important variables.
	private static Main m = Main.m;
	public static MainConfigurationHandler ch;
	public static PlayerConfigurationHandler pch;
	public static String betterLifeBanner = ChatColor.translateAlternateColorCodes('&', "&c[[&9BetterLife&c]] ") + ChatColor.RESET;
	public static String[] enabledTabCommands = {"toggledownfall","trail","bl"};

	/**
	 * Send the sender the BetterLife banner.
	 * @param sender
	 * @param message1
	 */
	public static void sendBannerMessage(CommandSender sender, String message1) {
		String coloredMessage = ChatColor.translateAlternateColorCodes('&', message1);
		if (sender instanceof Player) {
			sender.sendMessage(betterLifeBanner + coloredMessage);
		} else {
			sender.sendMessage(ChatColor.stripColor(coloredMessage));
		}
	}

	/**
	 * Sends the sender a message minus the BetterLife banner.
	 * @param sender
	 * @param message1
	 * @param banner
	 */
	public static void sendBannerMessage(CommandSender sender, String message1, boolean banner) {
		String coloredMessage = ChatColor.translateAlternateColorCodes('&', message1);
		if (sender instanceof Player) {
			sender.sendMessage(coloredMessage);
		} else {
			sender.sendMessage(ChatColor.stripColor(coloredMessage));
		}
	}

	/**
	 * Sends a help header
	 * @param sender
	 * @param commandName
	 * @param args
	 */
	public static void sendHelpMessage(CommandSender sender, String commandName, LinkedHashMap<String, String> args) {
		sendBannerMessage(sender, "&b&lby GT3CH1 & Starmism");
		sendBannerMessage(sender, "&6--== [ Available commands ] ==--", false);
		args.forEach((cmd, desc) -> {
			sendBannerMessage(sender, "&d/" + commandName + " " + cmd + " &r| " + "&6" + desc, false);
		});
	}

    /**
     * Enables & loads the configuration
     */
    public static void enableConfiguration() {
		ch = new MainConfigurationHandler(m);
		pch = new PlayerConfigurationHandler(m);
		m.saveDefaultConfig();
		pch.getCustomConfig();
		pch.saveCustomConfig();
    }

	/**
	 * Disables all configuration helpers.
	 */
	public static void disableConfiguration() {
		ch = null;
		pch = null;
	}

	/**
	 * @return MainConfiguration
	 */
	public static MainConfigurationHandler getMainConfiguration() {
		return ch;
	}

	/**
	 * @return PlayerConfigurationHandler
	 */
	public static PlayerConfigurationHandler getPlayerConfiguration() {
		return pch;
	}

	/**
	 * Reloads all configuration helpers
	 */
	public static void reloadConfiguration() {
		m.reloadConfig();
		pch.reloadCustomConfig();
	}

	/**
	 * 
	 * @return TabCommands
	 */
	public static String[] getEnabledTabCommands() {
		return enabledTabCommands;
	}
}
