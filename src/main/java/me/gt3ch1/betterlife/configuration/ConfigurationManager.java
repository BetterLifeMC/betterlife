package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.main.BetterLife;
import org.bukkit.ChatColor;


import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ConfigurationManager {

    private final MainConfigurationHandler ch;
    private final ParticleConfigurationHandler partch;
    public static final int CONFIG_VERSION = 2;

    @Inject
    public ConfigurationManager(MainConfigurationHandler ch, ParticleConfigurationHandler partch) {
        this.ch = ch;
        this.partch = partch;
    }
    /**
     * Enables & loads the configuration
     */
    public boolean enableConfiguration(BetterLife m) {
        m.saveDefaultConfig();
        partch.saveDefaultConfig();

        int version = ch.getCustomConfig().contains("version", true) ?
                ch.getCustomConfig().getInt("version") :
                -1;

        if (version != CONFIG_VERSION) {
            BetterLife.doBukkitLog(ChatColor.RED + "" + ChatColor.BOLD + "Config Version: " + version + " != " + CONFIG_VERSION);
            BetterLife.doBukkitLog(ChatColor.DARK_RED + "Config version outdated. Moving current config and saving a fresh config of the new version.");
            BetterLife.doBukkitLog(ChatColor.DARK_RED + "CONFIG SETTINGS WILL BE RESET. PLEASE TRANSLATE YOUR OLD CONFIG TO THE NEW FORMAT!!!");

            Path oldConfig = ch.getFile().toPath();
            try {
                Files.move(oldConfig, new File(m.getDataFolder(), "old_config.yml").toPath(),
                        REPLACE_EXISTING);
                Files.deleteIfExists(oldConfig);
            } catch (IOException e) {
                BetterLife.doBukkitLog(ChatColor.DARK_RED + "Failure moving old config. Rename it manually to get the latest version.");
            }
            m.saveDefaultConfig();
            return false;
        }
        return true;
    }

    public void reloadConfiguration() {
        ch.reloadCustomConfig();
        partch.reloadCustomConfig();
    }
}
