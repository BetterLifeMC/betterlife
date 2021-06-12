package me.gt3ch1.betterlife.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import me.gt3ch1.betterlife.Main.BetterLife;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_HOME;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the /home command for BetterLife.
 */
public class HOME extends BetterLifeCommands implements CommandExecutor {

    BL_HOME homeGetter = BetterLife.bl_home;

    /**
     * Initializes the home command.
     *
     * @param permission Permission required to use /home
     * @param cs         Sender of the command.
     * @param c          The command itself.
     * @param label      The string version of the command.
     * @param args       The arguments of the command.
     */
    public HOME(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);
    }

    /**
     * Runs the command.
     *
     * @param sender  Sender of the command.
     * @param c       The command itself.
     * @param command The string version of the command.
     * @param args    The arguments of the command.
     * @return True if the command executed successfully.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String command, String[] args) {
        if (!(sender instanceof Player player)) {
            sendMessage(sender, "&4You must be a player to use this command!", false);
            return true;
        }

        Location home;

        // Get the list of homes and location pairs, then get just an array of the names
        LinkedHashMap<String, Location> homeList = homeGetter.getHomes(player.getUniqueId());
        var listOfHomes = new ArrayList<>(homeList.keySet());

        switch (args.length) {
            case 0 -> {
                if (sender.hasPermission(getPermission())) {
                    if (homeList.size() == 1) {
                        home = homeList.get(listOfHomes.get(0));
                        teleportHelper.teleportPlayer(player, home, listOfHomes.get(0), false);
                    } else if (homeList.size() == 0) {
                        sendMessage(player, "&4You don't have any homes!", true);
                        return true;
                    } else {
                        sendListOfHomes(player, player);
                        return true;
                    }
                } else {
                    sendPermissionErrorMessage(sender);
                }
            }
            case 1 -> {
                if (player.hasPermission(getPermission())) {
                    if (args[0].equalsIgnoreCase("list")) {
                        sendListOfHomes(player, player);
                        return true;
                    }
                    home = homeList.get(args[0]);
                    if (home == null) {
                        sendMessage(player, "&4Home not found!", true);
                        sendListOfHomes(player, player);
                        return true;
                    }
                    teleportHelper.teleportPlayer(player, home, args[0], false);
                } else {
                    sendPermissionErrorMessage(sender);
                }
            }
            case 2 -> {
                switch (args[0]) {
                    case "set" -> {
                        if (player.hasPermission(getPermission())) {
                            homeGetter.addHome(player, args[1]);
                            sendMessage(player, "&aHome &f" + args[1] + " &acreated!", true);
                        } else {
                            sendPermissionErrorMessage(player);
                        }
                    }
                    case "del" -> {
                        if (player.hasPermission(getPermission())) {
                            if (homeGetter.delHome(player, args[1])) {
                                sendMessage(player, "&aHome &f" + args[1] + " &adeleted!", true);
                            } else {
                                sendMessage(player, "&4Home not found!", true);
                            }
                        } else {
                            sendPermissionErrorMessage(sender);
                        }
                    }
                    case "list" -> {
                        if (player.hasPermission(getPermission())) {
                            for (OfflinePlayer ofp : Bukkit.getOfflinePlayers()) {
                                if (args[1].equals(ofp.getName())) {
                                    sendListOfHomes(ofp, player);
                                    return true;
                                }
                            }
                            sendMessage(sender, "&4Invalid player.", true);
                        }
                    }
                    default -> sendMessage(sender, "&4Invalid option.", true);
                }
            }
            default -> {
                sendMessage(sender, "&4Too many arguments!", true);
                return false;
            }
        }
        return true;
    }

    /**
     * Sends the target a list of the homes of the source player.
     *
     * @param source Player to get the list of homes from.
     * @param target Player to send a list of homes to.
     */
    private void sendListOfHomes(OfflinePlayer source, Player target) {
        var listOfHomes = homeGetter.getHomes(source.getUniqueId()).keySet();
        sendMessage(target, "Valid homes: " + String.join(", ", listOfHomes), true);
    }
}
