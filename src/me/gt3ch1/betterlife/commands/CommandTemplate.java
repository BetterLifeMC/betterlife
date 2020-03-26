package me.gt3ch1.betterlife.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;

public class CommandTemplate {
	public ArrayList<String> commandStrings = new ArrayList<>();
	public static Main m = Main.getPlugin(Main.class);
	public static String betterLifeBanner = ChatColor.RED + "[[" + ChatColor.BLUE + "BetterLife" + ChatColor.RED + "]] "
			+ ChatColor.RESET;

	private Main plugin;

	public CommandTemplate(Main m) {
		this.plugin = m;
		for (Commands cmd : Commands.values()) {
			try {
				plugin.getCommand(cmd.toString().toLowerCase())
					.setExecutor((CommandExecutor) Class.forName("me.gt3ch1.betterlife.commands." 
						+ cmd.getCommand().toLowerCase() + "."
						+ cmd.getCommand().toUpperCase())
						.getConstructor(String.class).newInstance(cmd.getCommand().toLowerCase()));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}

	}

	public static void sendMessageToPlayer(Player p, String message1, ChatColor message1Color) {
		p.sendMessage(betterLifeBanner + message1Color + message1);
	}

	public static void sendMessageToPlayer(Player p, String message1, String message2, ChatColor message1Color,
			ChatColor message2Color, boolean banner) {
		p.sendMessage(betterLifeBanner + message1Color + message1 + ChatColor.RESET + " | " + message2Color + message2);
	}

}
