package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * This class contains methods that are needed to support different configuration files.
 *
 * @author gt3ch1
 */
public class ConfigurationHelper {

    private static FileConfiguration customConfig = null;
    private static File customConfigFile = null;
    private final String table;
    private final String filename;
    private final Main m = Main.m;

    /**
     * Creates a new configuration helper
     */
    public ConfigurationHelper(String table, String filename) {
        this.table = table;
        this.filename = filename;
    }

    /**
     * Reloads the configuration of all players
     */
    public void reloadCustomConfig() {

        if (customConfigFile == null)
            customConfigFile = new File(m.getDataFolder(), filename + ".yml");

        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        Reader defConfigStream = null;

        defConfigStream = new InputStreamReader(m.getResource(filename + ".yml"), StandardCharsets.UTF_8);

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
     * Sets the value of path to value for playerUUID
     *
     * @param path
     * @param value
     * @param playerUUID
     */
    public void setValue(String path, Object value, UUID playerUUID) {
        if (!Main.isUsingSql) {
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
     * @param path
     * @param playerUUID
     * @return
     */
    public boolean getBooleanValue(String path, UUID playerUUID) {
        if (!Main.isUsingSql)
            return this.getCustomConfig().getBoolean("player." + playerUUID.toString() + "." + path);
        else
            return Boolean.valueOf(Main.sql.getValue(path, playerUUID.toString(), table).toString());
    }

    /**
     * Gets a string value from the config. for playerUUID.
     *
     * @param path
     * @param playerUUID
     * @return value
     */
    public String getStringValue(String path, UUID playerUUID) {
        if (!Main.isUsingSql)
            return this.getCustomConfig().getString("player." + playerUUID.toString() + "." + path);
        else
            return Main.sql.getValue(path, playerUUID.toString(), table).toString();
    }

    /**
     * Returns a list from the player config for playerUUID.
     *
     * @param path
     * @param playerUUID
     * @return value list
     */
    public List<String> getList(String path, UUID playerUUID) {
        return this.getCustomConfig().getStringList("player." + playerUUID.toString() + "." + path);
    }

    /**
     * Get's the row of data from SQL
     *
     * @param path
     * @return List
     */
    public List<String> getRow(String path) {
        if (Main.isUsingSql) {
            path = path.replace(".", "_");
            return Main.sql.getRows(path, table);
        }else{
            System.out.println("no SQL for : " + path + " on file: " + filename + ".yml");
            System.out.println(Arrays.toString(this.getCustomConfig().getStringList(path).toArray()));
            return this.getCustomConfig().getStringList(path);
        }

    }
    //TODO: Merge getSqlRow and getList together.

    /**
     * Get's the value of path from the player UUID from the config.
     *
     * @param path
     * @param playerUUID
     * @return
     */
    public Object get(String path, UUID playerUUID) {
        if (!Main.isUsingSql)
            return this.getCustomConfig().get("player." + playerUUID.toString() + "." + path);
        else {
            path = path.replace(".", "_");
            return Main.sql.getValue(path, playerUUID.toString(), table);
        }

    }

}
