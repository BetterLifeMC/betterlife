package me.gt3ch1.betterlife.commands;

import java.util.LinkedHashMap;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_HOME;
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
    private int checkerID;
    private int taskID;
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
                if (homeList.size() == 1) {
                    home = homeList.get(listOfHomes[0]);
                    teleportPlayer(player, home, listOfHomes[0]);
                } else if (homeList.size() == 0) {
                    sendMessage(player, "&4You don't have any homes!", true);
                    return false;
                } else {
                    sendListOfHomes(player);
                    return true;
                }
                break;
            case 1:
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
                teleportPlayer(player, home, args[0]);
                break;
            //TODO: Maybe we should add a /home info {home name} ?
            case 2:
                switch (args[0]) {
                    case "set":
                        homeGetter.addHome(player, args[1]);
                        sendMessage(player, "&aHome &f" + args[1] + " &acreated!", true);
                        break;
                    case "del":
                        if (homeGetter.delHome(player, args[1]))
                            sendMessage(player, "&aHome &f" + args[1] + " &adeleted!", true);
                        else
                            sendMessage(player, "&4Home not found!", true);
                        break;
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
     * Teleports the given player to the location.
     *
     * @param player   Player to teleport.
     * @param location Location to teleport player.
     * @param home     The name of the home (used for command output).
     */
    private void teleportPlayer(Player player, Location location, String home) {
        int ticks = ch.getCustomConfig().getInt("home-countdown") * 20;

        sendMessage(player, "Teleporting to " + home + " in " + ticks / 20 + " seconds...", true);

        final Location initial = player.getLocation();

        //BL-Test
        if(m.isTesting()) {
            player.teleport(location);
            return;
        }

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        BukkitTask task = scheduler.runTaskLater(m, () -> {
            player.teleport(location);
            sendMessage(player, "&cTeleportation successful!", true);
        }, ticks);

        BukkitTask checker = scheduler.runTaskTimer(m, () -> {
            if (!scheduler.isQueued(task.getTaskId())) {
                cancelChecker();
            }
            Location cur = player.getLocation();

            if (checkPlayerHasMoved(initial, cur)) {
                if (scheduler.isQueued(task.getTaskId())) {
                    cancelTask();
                    cancelChecker();
                    sendMessage(player, "&4Teleportation cancelled.", true);
                }
            }
        }, 10, 10);

        this.checkerID = checker.getTaskId();
        this.taskID = task.getTaskId();
    }

    /**
     * Cancels the teleport player checker.
     */
    private void cancelChecker() {
        Bukkit.getServer().getScheduler().cancelTask(this.checkerID);
    }

    /**
     * Cancels the current task.
     */
    private void cancelTask() {
        Bukkit.getServer().getScheduler().cancelTask(this.taskID);
    }

    /**
     * Checks whether or not the player has moved.
     *
     * @param initial Initial location of the player.
     * @param cur     Current location of the player.
     * @return True if the player has moved, false otherwise.
     */
    private boolean checkPlayerHasMoved(Location initial, Location cur) {
        double maxX, maxZ, minX, minZ;

        maxX = initial.getX() + 0.5;
        maxZ = initial.getZ() + 0.5;

        minX = initial.getX() - 0.5;
        minZ = initial.getZ() - 0.5;

        return !(cur.getX() >= minX && cur.getX() <= maxX
                && cur.getZ() >= minZ && cur.getZ() <= maxZ);
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
