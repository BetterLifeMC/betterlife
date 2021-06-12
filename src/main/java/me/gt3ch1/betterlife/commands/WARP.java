package me.gt3ch1.betterlife.commands;

import java.util.ArrayList;
import me.gt3ch1.betterlife.Main.BetterLife;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_WARP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WARP extends BetterLifeCommands implements CommandExecutor {

    private BL_WARP warps = BetterLife.bl_warp;

    /**
     * Initializes the warp command.
     *
     * @param permission Permission required to use /warp
     * @param cs         Sender of the command.
     * @param c          The command itself.
     * @param label      The string version of the command.
     * @param args       The arguments of the command.
     */
    public WARP(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);
    }

    /**
     * Runs the /warp command
     *
     * @param sender  Sender of the command.
     * @param c       The command itself.
     * @param command The string version of the command.
     * @param args    The arguments of the command.
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "You need to be a player!", true);
            return false;
        }

        ArrayList<String> availableWarps = new ArrayList<>();

        for (String names : BetterLife.bl_warp.getWarps().keySet()) {
            if (sender.hasPermission("betterlife.warp." + names.toLowerCase()) || sender.hasPermission("betterlife.warp.*")) {
                availableWarps.add(names);
            }
        }

        Player player = (Player) sender;
        switch (args.length) {
            case 0:
                sendMessage(sender, "&4Invalid usage!", true);
                return false;
            case 1:
                if (args[0].equalsIgnoreCase("list")) {
                    if (availableWarps.size() == 0) {
                        sendMessage(player, "&4No available warps.", true);
                        return true;
                    }
                    sendMessage(player, "&dAvailable warps:", true);
                    for (String warp : availableWarps) {
                        sendMessage(player, "&b" + warp, false);
                    }
                    return true;
                }
                if (warps.getWarps().size() > 0 && warps.getWarps().containsKey(args[0])) {
                    if (sender.hasPermission("betterlife.warp." + args[0]) || sender.hasPermission("betterlife.warp.*")) {
                        teleportHelper.teleportPlayer(player, warps.getWarps().get(args[0]), args[0]);
                        return true;
                    }
                }
            case 2:
                if (args[0].equalsIgnoreCase("set")) {
                    if (sender.hasPermission("betterlife.warp.set") || sender.hasPermission("betterlife.warp.*")) {
                        BetterLife.bl_warp.setWarp(player, args[1]);
                        sendMessage(sender, "&bWarp &e" + args[1] + "&b set to your current location!", true);
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("del")) {
                    if (sender.hasPermission("betterlife.warp.del") || sender.hasPermission("betterlife.warp.*")) {
                        BetterLife.bl_warp.delWarp(args[1]);
                        sendMessage(sender, "&bWarp &e" + args[1] + "&b has been deleted.", true);
                        return true;
                    }
                }
            default:
                return false;
        }
    }
}
