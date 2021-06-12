package me.gt3ch1.betterlife.commands;

import java.util.HashSet;
import java.util.UUID;
import me.gt3ch1.betterlife.Main.BetterLife;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TRAIL extends BetterLifeCommands implements CommandExecutor {

    BL_PLAYER playerGetter = BetterLife.bl_player;

    /**
     * Handles the command /trail
     *
     * @param permission Permission required to use /home
     * @param cs         Sender of the command.
     * @param c          The command itself.
     * @param label      The string version of the command.
     * @param args       The arguments of the command.
     */
    public TRAIL(String permission, CommandSender cs, Command c, String label, String[] args) {

        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);

    }

    /**
     * Runs the /trail command
     *
     * @param sender  Sender of the command.
     * @param c       The command itself.
     * @param command The string version of the command.
     * @param args    The arguments of the command.
     * @return True if the command was successful.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String command, String[] args) {

        var allowedParticles = new HashSet<>(CommandUtils.partch.getCustomConfig().getStringList("particle"));

        if (args.length == 0) {
            sendHelpMessage(sender, "trail", helpHash);
            return true;
        }

        switch (args.length) {
            case 1 -> {
                switch (args[0]) {
                    case "list" -> {
                        if (sender instanceof Player player) {
                            sendMessage(player, "&7The currently enabled trails are: ", true);

                            for (String allowedParticle : allowedParticles) {
                                if (player.hasPermission(
                                        "betterlife.trails.particle." + allowedParticle.toLowerCase())
                                        || player.hasPermission("betterlife.trails.particle.*")) {
                                    sendMessage(player, "&d" + allowedParticle, true);
                                }
                            }
                        } else {
                            sendMessage(sender, "&7The currently enabled trails are: ", true);
                            for (String allowedParticle : allowedParticles) {
                                sendMessage(sender, "&d" + allowedParticle, true);
                            }
                        }
                        return true;
                    }
                    case "toggle" -> {
                        if (sender instanceof Player player) {
                            UUID playerUUID = player.getUniqueId();
                            if (player.hasPermission("betterlife.trail.toggle")) {
                                boolean trailsEnabled = playerGetter.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER);
                                playerGetter.setPlayerToggle(playerUUID, BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER);
                                String toggleState = trailsEnabled ? "&cdisabled" : "&aenabled";
                                sendMessage(player, "&7Trail " + toggleState + "&7!", true);
                            }
                        } else {
                            // TODO: Add console support for attaching a username target as arg[1]
                            sendMessage(sender, "&4You must be a player to use this command!", true);
                        }
                        return true;
                    }
                    case "trail" -> {
                        sendHelpMessage(sender, "trail", helpHash);
                        return true;
                    }
                    default -> {
                        return false;
                    }
                }
            }
            case 2 -> {
                if (args[0].equalsIgnoreCase("set")) {
                    if (sender instanceof Player player) {
                        UUID playerUUID = player.getUniqueId();

                        if (allowedParticles.contains(args[1].toUpperCase()) && (
                                player.hasPermission("betterlife.trail.particle." + args[1].toLowerCase())
                                        || player.hasPermission("betterlife.trail.particle.*"))) {

                            playerGetter.setPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER, args[1].toUpperCase());
                            sendMessage(player, "&7Your trail is now set to: &6" + args[1].toUpperCase(), true);

                        } else if (!player.hasPermission("betterlife.trail.particle." + args[1].toLowerCase())) {
                            sendMessage(player, "&4You need permission to use that trail!", true);
                        } else if (!allowedParticles.contains(args[1].toUpperCase())) {
                            sendMessage(player, "&4That particle is disabled on this server! Try /trail list to see the list of available trails.",
                                    true);
                        }
                    } else {
                        // TODO: Add console support for attaching a username target as arg[2]
                        sendMessage(sender, "&4You must be a player to use this command!", true);
                    }
                }
                return true;
            }
            default -> {
                sendHelpMessage(sender, "trail", helpHash);
                return true;
            }
        }
    }
}