package me.gt3ch1.betterlife.Main;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.events.BlockFade;
import me.gt3ch1.betterlife.events.ComposterClick;
import me.gt3ch1.betterlife.events.PlayerJoin;
import me.gt3ch1.betterlife.events.PlayerWalk;
import me.gt3ch1.betterlife.tabcompletor.TabCompletorHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main extends JavaPlugin {
	MainConfigurationHandler ch;
	PlayerConfigurationHandler pch;
	Listener blockFadeListener,playerMoveListener,playerJoinListener,composterClickListener;
	Main m;
	@Override
	public void onEnable() {
		// Config setup
		ch = new MainConfigurationHandler(this);
		pch = new PlayerConfigurationHandler(this);
		saveDefaultConfig();
		pch.getCustomConfig();
		pch.saveCustomConfig();

		// Listener setup
		blockFadeListener = new BlockFade(this);
	 	playerMoveListener = new PlayerWalk(this);
	 	playerJoinListener = new PlayerJoin(this);
	 	composterClickListener = new ComposterClick(this);
		Bukkit.getPluginManager().registerEvents(blockFadeListener, this);
		Bukkit.getPluginManager().registerEvents(playerMoveListener, this);
		Bukkit.getPluginManager().registerEvents(playerJoinListener, this);
		Bukkit.getPluginManager().registerEvents(composterClickListener,this);
		// Initialize Main variable
		m = this;
		//TODO: Set tab completors.  Need a way to load all of the commands.  I've got an idea
		// Make the superclass of BetterLifeCommands add to an array of strings in this class.
		
		// Log output
		getLogger().info(ChatColor.GREEN + "Enabled!");
	}

	@Override
	public void onDisable() {
		ch = null;
		pch = null;

		blockFadeListener = null;
		playerMoveListener = null;
		playerMoveListener = null;
		composterClickListener = null;
		
		getLogger().info(ChatColor.RED + "Disabled!");
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
