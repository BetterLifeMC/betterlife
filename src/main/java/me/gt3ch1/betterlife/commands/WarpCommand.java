package me.gt3ch1.betterlife.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import me.gt3ch1.betterlife.data.BL_WARP;
import me.gt3ch1.betterlife.eventhelpers.PlayerTeleportHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;

public class WarpCommand implements BetterLifeCommand {

    private final BL_WARP blWarp;
    private final PlayerTeleportHelper teleportHelper;

    @Inject
    public WarpCommand(BL_WARP blWarp, PlayerTeleportHelper teleportHelper) {
        this.blWarp = blWarp;
        this.teleportHelper = teleportHelper;
    }

    @Override
    public String getPermission() {
        return "betterlife.warp";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Allows players to teleport to warps.";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sendMessage(sender, "You need to be a player!", true);
            return false;
        }

        ArrayList<String> availableWarps = new ArrayList<>();

        for (String names : blWarp.getWarps().keySet()) {
            if (sender.hasPermission("betterlife.warp." + names.toLowerCase())) {
                availableWarps.add(names);
            }
        }

        switch (args.length) {
            case 0 -> sendMessage(sender, "&4Invalid usage!", true);
            case 1 -> {
                if (args[0].equalsIgnoreCase("list")) {
                    if (availableWarps.size() == 0) {
                        sendMessage(player, "&4No available warps.", true);
                        return true;
                    }
                    sendMessage(player, "&dAvailable warps:", true);
                    for (String warp : availableWarps) {
                        sendMessage(player, "&b" + warp, false);
                    }
                }
                if (blWarp.getWarps().size() > 0 && blWarp.getWarps().containsKey(args[0])) {
                    if (sender.hasPermission("betterlife.warp." + args[0]) || sender.hasPermission("betterlife.warp.*")) {
                        teleportHelper.teleportPlayer(player, blWarp.getWarps().get(args[0]), args[0]);
                    }
                }
            }
            case 2 -> {
                if (args[0].equalsIgnoreCase("set")) {
                    if (sender.hasPermission("betterlife.warp.set") || sender.hasPermission("betterlife.warp.*")) {
                        blWarp.setWarp(player, args[1]);
                        sendMessage(sender, "&bWarp &e" + args[1] + "&b set to your current location!", true);
                    }
                }
                if (args[0].equalsIgnoreCase("del")) {
                    if (sender.hasPermission("betterlife.warp.del") || sender.hasPermission("betterlife.warp.*")) {
                        blWarp.delWarp(args[1]);
                        sendMessage(sender, "&bWarp &e" + args[1] + "&b has been deleted.", true);
                    }
                }
            }
        }
        return true;
    }


    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        final List<String> subCommands = new ArrayList<>();
        if (!(sender instanceof Player player)) {
            return Collections.emptyList();
        }

        switch (args.length) {
            case 1 -> {
                if (player.hasPermission("betterlife.warp")) {
                    for (String warpName : blWarp.getWarps().keySet()) {
                        if (player.hasPermission("betterlife.warp." + warpName)) {
                            subCommands.add(warpName);
                        }
                    }
                }

                var warpCmds = Arrays.asList("set", "del", "list");
                for (String cmd : warpCmds) {
                    if (player.hasPermission("betterlife.warp." + cmd)) {
                        subCommands.add(cmd);
                    }
                }
            }
            case 2 -> {
                if (player.hasPermission("betterlife.warp.del")) {
                    for (String warpName : blWarp.getWarps().keySet()) {
                        if (player.hasPermission("betterlife.warp." + warpName)) {
                            subCommands.add(warpName);
                        }
                    }
                }
            }
        }
        return subCommands;
    }
}
