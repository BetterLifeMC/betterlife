package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CommandUtils {
	public ArrayList<String> commandStrings = new ArrayList<>();
	public static Main m = Main.getPlugin(Main.class);
	public static String betterLifeBanner = ChatColor.RED + "[[" + ChatColor.BLUE + "BetterLife" + ChatColor.RED + "]] " + ChatColor.RESET;


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

	public static void sendInvalidMessage(CommandSender sender, String cmd) {
		sendBannerMessage(sender, ChatColor.DARK_RED + "Invalid usage! Try /" + cmd.toLowerCase() + " help");
	}
}
