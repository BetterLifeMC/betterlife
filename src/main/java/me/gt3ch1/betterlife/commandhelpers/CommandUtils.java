package me.gt3ch1.betterlife.commandhelpers;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.configuration.ParticleConfigurationHandler;
import me.gt3ch1.betterlife.eventhelpers.PlayerTeleportHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

/**
 * Base class for BetterLifeCommands, and various other methods required to send messages to players, or to add tab completion for certain commands.
 * This class also stores all of the configuration data required to run the plugin.
 */
public class CommandUtils {

    // Initializes some important variables.
    public static Main m = Main.m;
    public static MainConfigurationHandler ch;
    public static ParticleConfigurationHandler partch;
    public static PlayerTeleportHelper teleportHelper;
    public static String betterLifeBanner = ChatColor.translateAlternateColorCodes('&', "&c[&3BetterLife&c] ") + ChatColor.RESET;
    public static String[] enabledTabCommands = {"trail", "toggledownfall", "bl", "eco", "home", "warp"};
    public static final int currentConfigVersion = 2;

    /**
     * Sends the sender a message.
     *
     * @param sender  The user to send the message to.
     * @param message The message to be sent, with color codes parsed.
     * @param banner  Whether or not to send the message with a BetterLife banner.
     */
    public static void sendMessage(CommandSender sender, String message, boolean banner) {
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', message);
        boolean player = sender instanceof Player;
        if (player) {
            if (banner) {
                sender.sendMessage(betterLifeBanner + coloredMessage);
            } else {
                sender.sendMessage(coloredMessage);
            }
        } else {
            sender.sendMessage(betterLifeBanner + (coloredMessage));
        }
    }

    /**
     * Sets a message to the sender saying that they do not have permissions.
     *
     * @param sender Sender to send the permission error message to.
     */
    public static void sendPermissionErrorMessage(CommandSender sender) {
        sendMessage(sender, "&4You do not have permissions!", false);
    }

    /**
     * Sends a help header
     *
     * @param sender      Who sent the message.
     * @param commandName The name of the command.
     * @param args        Arguments of the command.
     */
    public static void sendHelpMessage(CommandSender sender, String commandName, LinkedHashMap<String, String> args) {
        sendMessage(sender, "&b&lby GT3CH1 & Starmism", true);
        sendMessage(sender, "&6--== [ Available commands ] ==--", true);
        args.forEach((cmd, desc) -> {
            sendMessage(sender, "&d/" + commandName + " " + cmd + " &r| " + "&6" + desc, false);
        });
    }

    /**
     * Enables & loads the configuration
     */
    public static boolean enableConfiguration() {
        ch = new MainConfigurationHandler();
        partch = new ParticleConfigurationHandler();
        teleportHelper = new PlayerTeleportHelper();
        m.saveDefaultConfig();
        partch.saveDefaultConfig();

        int version;
        if (ch.getCustomConfig().contains("version", true)) {
            version = ch.getCustomConfig().getInt("version");
        } else {
            version = -1;
        }

        if (version != currentConfigVersion) {
            Main.doBukkitLog(ChatColor.RED + "" + ChatColor.BOLD + "Config Version: " + version + " != " + currentConfigVersion);
            Main.doBukkitLog(ChatColor.DARK_RED + "Config version outdated. Moving current config and saving a fresh config of the new version.");
            Main.doBukkitLog(ChatColor.DARK_RED + "CONFIG SETTINGS WILL BE RESET. PLEASE TRANSLATE YOUR OLD CONFIG TO THE NEW FORMAT!!!");

            Path oldConfig = ch.getFile().toPath();
            try {
                Files.move(oldConfig, new File(m.getDataFolder(), "old_config.yml").toPath(),
                    REPLACE_EXISTING);
                Files.deleteIfExists(oldConfig);
            } catch (IOException e) {
                Main.doBukkitLog(ChatColor.DARK_RED + "Failure moving old config. Rename it manually to get the latest version.");
            }
            m.saveDefaultConfig();
            return false;
        }
        return true;
    }

    /**
     * Disables all configuration helpers.
     */
    public static void disableConfiguration() {
        ch = null;
        partch = null;
    }

    /**
     * Reloads all configuration helpers
     */
    public static void reloadConfiguration() {
        m.reloadConfig();
        partch.reloadCustomConfig();
    }

    /**
     * @return TabCommands
     */
    public static String[] getEnabledTabCommands() {
        return enabledTabCommands;
    }
}
