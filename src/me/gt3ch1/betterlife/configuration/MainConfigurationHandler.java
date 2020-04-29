package me.gt3ch1.betterlife.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.gt3ch1.betterlife.Main.Main;

/**
 * This class handles the main configuration of the plugin.
 */
public class MainConfigurationHandler extends ConfigurationHelper{

	public MainConfigurationHandler(){
		super("config","config",false);
	}

}
