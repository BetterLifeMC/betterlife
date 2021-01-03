package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BL extends BetterLifeCommands implements CommandExecutor {

    // Get the plugin from Main
    private static Main m = Main.m;

    /**
     * Handles the /bl command
     *
     * @param permission Permission required for the /bl command.
     * @param cs         The command sender.
     * @param c          The command.
     * @param label      The String version of the command.
     * @param args       The arguments to the command.
     */
    public BL(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);
    }

    /**
     * Sends the current version of the plugin to the sender.
     *
     * @param sender Who sent the command.
     */
    private static void sendVersion(CommandSender sender) {
        sendMessage(sender, "&7Version &6" + m.getDescription().getVersion() + " &7of BetterLife installed.", true);
    }

    /**
     * Reloads the configuration and sends messages to the sender.
     *
     * @param sender Who sent the commmand.
     */
    private static void reloadConfig(CommandSender sender) {
        sendMessage(sender, "&eConfiguration file reloading...", true);
        CommandUtils.reloadConfiguration();
        sendMessage(sender, "&aConfiguration file reloaded!", true);
    }

    /**
     * When /bl is sent
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cs, String command, String[] args) {

        switch (args.length) {
            case 0:
                sendVersion(sender);
                break;
            case 1:
                switch (args[0]) {
                    case "reload":
                        if (sender instanceof Player) {
                            Player p = (Player) sender;

                            if (p.hasPermission("betterlife.reload")) {
                                reloadConfig(p);
                            } else {
                                sendPermissionErrorMessage(p);
                            }
                        } else {
                            reloadConfig(sender);
                        }
                        break;

                    case "version":
                        sendVersion(sender);
                        break;

                    default:
                        return false;
                }
                return true;
            default:
                return false;
        }
        return true;
    }
}
