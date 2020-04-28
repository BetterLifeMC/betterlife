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

/**
 * This class handles all of the player related configuration settings.  It contains the hashmaps and other
 * related functions to provide configuration support.
 */
public class PlayerConfigurationHandler extends  ConfigurationHelper{
    private static FileConfiguration customConfig = null;
    private static File customConfigFile = null;
    // Declare m
    public HashMap<UUID, Boolean> trailEnabledPerPlayer = new HashMap<>();
    public HashMap<UUID, String> trailPerPlayer = new HashMap<>();
    public HashMap<UUID, Boolean> roadboostPerPlayer = new HashMap<>();
    /**
     * Creates a new configuration handler for players (player_config.yml)
     */
    public PlayerConfigurationHandler() {
        super("players","player_config");
    }

}
