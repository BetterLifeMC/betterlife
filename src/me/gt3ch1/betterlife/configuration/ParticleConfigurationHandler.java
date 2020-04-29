package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

/**
 * This class handles all of the configuration files for the player particles.
 */
public class ParticleConfigurationHandler extends ConfigurationHelper {
    /**
     * Creates a new configuration handler for particles (particles.yml)
     */
    public ParticleConfigurationHandler() {
        super("particles","particles",false);
        this.saveCustomConfig();
    }
}
