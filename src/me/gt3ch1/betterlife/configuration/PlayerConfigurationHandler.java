package me.gt3ch1.betterlife.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.gt3ch1.betterlife.Main.Main;

public class PlayerConfigurationHandler {
	// Declare m
	private Main m;
	
	/**
	 * Creates a new configuration handler for players (player_config.yml)
	 * @param m
	 */
	public PlayerConfigurationHandler(Main m) {
		this.m = m;
	}
	// Initiate a file configuration
	private static FileConfiguration customConfig = null;
	// Initiate a file
	private static File customConfigFile = null;

	/**
	 *  Reloads the configuration of all players
	 */
	public void reloadCustomConfig() {
		
		if (customConfigFile == null) {
			// Set the file to BetterLife/player_config.yml
			customConfigFile = new File(m.getDataFolder(), "player_config.yml");
		}
		// Load the configuration inside the file
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

		Reader defConfigStream = null;
		try {
			// Try to read the file.
			defConfigStream = new InputStreamReader(m.getResource("player_config.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			customConfig.setDefaults(defConfig);
		}
	}

	/**
	 * Returns the current instance of the player configuration
	 * file.
	 * @return PlayerConfiguration
	 */
	public FileConfiguration getCustomConfig() {
		if (customConfig == null) {
			reloadCustomConfig();
		}
		return customConfig;
	}

	/**
	 * Save the current state of the player configuration in to
	 * it's file.
	 */
	public void saveCustomConfig() {
		if (customConfig == null || customConfigFile == null) {
			return;
		}
		try {
			getCustomConfig().save(customConfigFile);
		} catch (IOException ex) {
			m.getLogger().severe("Could not save config to " + customConfigFile + "!\n" + ex);
		}
	}
}
