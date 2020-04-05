package me.gt3ch1.betterlife.Main;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.events.BlockFade;
import me.gt3ch1.betterlife.events.PlayerJoin;
import me.gt3ch1.betterlife.events.PlayerMove;
import me.gt3ch1.betterlife.tabcompleter.TabCompleterHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class Main extends JavaPlugin {
	private Listener blockFadeListener,playerMoveListener,playerJoinListener;
	Listener[] enabledListeners = {blockFadeListener,playerMoveListener,playerJoinListener};
	public static Main m;

	@Override
	public void onEnable() {
		// Initialize Main variable
		m = this;
		// Config setup
		CommandUtils.enableConfiguration(m);
		// Listener setup
		blockFadeListener = new BlockFade();
		playerMoveListener = new PlayerMove();
		playerJoinListener = new PlayerJoin();
/*		for (Listener listener : enabledListeners) {
			Bukkit.getPluginManager().registerEvents(listener, this);
		}*/
		Bukkit.getPluginManager().registerEvents(blockFadeListener, this);
		Bukkit.getPluginManager().registerEvents(playerMoveListener, this);
		Bukkit.getPluginManager().registerEvents(playerJoinListener, this);
		// Tab Completion setup
		for (String command : CommandUtils.getEnabledTabCommands()) {
			getCommand(command).setTabCompleter(new TabCompleterHelper());
		}
		// Log output
		getLogger().info("Hello!");
	}

	@Override
	public void onDisable() {
		// Set the configuration managers to null
		CommandUtils.disableConfiguration(m);
		// Set all listeners to null
		for (Listener l : enabledListeners) {
			l = null;
		}
		// Log output
		getLogger().info("Goodbye!");
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		// Sends the command to the designated class file
		try {
			this.getCommand(label).setExecutor((CommandExecutor) Class
					.forName("me.gt3ch1.betterlife.commands." + label.toUpperCase())
					.getConstructor(String.class, CommandSender.class, Command.class, String.class, String[].class)
					.newInstance(label.toLowerCase(), cs, cmd, label, args));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return true;
	}
}
