package me.gt3ch1.betterlife.Main;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;

public class Main extends JavaPlugin {
	MainConfigurationHandler ch;
	PlayerConfigurationHandler pch;

	@Override
	public void onEnable() {
//		new CommandTemplate(this);

		ch = new MainConfigurationHandler(this);
		pch = new PlayerConfigurationHandler(this);
		saveDefaultConfig();
		ch.getCustomConfig();
	}

	@Override
	public void onDisable() {
		ch = null;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		try {
			this.getCommand(label).setExecutor((CommandExecutor) Class
					.forName("me.gt3ch1.betterlife.commands." + label.toLowerCase() + "." + label.toUpperCase())
					.getConstructor(String.class, CommandSender.class, Command.class, String.class, String[].class)
					.newInstance(label.toLowerCase(), cs, cmd, label, args));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return true;
	}

}
