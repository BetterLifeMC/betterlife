package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class ParticleConfigurationHandler {
    // Declare m
    private Main m;

    /**
     * Creates a new configuration handler for particles (particles.yml)
     * @param m
     */
    public ParticleConfigurationHandler(Main m) {
        this.m = m;
    }
    private static FileConfiguration particleConfig = null;
    private static File particleConfigFile = null;

    /**
     *  Reloads the configuration of all particles
     */
    public void reloadParticleConfig() {

        if (particleConfigFile == null)
            particleConfigFile = new File(m.getDataFolder(), "particles.yml");

        particleConfig = YamlConfiguration.loadConfiguration(particleConfigFile);
        Reader defConfigStream = null;

        try {
            defConfigStream = new InputStreamReader(m.getResource("particles.yml"), "UTF8");
        } catch (UnsupportedEncodingException e) {
            m.getLogger().severe("There is a problem with particles.yml!");
            e.printStackTrace();
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            particleConfig.setDefaults(defConfig);
        }
    }

    /**
     * Returns the current instance of the player configuration
     * file.
     * @return ParticleConfiguration
     */
    public FileConfiguration getParticleConfig() {
        if (particleConfig == null)
            reloadParticleConfig();

        return particleConfig;
    }

    /**
     * Save the current state of the player configuration in to
     * it's file.
     */
    public void saveParticleConfig() {
        if (particleConfig == null || particleConfigFile == null)
            return;

        try {
            getParticleConfig().save(particleConfigFile);
        } catch (IOException ex) {
            m.getLogger().severe("Could not save config to " + particleConfigFile + "!\n" + ex);
        }
    }

    public void saveDefaultParticleConfig() {
        if (particleConfigFile == null)
            particleConfigFile = new File(m.getDataFolder(), "particles.yml");

        if (!particleConfigFile.exists()) {
            m.saveResource("particles.yml", false);
        }
    }
}
