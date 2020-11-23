package me.gt3ch1.betterlife.commands;

import java.util.LinkedHashMap;
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

public class HOME extends BetterLifeCommands implements CommandExecutor {
    private int checkerID;
    private int taskID;
    BL_HOME homeGetter = new BL_HOME();

    public HOME(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);
    }

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
                    return false;
                }
                break;
            case 1:
                home = homeList.get(args[0]);
                if (home == null) {
                    sendMessage(player, "&4Home not found!", true);
                    sendListOfHomes(player);
                    return false;
                }
                teleportPlayer(player, home, args[0]);
                break;
            default:
                sendMessage(sender, "&4Too many arguments!", true);
                return false;
        }
        return true;
    }

    private void teleportPlayer(Player player, Location location, String home) {
        int ticks = ch.getCustomConfig().getInt("home-countdown") * 20;

        sendMessage(player, "Teleporting to " + home + " in " + ticks / 20 + " seconds...", true);

        final Location initial = player.getLocation();

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
                    cancelTask(player);
                    cancelChecker();
                    sendMessage(player, "&4Teleportation cancelled.", true);
                }
            }
        }, 10, 10);

        this.checkerID = checker.getTaskId();
        this.taskID = task.getTaskId();
    }

    private void cancelChecker() {
        Bukkit.getServer().getScheduler().cancelTask(this.checkerID);
    }

    private void cancelTask(Player player) {
        Bukkit.getServer().getScheduler().cancelTask(this.taskID);
    }

    private boolean checkPlayerHasMoved(Location initial, Location cur) {
        double maxX, maxZ, minX, minZ;

        maxX = initial.getX() + 0.5;
        maxZ = initial.getZ() + 0.5;

        minX = initial.getX() - 0.5;
        minZ = initial.getZ() - 0.5;

        return !(cur.getX() >= minX && cur.getX() <= maxX
            && cur.getZ() >= minZ && cur.getZ() <= maxZ);
    }

    private void sendListOfHomes(Player player) {
        LinkedHashMap<String, Location> homeList = homeGetter.getHomes(player.getUniqueId());
        String[] listOfHomes = homeList.keySet().toArray(new String[0]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listOfHomes.length; i++) {
            sb.append(listOfHomes[i]);
            if (i < listOfHomes.length -1) {
                sb.append(", ");
            }
        }
        String prettyHomes = sb.toString();

        sendMessage(player, "Valid homes: " + prettyHomes, true);
    }
}
