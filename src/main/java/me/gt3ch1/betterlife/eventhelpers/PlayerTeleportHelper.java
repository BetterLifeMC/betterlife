package me.gt3ch1.betterlife.eventhelpers;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.ConfigurationHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;

/**
 * @author gt3ch1
 * @author starmism
 * @version 12/2/20
 * Project betterlife
 */
public class PlayerTeleportHelper {

    private int checkerID;
    private int taskID;
    private Main m = Main.m;
    private ConfigurationHelper ch = CommandUtils.ch;

    /**
     * Teleports the given player to the location.
     *
     * @param player   Player to teleport.
     * @param location Location to teleport player.
     * @param home     The name of the home (used for command output).
     */
    public void teleportPlayer(Player player, Location location, String home) {
        int ticks = ch.getCustomConfig().getInt("home-countdown") * 20;

        sendMessage(player, "Teleporting to " + home + " in " + ticks / 20 + " seconds...", true);

        final Location initial = player.getLocation();

        //BL-Test
        if(Main.isTesting) {
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

}
