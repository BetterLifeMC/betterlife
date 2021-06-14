package me.gt3ch1.betterlife.commands;

import java.util.*;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import me.gt3ch1.betterlife.data.BL_HOME;
import me.gt3ch1.betterlife.eventhelpers.PlayerTeleportHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;
import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendPermissionErrorMessage;


public class HomeCommand implements BetterLifeCommand {

    private final BL_HOME blHome;
    private final PlayerTeleportHelper teleportHelper;

    @Inject
    public HomeCommand(BL_HOME blHome, PlayerTeleportHelper teleportHelper) {
        this.blHome = blHome;
        this.teleportHelper = teleportHelper;
    }

    /**
     * Sends the target a list of the homes of the source player.
     *
     * @param source Player to get the list of homes from.
     * @param target Player to send a list of homes to.
     */
    private void sendListOfHomes(OfflinePlayer source, Player target) {
        var listOfHomes = blHome.getHomes(source.getUniqueId()).keySet();
        sendMessage(target, "Valid homes: " + String.join(", ", listOfHomes), true);
    }

    @Override
    public String getPermission() {
        return "betterlife.home";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Allows you to teleport and set homes.";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sendMessage(sender, "&4You must be a player to use this command!", false);
            return true;
        }

        Location home;

        // Get the list of homes and location pairs, then get just an array of the names
        LinkedHashMap<String, Location> homeList = blHome.getHomes(player.getUniqueId());
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
                            blHome.addHome(player, args[1]);
                            sendMessage(player, "&aHome &f" + args[1] + " &acreated!", true);
                        } else {
                            sendPermissionErrorMessage(player);
                        }
                    }
                    case "del" -> {
                        if (player.hasPermission(getPermission())) {
                            if (blHome.delHome(player, args[1])) {
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

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        final List<String> subCommands = new ArrayList<>();
        if (!(sender instanceof Player player)) {
            return Collections.emptyList();
        }
        
        switch (args.length) {
            case 1 -> {
                if (player.hasPermission("betterlife.home")) {
                    subCommands.addAll(blHome.getHomes(player.getUniqueId()).keySet());
                }

                var homeCmds = Arrays.asList("set", "del", "list");
                for (String cmd : homeCmds) {
                    if (player.hasPermission("betterlife.home." + cmd)) {
                        subCommands.add(cmd);
                    }
                }
            }
            case 2 -> {
                switch (args[1]) {
                    case "del" -> {
                        if (player.hasPermission("betterlife.home.del")) {
                            subCommands.addAll(blHome.getHomes(player.getUniqueId()).keySet());
                        }
                    }
                    case "list" -> {
                        if (player.hasPermission("betterlife.home.list.others")) {
                            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                subCommands.add(p.getName());
                            }
                        }
                    }
                }
            }
        }
        return subCommands;
    }
}
