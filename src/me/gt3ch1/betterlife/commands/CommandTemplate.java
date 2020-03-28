package me.gt3ch1.betterlife.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;

public class CommandTemplate {
	public ArrayList<String> commandStrings = new ArrayList<>();
	public static Main m = Main.getPlugin(Main.class);
	public static String betterLifeBanner = ChatColor.RED + "[[" + ChatColor.BLUE + "BetterLife" + ChatColor.RED + "]] "
			+ ChatColor.RESET;


	public static void sendMessageToPlayer(Player p, String message1, ChatColor message1Color) {
		p.sendMessage(betterLifeBanner + message1Color + message1);
	}

	public static void sendMessageToPlayer(Player p, String message1, String message2, ChatColor message1Color,
			ChatColor message2Color, boolean banner) {
		p.sendMessage(betterLifeBanner + message1Color + message1 + ChatColor.RESET + " | " + message2Color + message2);
	}

}
