package me.gt3ch1.betterlife.commands;

import java.util.LinkedHashMap;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_HOME;
import me.gt3ch1.betterlife.eventhelpers.PlayerTeleportHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

/**
 * This class represents the /home command for BetterLife.
 */
public class HOME extends BetterLifeCommands implements CommandExecutor {

    BL_HOME homeGetter = Main.bl_home;

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
    public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "&4You must be a player to use this command!", false);
            return false;
        }

        Player player = (Player) sender;
        Location home;

        // Get the list of homes and location pairs, then get just an array of the names
        LinkedHashMap<String, Location> homeList = homeGetter.getHomes(player.getUniqueId());
        String[] listOfHomes = homeList.keySet().toArray(new String[0]);

        switch (args.length) {
            case 0:
                if (sender.hasPermission(getPermission()) || player.hasPermission(getPermission() + ".*")) {
                    if (homeList.size() == 1) {
                        home = homeList.get(listOfHomes[0]);
                        teleportHelper.teleportPlayer(player, home, listOfHomes[0],false);
                    } else if (homeList.size() == 0) {
                        sendMessage(player, "&4You don't have any homes!", true);
                        return false;
                    } else {
                        sendListOfHomes(player);
                        return true;
                    }
                } else
                    sendPermissionErrorMessage(sender);
                break;
            case 1:
                if (player.hasPermission(getPermission()) || player.hasPermission(getPermission() + ".*")) {
                    if (args[0].equalsIgnoreCase("list")) {
                        sendListOfHomes(player);
                        return true;
                    }
                    home = homeList.get(args[0]);
                    if (home == null) {
                        sendMessage(player, "&4Home not found!", true);
                        sendListOfHomes(player);
                        return false;
                    }
                    teleportHelper.teleportPlayer(player, home, args[0],false);
                    break;
                } else
                    sendPermissionErrorMessage(sender);
            case 2:
                switch (args[0]) {
                    case "set":
                        if (player.hasPermission(getPermission()) || player.hasPermission(getPermission() + ".*")) {
                            homeGetter.addHome(player, args[1]);
                            sendMessage(player, "&aHome &f" + args[1] + " &acreated!", true);
                        } else
                            sendPermissionErrorMessage(player);
                        break;
                    case "del":
                        if (player.hasPermission(getPermission()) || player.hasPermission(getPermission() + ".*")) {

                            if (homeGetter.delHome(player, args[1]))
                                sendMessage(player, "&aHome &f" + args[1] + " &adeleted!", true);
                            else
                                sendMessage(player, "&4Home not found!", true);
                            break;
                        } else
                            sendPermissionErrorMessage(sender);
                    default:
                        sendMessage(sender, "&4Invalid option.", true);
                        break;
                }
                break;
            default:
                sendMessage(sender, "&4Too many arguments!", true);
                return false;
        }
        return true;
    }

    /**
     * Sends the given player a list of all their homes.
     *
     * @param player Player to send a list of homes to.
     */
    private void sendListOfHomes(Player player) {
        LinkedHashMap<String, Location> homeList = homeGetter.getHomes(player.getUniqueId());
        String[] listOfHomes = homeList.keySet().toArray(new String[0]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listOfHomes.length; i++) {
            sb.append(listOfHomes[i]);
            if (i < listOfHomes.length - 1) {
                sb.append(", ");
            }
        }
        String prettyHomes = sb.toString();

        sendMessage(player, "Valid homes: " + prettyHomes, true);
    }
}
