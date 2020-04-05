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
     * @param permission
     * @param cs
     * @param c
     * @param label
     * @param args
     */
    public BL(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);
    }

    /**
     * When /bl is sent
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cs, String command, String[] args) {
    	// Test the args length
        switch(args.length) {
        	// If there is nothing, send the version.
            case 0:
                sendVersion(sender);
                break;
            // If the args are 1
            case 1:
            	// Test the arguments
                switch(args[0]) {
                	// If it is reload
                    case "reload":
                    	// And a player
                        if(sender instanceof Player) {
                            Player p = (Player) sender;
                            // And has permission, reload the config.
                            if (p.hasPermission("betterlife.reload")) {
                                reloadConfig(p);
                            } else {
                                sendBannerMessage(p, "&4You don't have permission!");
                            }
                        } else {
                        	// Must be the console...
                            reloadConfig(sender);
                        }
                        break;
                    // If it is version, send the version info.
                    case "version":
                        sendVersion(sender);
                        break;
                    // Do nothing.
                    default:
                        return false;
                }
                return true;
            // Do nothing.
            default:
                return false;
        }
        return true;
    }

    /**
     * Sends the current version of the plugin to the sender.
     * @param sender
     */
    private static void sendVersion(CommandSender sender) {
        sendBannerMessage(sender, "&7Version &6" + m.getDescription().getVersion() + " &7of BetterLife installed.");
    }

    /**
     * Reloads the configuration and sends messages to the sender.
     * @param sender
     */
    private static void reloadConfig(CommandSender sender) {
        sendBannerMessage(sender, "&eConfiguration file reloading...");
        CommandUtils.reloadConfiguration(m);
        sendBannerMessage(sender, "&aConfiguration file reloaded!");
    }
}
