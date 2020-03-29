package me.gt3ch1.betterlife.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.gt3ch1.betterlife.Main.Main;

public class MainConfigurationHandler {
	private Main m;
	public MainConfigurationHandler(Main m) {
		this.m = m;
	}
	private static FileConfiguration customConfig = null;
	private static File customConfigFile = null;

	public void reloadCustomConfig() {
		if (customConfigFile == null) {
			customConfigFile = new File(m.getDataFolder(), "config.yml");
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

		Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(m.getResource("config.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			customConfig.setDefaults(defConfig);
		}
	}

	public FileConfiguration getCustomConfig() {
		if (customConfig == null) {
			reloadCustomConfig();
		}
		return m.getConfig();
	}

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