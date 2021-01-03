package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * This class contains methods that are needed to support different configuration files.
 *
 * @author gt3ch1
 */
public class ConfigurationHelper {

    private FileConfiguration customConfig = null;
    private File customConfigFile = null;
    private String table;
    private String filename;
    private Main m = Main.m;

    /**
     * Creates a new configuration helper
     *
     * @param table    SQL table/
     * @param filename File to be createad/writen to if not using SQL.
     */
    public ConfigurationHelper(String table, String filename) {
        this.table = table;
        this.filename = filename + ".yml";
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

        defConfigStream = new InputStreamReader(m.getResource(filename), StandardCharsets.UTF_8);

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
