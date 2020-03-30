package me.gt3ch1.betterlife.Main;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.events.PlayerWalkEvent;
import me.gt3ch1.betterlife.events.BlockFade;

public class Main extends JavaPlugin {
	MainConfigurationHandler ch;
	PlayerConfigurationHandler pch;
	Listener blockFadeListener,playerMoveListener;
	@Override
	public void onEnable() {

		ch = new MainConfigurationHandler(this);
		pch = new PlayerConfigurationHandler(this);
		saveDefaultConfig();
		pch.getCustomConfig();
		pch.saveCustomConfig();
		blockFadeListener = new BlockFade(this);
	 	playerMoveListener = new PlayerWalkEvent(this);
		Bukkit.getPluginManager().registerEvents(blockFadeListener, this);
		Bukkit.getPluginManager().registerEvents(playerMoveListener, this);
		getLogger().info(ChatColor.GREEN+"Enabled!");
		
	}

	@Override
	public void onDisable() {
		ch = null;
		pch = null;
		blockFadeListener = null;
		playerMoveListener = null;
		getLogger().info(ChatColor.BLUE + "Disabled!");
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		try {
			this.getCommand(label).setExecutor((CommandExecutor) Class
					.forName("me.gt3ch1.betterlife.commands." + label.toUpperCase())
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
