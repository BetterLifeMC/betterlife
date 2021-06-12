package me.gt3ch1.betterlife.commands;

import java.util.*;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import me.gt3ch1.betterlife.configuration.ParticleConfigurationHandler;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;

public class TrailCommand implements BetterLifeCommand {

    BL_PLAYER blPlayer;
    ParticleConfigurationHandler partch;

    @Inject
    public TrailCommand(BL_PLAYER blPlayer, ParticleConfigurationHandler partch) {
        this.blPlayer = blPlayer;
        this.partch = partch;
    }

    @Override
    public String getPermission() {
        return "betterlife.trail";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Allows you to have a pretty particle trail!";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        var allowedParticles = new HashSet<>(partch.getCustomConfig().getStringList("particle"));

        if (args.length == 0) {
            //sendHelpMessage(sender, "trail", helpHash);
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
                    }
                    case "toggle" -> {
                        if (sender instanceof Player player) {
                            UUID playerUUID = player.getUniqueId();
                            if (player.hasPermission("betterlife.trail.toggle")) {
                                boolean trailsEnabled = blPlayer.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER);
                                blPlayer.setPlayerToggle(playerUUID, BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER);
                                String toggleState = trailsEnabled ? "&cdisabled" : "&aenabled";
                                sendMessage(player, "&7Trail " + toggleState + "&7!", true);
                            }
                        } else {
                            // TODO: Add console support for attaching a username target as arg[1]
                            sendMessage(sender, "&4You must be a player to use this command!", true);
                        }
                    }
                    case "trail" -> {
                        //sendHelpMessage(sender, "trail", helpHash);
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

                            blPlayer.setPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER, args[1].toUpperCase());
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
            }
            default -> {
                //sendHelpMessage(sender, "trail", helpHash);
                return true;
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
                var trailCmds = Arrays.asList("set", "list", "help", "toggle");
                for (String cmd : trailCmds) {
                    if (player.hasPermission("betterlife.trail." + cmd)) {
                        subCommands.add(cmd);
                    }
                }
            }
            case 2 -> {
                // If it is set, return the list of currently enabled particles,
                // and append them to the tablist list.
                if (args[0].equalsIgnoreCase("set")) {
                    for (String particle : partch.getCustomConfig().getStringList("particle")) {
                        if (player.hasPermission("betterlife.trail.particle." + particle.toLowerCase())) {
                            subCommands.add(particle);
                        }
                    }
                }
            }
        }
        return subCommands;
    }
}