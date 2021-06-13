package me.gt3ch1.betterlife.commandhelpers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

/**
 * Class contains helper methods for Commands, primarily involved with sending messages that are formatted prettily.
 */
public class CommandUtils {

    public static final String BANNER =
            ChatColor.translateAlternateColorCodes('&', "&c[&3BetterLife&c] ") + ChatColor.RESET;

    /**
     * Sends the sender a message.
     *
     * @param sender  The user to send the message to.
     * @param message The message to be sent, with color codes parsed.
     * @param banner  Whether or not to send the message with a BetterLife banner.
     */
    public static void sendMessage(CommandSender sender, String message, boolean banner) {
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', message);
        if (sender instanceof Player) {
            if (banner) {
                sender.sendMessage(BANNER + coloredMessage);
            } else {
                sender.sendMessage(coloredMessage);
            }
        } else {
            sender.sendMessage(BANNER + (coloredMessage));
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
    @Deprecated
    public static void sendHelpMessage(CommandSender sender, String commandName, LinkedHashMap<String, String> args) {
        sendMessage(sender, "&b&lby GT3CH1 & Starmism", true);
        sendMessage(sender, "&6--== [ Available commands ] ==--", true);
        args.forEach((cmd, desc) -> {
            sendMessage(sender, "&d/" + commandName + " " + cmd + " &r| " + "&6" + desc, false);
        });
    }
}
