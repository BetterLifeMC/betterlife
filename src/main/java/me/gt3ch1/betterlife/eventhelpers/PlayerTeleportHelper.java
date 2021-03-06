package me.gt3ch1.betterlife.eventhelpers;

import me.gt3ch1.betterlife.main.BetterLife;
import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.configuration.YamlConfigurationHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import javax.inject.Inject;
import javax.inject.Singleton;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;

/**
 * @author Starmism
 */
@Singleton
public class PlayerTeleportHelper {

    private int checkerID;
    private int taskID;
    private final BetterLife m;
    private final YamlConfigurationHandler ch;

    @Inject
    public PlayerTeleportHelper(BetterLife m, MainConfigurationHandler ch) {
        this.m = m;
        this.ch = ch;
    }

    public void teleportPlayer(Player player, Location location, String home) {
        teleportPlayerHelper(player, location, home, false);
    }

    public void teleportPlayer(Player player, Player otherPlayer) {
        teleportPlayerHelper(player, otherPlayer.getLocation(), otherPlayer.getName(), false);
    }

    public void teleportPlayer(Player player, Location location, String home, boolean forceTeleport) {
        teleportPlayerHelper(player, location, home, forceTeleport);
    }


    /**
     * Teleports the given player to the location.
     *
     * @param player   Player to teleport.
     * @param location Location to teleport player.
     * @param home     The name of the home (used for command output).
     */
    private void teleportPlayerHelper(Player player, Location location, String home, boolean forceTeleport) {
        int ticks = ch.getCustomConfig().getInt("home-countdown") * 20;
        if (home != null) {
            sendMessage(player, "&bTeleporting to &e" + home + "&b in &a" + ticks / 20 + "&b seconds...", true);
        }

        final Location initial = player.getLocation();

        // Force teleport means instant teleport
        if (forceTeleport) {
            player.teleport(location);
            return;
        }

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        BukkitTask task = scheduler.runTaskLater(m, () -> {
            player.teleport(location);
            sendMessage(player, "&aTeleportation successful!", true);
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
