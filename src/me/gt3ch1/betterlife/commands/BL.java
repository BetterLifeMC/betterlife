package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BL extends BetterLifeCommands implements CommandExecutor {

    public static Main m = Main.m;
    public BL(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cs, String command, String[] args) {
        switch(args.length) {
            case 0:
                sendVersion(sender);
                break;
            case 1:
                switch(args[0]) {
                    case "reload":
                        if(sender instanceof Player) {
                            Player p = (Player) sender;
                            if (p.hasPermission("betterlife.reload")) {
                                reloadConfig(p);
                            } else {
                                sendBannerMessage(p, ChatColor.DARK_RED + "You don't have permission!");
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

    private static void sendVersion(CommandSender sender) {
        sendBannerMessage(sender, ChatColor.GRAY + "Version " + ChatColor.GOLD + m.getDescription().getVersion() + ChatColor.GRAY + " of BetterLife installed.");
    }

    private static void reloadConfig(CommandSender sender) {
        sendBannerMessage(sender, ChatColor.RED + "Configuration file reloading...");
        CommandUtils.reloadConfiguration(m);
        sendBannerMessage(sender, ChatColor.GREEN + "Configuration file reloaded!");
    }
}
