package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class PlayerConfigurationHandler {
    private static FileConfiguration customConfig = null;
    private static File customConfigFile = null;
    // Declare m
    private final Main m;
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

    public void setValue(String path, Object value, String playerUUID) {
        if (!Main.isUsingSql) {
            this.getCustomConfig().set("player." + playerUUID + "." + path, value);
            this.saveCustomConfig();
        } else {
            try {
                Main.sql.setValue(path, value, playerUUID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public boolean getBooleanValue(String path, String playerUUID) {
        if (!Main.isUsingSql)
            return this.getCustomConfig().getBoolean("player." + playerUUID + "." + path);
        else
            return Boolean.valueOf(Main.sql.getValue(path, playerUUID).toString());
    }

    public String getStringValue(String path, String playerUUID) {
        if (!Main.isUsingSql)
            return this.getCustomConfig().getString("player." + playerUUID + "." + path);
        else
            return Main.sql.getValue(path, playerUUID).toString();
    }

    public List<String> getList(String path, String playerUUID) {
        return this.getCustomConfig().getStringList("player." + playerUUID + "." + path);
    }

    public List<String> getSqlRow(String path) {
        path = path.replace(".", "_");
        return Main.sql.getRows(path);
    }

    public Object get(String path, String playerUUID) {
        if (!Main.isUsingSql)
            return this.getCustomConfig().get("player." + playerUUID + "." + path);
        else {
            path = path.replace(".", "_");
            return Main.sql.getValue(path, playerUUID);
        }

    }

}
