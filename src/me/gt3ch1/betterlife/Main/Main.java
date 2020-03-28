package me.gt3ch1.betterlife.Main;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.events.blockFade;

public class Main extends JavaPlugin {
	MainConfigurationHandler ch;
	PlayerConfigurationHandler pch;
	Listener l;
	@Override
	public void onEnable() {
//		new CommandTemplate(this);

		ch = new MainConfigurationHandler(this);
		pch = new PlayerConfigurationHandler(this);
		saveDefaultConfig();
		pch.getCustomConfig();
		pch.saveCustomConfig();
		l = new blockFade(this);
		Bukkit.getPluginManager().registerEvents(l, this);
		
	}

	@Override
	public void onDisable() {
		ch = null;
		pch = null;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		try {
			this.getCommand(label).setExecutor((CommandExecutor) Class
					.forName("me.gt3ch1.betterlife.commands." + label.toLowerCase() + "." + label.toUpperCase())
					.getConstructor(Main.class, String.class, CommandSender.class, Command.class, String.class, String[].class)
					.newInstance(this, label.toLowerCase(), cs, cmd, label, args));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return true;
	}

	public MainConfigurationHandler getMainConfiguration() {
		return ch;
	}

	public PlayerConfigurationHandler getPlayerConfiguration() {
		return pch;
	}

}
