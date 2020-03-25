package me.gt3ch1.betterlife.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;

public abstract class CommandTemplate {
	public ArrayList<String> commandStrings = new ArrayList<>();
	public static Main m = Main.getPlugin(Main.class);
	static String customTrailsBanner = ChatColor.GREEN + "[[" + ChatColor.BLUE + "CustomTrails" + ChatColor.GREEN
			+ "]] " + ChatColor.RESET;
	public CommandTemplate() {
		
	}

	public static boolean doCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		// Overly complicated way of having a larger plugin.
//		Trail.voidDoCommand(sender, cmd, CommandLabel, args);
		return true;
	}
	
	public static void sendMessageToPlayer(Player p, String message1, ChatColor message1Color, boolean banner) {
		p.sendMessage(((banner) ? customTrailsBanner : "").toString() + message1Color + message1);
	}
	public static void sendMessageToPlayer(Player p, String message1, String message2, ChatColor message1Color, ChatColor message2Color, boolean banner) {
		p.sendMessage(((banner) ? customTrailsBanner : "").toString() + message1Color + message1 + ChatColor.RESET + " | " + message2Color + message2);
	}


}
