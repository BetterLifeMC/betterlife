package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

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
    public boolean isUsingSql = Main.isUsingSql;
    /**
     * Creates a new configuration helper
     * @param table SQL table/
     * @param filename File to be createad/writen to if not using SQL.
     * @param isUsingSQL Whether or not to use MySQL for configuration storage.
     */
    public ConfigurationHelper(String table,String filename,boolean isUsingSQL){
        this.table = table;
        this.filename = filename;
        this.isUsingSql=isUsingSQL;
        if(isUsingSQL)
            Main.doBukkitLog(ChatColor.DARK_PURPLE+"Using SQL for configuration » " + filename);
        else
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
     * Returns the current instance of the player configuration
     * file.
     *
     * @return PlayerConfiguration
     */
    public FileConfiguration getCustomConfig() {

        if (customConfig == null)
            reloadCustomConfig();

        return customConfig;
    }

    /**
     * Save the current state of the player configuration in to
     * it's file.
     */
    public void saveCustomConfig() {

        if (customConfig == null || customConfigFile == null)
            return;

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
    /**
     * Sets the value of path to value for playerUUID
     *
     * @param path Location to set value
     * @param value Object to set value
     * @param playerUUID Player UUID
     */
    public void setValue(String path, Object value, UUID playerUUID) {
        if (!isUsingSql) {
            this.getCustomConfig().set("player." + playerUUID.toString() + "." + path, value);
            this.saveCustomConfig();
        } else {
            try {
                Main.sql.setValue(path, value, playerUUID.toString(), table);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * Gets the boolean value of path from config for playerUUID
     *
     * @param path Location of boolean in config
     * @param playerUUID Player UUID
     * @return
     */
    public boolean getBooleanValue(String path, UUID playerUUID) {
        if (!isUsingSql)
            return this.getCustomConfig().getBoolean("player." + playerUUID.toString() + "." + path);
        else
            return Boolean.valueOf(Main.sql.getValue(path, playerUUID.toString(), table).toString());
    }

    /**
     * Gets a string value from the config. for playerUUID.
     *
     * @param path Location of wanted value
     * @param playerUUID Player UUID
     * @return value
     */
    public String getStringValue(String path, UUID playerUUID) {
        if (!isUsingSql)
            return this.getCustomConfig().getString("player." + playerUUID.toString() + "." + path);
        else
            return Main.sql.getValue(path, playerUUID.toString(), table).toString();
    }

    /**
     * Get's the row of data from SQL
     *
     * @param path Location in config
     * @return Items existing at row
     */
    public List<String> getRow(String path) {
        if (isUsingSql) {
            path = path.replace(".", "_");
            return Main.sql.getRows(path, table);
        } else {
            return this.getCustomConfig().getStringList(path);
        }

    }

    /**
     * Get's the value of path from the player UUID from the config.
     *
     * @param path
     * @param playerUUID
     * @return value at path
     */
    public Object get(String path, UUID playerUUID) {
        if (!isUsingSql)
            return this.getCustomConfig().get("player." + playerUUID.toString() + "." + path);
        else {
            path = path.replace(".", "_");
            return Main.sql.getValue(path, playerUUID.toString(), table);
        }

    }

}
