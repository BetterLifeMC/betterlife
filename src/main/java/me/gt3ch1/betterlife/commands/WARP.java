package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_WARP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WARP extends BetterLifeCommands implements CommandExecutor {

    private BL_WARP warps = Main.bl_warp;

    /**
     * Initializes the home command.
     *
     * @param permission Permission required to use /home
     * @param cs         Sender of the command.
     * @param c          The command itself.
     * @param label      The string version of the command.
     * @param args       The arguments of the command.
     */
    public WARP(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        Main.bl_warp.getWarps();
        this.onCommand(cs, c, label, args);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "You need to be a player!", true);
            return false;
        }
        switch (args.length) {
            case 0:
                sendMessage(sender, "Invalid usage!", true);
                return false;
            case 1:
                if (args[0].equalsIgnoreCase("list")) {
                    sendMessage(player, ChatColor.LIGHT_PURPLE + "Available warps:", true);
                    for (String names : Main.bl_warp.getWarps().keySet())
                        if (sender.hasPermission("betterlife.warps." + names.toLowerCase()))
                            sendMessage(player, ChatColor.AQUA + names, false);

                    return true;
                }
                if (warps.getWarps().size() > 0 && warps.getWarps().containsKey(args[0])) {
                    if (sender.hasPermission("betterlife.warps." + args[0])) {
                        teleportHelper.teleportPlayer((Player) sender, warps.getWarps().get(args[0]), args[0]);
                        return true;
                    }
                }
            case 2:
                if (args[0].equalsIgnoreCase("set")) {
                    if (sender.hasPermission("betterlife.warps.set")) {
                        Main.bl_warp.addWarp((Player) sender, args[1]);
                        sendMessage(sender, "Warp " + args[1] + " sat to your location", true);
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("del")) {
                    if (sender.hasPermission("betterlife.warps.del")) {
                        Main.bl_warp.delWarp((Player) sender, args[1]);
                        sendMessage(sender, "Warp " + args[1] + " has been delete.", true);
                        return true;
                    }
                }
            default:
                return false;
        }
    }
}
