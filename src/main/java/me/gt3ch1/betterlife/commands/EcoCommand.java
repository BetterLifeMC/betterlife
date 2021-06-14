package me.gt3ch1.betterlife.commands;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.*;

public class EcoCommand implements BetterLifeCommand {

    private final Economy economy;

    @Inject
    public EcoCommand(Economy economy) {
        this.economy = economy;
    }

    @Override
    public String getPermission() {
        return "betterlife.eco";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Allows players to control the economy.";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player commandReceiver;
        if (economy == null) {
            sendMessage(sender, "&4Vault not found! Eco commands won't work.", true);
            return false;
        }

        if (sender instanceof Player player) {
            switch (args.length) {
                case 0 -> {
                    //sendHelpMessage(sender, "eco");
                    return true;
                }
                case 1 -> {
                    switch (args[0]) {
                        case "bal" -> {
                            if (player.hasPermission(getPermission() + ".bal")) {
                                sendMessage(player, "&aBalance of &7" + player.getName() + "&7:&a " + economy.getBalance(player), false);
                            } else {
                                sendPermissionErrorMessage(sender);
                            }
                        }
                        case "give", "set" -> {
                            //sendHelpMessage(sender, "eco", helpHash);
                        }
                        default -> {
                            return false;
                        }
                    }
                }
                case 2 -> {
                    switch (args[0]) {
                        case "bal":
                            if (player.hasPermission(getPermission() + ".bal.others")) {
                                try {
                                    commandReceiver = Bukkit.getPlayer(args[1]).getPlayer();
                                } catch (Exception e) {
                                    sendMessage(player, "&cThat player is not online!", false);
                                    return false;
                                }
                                double bal = economy.getBalance(commandReceiver);
                                // This needs to be redone, but I can't care at the moment.
                                if (commandReceiver != null || bal > 0) {
                                    sendMessage(player, "&aBalance of&7 " + args[1] + "&7:&a " + bal, false);
                                } else {
                                    sendMessage(player, "&4Player not found!", false);
                                }
                                return true;
                            } else {
                                sendPermissionErrorMessage(sender);
                            }
                        default:
                            return false;
                    }
                }
                case 3 -> {
                    switch (args[0]) {
                        case "give" -> {
                            commandReceiver = Bukkit.getOfflinePlayer(args[1]).getPlayer();
                            if (player.hasPermission(getPermission() + ".give")) {
                                double playerBalance = Double.parseDouble(args[2]);
                                if ((economy.getBalance(player) - playerBalance) > 0) {
                                    economy.depositPlayer(commandReceiver, playerBalance);
                                    economy.withdrawPlayer(player, playerBalance);
                                    sendMessage(player, "&aYou sent &6$" + playerBalance + "&a to &7" + commandReceiver.getName(), false);
                                    try {
                                        sendMessage(commandReceiver, "&aYou received &6 $" + playerBalance + "&a from &7" + commandReceiver.getName(),
                                                false);
                                    } catch (Exception e) {
                                        return false;
                                    }
                                    return true;
                                }
                                return false;
                            }

                            if (!player.hasPermission(getPermission() + ".give")) {
                                sendPermissionErrorMessage(sender);
                                return false;
                            }
                            return false;
                        }
                        case "set" -> {
                            commandReceiver = Bukkit.getPlayer(args[1]);
                            double balance = Double.parseDouble(args[2]);
                            if (player.hasPermission(getPermission() + ".set")) {
                                economy.withdrawPlayer(commandReceiver, economy.getBalance(commandReceiver));
                                economy.depositPlayer(commandReceiver, balance);
                                sendMessage(sender, "&aSetting player &7" + commandReceiver.getName() + "'s&a balance to &6" + balance, false);
                            }
                            if (!player.hasPermission(getPermission() + ".set")) {
                                sendPermissionErrorMessage(sender);
                            }
                            return true;
                        }
                        default -> {
                            return false;
                        }
                    }
                }
                default -> {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        final List<String> subCommands = new ArrayList<>();
        if (economy == null) {
            return Collections.emptyList();
        }

        if (sender instanceof Player player) {
            switch (args.length) {
                case 1 -> {
                    var ecoCmds = Arrays.asList("bal");
                    for (String cmd : ecoCmds) {
                        if (player.hasPermission("betterlife.eco." + cmd)) {
                            subCommands.add(cmd);
                        }
                    }
                }
                case 2 -> {
                    var ecoCmds = Arrays.asList("bal.others");
                    for (String cmd : ecoCmds) {
                        if (player.hasPermission("betterlife.eco." + cmd)) {
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