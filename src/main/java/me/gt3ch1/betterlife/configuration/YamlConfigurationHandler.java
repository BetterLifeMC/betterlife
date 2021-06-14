package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.main.BetterLife;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This class contains methods that are needed to support different configuration files.
 *
 * @author gt3ch1
 */
public abstract class YamlConfigurationHandler {

    private FileConfiguration customConfig = null;
    private File customConfigFile = null;
    private String filename;
    private BetterLife m;

    /**
     * Creates a new configuration helper
     *
     * @param filename File to be createad/writen to if not using SQL.
     */
    protected YamlConfigurationHandler(String filename, BetterLife m) {
        this.filename = filename + ".yml";
        this.m = m;
    }

    /**
     * Reloads the configuration of all players
     */
    public void reloadCustomConfig() {

        if (customConfigFile == null) {
            customConfigFile = new File(m.getDataFolder(), filename);
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        Reader defConfigStream = null;
        InputStream config = m.getResource(filename);
        if (config != null) {
            defConfigStream = new InputStreamReader(config, StandardCharsets.UTF_8);
        }

        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }
    }

    /**
     * Returns the current instance of the player configuration file.
     *
     * @return PlayerConfiguration
     */
    public FileConfiguration getCustomConfig() {

        if (customConfig == null) {
            reloadCustomConfig();
        }

        return customConfig;
    }

    /**
     * Returns the file used for the config.
     * @return Config file
     */
    public File getFile() {
        if (customConfigFile != null) {
            return customConfigFile;
        }
        return null;
    }

    /**
     * Save the current state of the player configuration in to it's file.
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

    /**
     * Saves default config.
     */
    public void saveDefaultConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(m.getDataFolder(), filename);
        }
        if (!customConfigFile.exists()) {
            m.saveResource(filename, false);
        }
    }
}
