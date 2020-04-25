package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerConfigurationHandler {
    private static FileConfiguration customConfig = null;
    private static File customConfigFile = null;
    // Declare m
    private final Main m;
    public HashMap<UUID, Boolean> trailEnabledPerPlayer = new HashMap<>();
    public HashMap<UUID, String> trailPerPlayer = new HashMap<>();

    /**
     * Creates a new configuration handler for players (player_config.yml)
     *
     * @param m
     */
    public PlayerConfigurationHandler(Main m) {
        this.m = m;
    }

    /**
     * Reloads the configuration of all players
     */
    public void reloadCustomConfig() {

        if (customConfigFile == null)
            customConfigFile = new File(m.getDataFolder(), "player_config.yml");

        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        Reader defConfigStream = null;

        defConfigStream = new InputStreamReader(m.getResource("player_config.yml"), StandardCharsets.UTF_8);

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

    /** Sets the value of path to value for playerUUID
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
                Main.sql.setValue(path, value, playerUUID.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /** Gets the boolean value of path from config for playerUUID
     * @param path
     * @param playerUUID
     * @return
     */
    public boolean getBooleanValue(String path, UUID playerUUID) {
        if (!Main.isUsingSql)
            return this.getCustomConfig().getBoolean("player." + playerUUID.toString() + "." + path);
        else
            return Boolean.valueOf(Main.sql.getValue(path, playerUUID.toString()).toString());
    }

    /**
     * Gets a string value from the config. for playerUUID.
     * @param path
     * @param playerUUID
     * @return value
     */
    public String getStringValue(String path, UUID playerUUID) {
        if (!Main.isUsingSql)
            return this.getCustomConfig().getString("player." + playerUUID.toString() + "." + path);
        else
            return Main.sql.getValue(path, playerUUID.toString()).toString();
    }

    /**
     * Returns a list from the player config for playerUUID.
     * @param path
     * @param playerUUID
     * @return value list
     */
    public List<String> getList(String path, UUID playerUUID) {
        return this.getCustomConfig().getStringList("player." + playerUUID.toString() + "." + path);
    }

    /**
     * Get's the row of data from SQL
     * @param path
     * @return List
     */
    public List<String> getSqlRow(String path) {
        path = path.replace(".", "_");
        return Main.sql.getRows(path);
    }
    //TODO: Merge getSqlRow and getList together.

    /** Get's the value of path from the player UUID from the config.
     * @param path
     * @param playerUUID
     * @return
     */
    public Object get(String path, UUID playerUUID) {
        if (!Main.isUsingSql)
            return this.getCustomConfig().get("player." + playerUUID.toString() + "." + path);
        else {
            path = path.replace(".", "_");
            return Main.sql.getValue(path, playerUUID.toString());
        }

    }

}
