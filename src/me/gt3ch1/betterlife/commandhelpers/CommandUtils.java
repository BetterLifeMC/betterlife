package me.gt3ch1.betterlife.commandhelpers;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.configuration.ConfigurationHelper;
import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.configuration.ParticleConfigurationHandler;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Base class for BetterLifeCommands, and various other methods required to send messages to players,
 * or to add tab completion for certain commands.  This class also stores all of the configuration
 * data required to run the plugin.
 */
public class CommandUtils {
    // Initializes some important variables.
    private static Main m = Main.m;
    public static MainConfigurationHandler ch;
    public static PlayerConfigurationHandler pch;
    public static ParticleConfigurationHandler partch;
    public static String betterLifeBanner = ChatColor.translateAlternateColorCodes('&', "&c[[&9BetterLife&c]] ") + ChatColor.RESET;
    public static String[] enabledTabCommands = {"trail", "toggledownfall", "bl", "eco"};
    public static PlayerConfigurationHandler playerConfig = pch;
    public static Object[] playerUUIDs;

    /**
     * Send the sender the BetterLife banner.
     *
     * @param sender
     * @param message
     */
    public static void sendBannerMessage(CommandSender sender, String message) {
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', message);
        if (sender instanceof Player) {
            sender.sendMessage(betterLifeBanner + coloredMessage);
        } else {
            sender.sendMessage(ChatColor.stripColor(coloredMessage));
        }
    }

    /**
     * Sends the sender a message minus the BetterLife banner.
     *
     * @param sender
     * @param message
     */
    public static void sendMessage(CommandSender sender, String message) {
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', message);
        if (sender instanceof Player) {
            sender.sendMessage(coloredMessage);
        } else {
            sender.sendMessage(ChatColor.stripColor(coloredMessage));
        }
    }

    public static void sendPermissionErrorMessage(CommandSender sender) {
        sendMessage(sender, ChatColor.translateAlternateColorCodes('&', "&4You do not have permissions!"));
    }

    /**
     * Sends a help header
     *
     * @param sender
     * @param commandName
     * @param args
     */
    public static void sendHelpMessage(CommandSender sender, String commandName, LinkedHashMap<String, String> args) {
        sendBannerMessage(sender, "&b&lby GT3CH1 & Starmism");
        sendBannerMessage(sender, "&6--== [ Available commands ] ==--");
        args.forEach((cmd, desc) -> {
            sendBannerMessage(sender, "&d/" + commandName + " " + cmd + " &r| " + "&6" + desc);
        });
    }

    /**
     * Enables & loads the configuration
     */
    public static void enableConfiguration() {
        // TODO: Setup a scheme to recognize out-of-date config files,
        // move them to old files, and generate current versions
        pch = new PlayerConfigurationHandler();
        partch = new ParticleConfigurationHandler();
        m.saveDefaultConfig();
        pch.saveCustomConfig();
        partch.saveDefaultConfig();
        playerUUIDs = pch.antiGriefLocation1PerPlayer.keySet().toArray();
    }

    /**
     * Enables the main configuration.
     */
    // Please do not touch this.  I need to load the main
    // config before everything else.
    public static void enableMainConfiguration(){
        ch = null;
        ch = new MainConfigurationHandler();
    }

    /**
     * Disables all configuration helpers.
     */
    public static void disableConfiguration() {
        ch = null;
        pch = null;
        partch = null;
    }

    /**
     * Reloads all configuration helpers
     */
    public static void reloadConfiguration() {
        m.reloadConfig();
        pch.reloadCustomConfig();
        partch.reloadCustomConfig();
    }

    /**
     * @return TabCommands
     */
    public static String[] getEnabledTabCommands() {
        return enabledTabCommands;
    }


    /**
     * Converts a String into a location.
     * @param antigriefLocation Location as string
     * @param playerUUID Player UUID
     * @return parsed location
     */
    public static Location parseLocation(String antigriefLocation, UUID playerUUID, ConfigurationHelper ch) {

        String locationString1 = ch.get("antigrief.location." + antigriefLocation, playerUUID).toString()
                .replace("Location{world=CraftWorld{name", "").replace("}", "");

        String[] splitLocString1 = locationString1.split(",");
        String[] newLoc1 = new String[splitLocString1.length];

        for (int x = 0; x < splitLocString1.length; x++)
            newLoc1[x] = splitLocString1[x].split("=")[1];

        return new Location(Bukkit.getWorld(newLoc1[0]), Double.parseDouble(newLoc1[1]), Double.parseDouble(newLoc1[2]), Double.parseDouble(newLoc1[3]), Float.parseFloat(newLoc1[4]), Float.parseFloat(newLoc1[5]));
    }
}
