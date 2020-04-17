package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.HelpHelper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class ECO extends BetterLifeCommands implements CommandExecutor {

    /**
     * @param permission
     * @param cs
     * @param c
     * @param label
     * @param args
     */
    public ECO(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
    }

    Player commandReceiver = null;

    @Override
    public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (args.length) {
                case 0:
                    sendHelpMessage(sender, "eco", helpHash);
                    return false;
                case 1:
                    switch (args[0]) {
                        case "bal":
                            sendMessage(player, "&aBalance: " + economy.getBalance(player));
                            return true;
                        case "give":
                        case "set":
                            sendHelpMessage(sender, "eco", helpHash);
                            return true;
                        default:
                            return false;
                    }
                case 2:
                    switch (args[0]) {
                        case "bal":
                            commandReceiver = Bukkit.getPlayer(args[1]);
                            sendMessage(player, "&aBalance of&6 " + commandReceiver.getName() + "&7:&a " + economy.getBalance(commandReceiver));
                            return true;
                        default:
                            return false;
                    }
                case 3:
                    switch (args[0]) {
                        case "give":
                            commandReceiver = Bukkit.getPlayer(args[1]);
                            if (commandReceiver instanceof Player && args.length >= 2) {
                                double playerBalance = Double.valueOf(args[2]);
                                if ((economy.getBalance(player) - playerBalance) > 0) {
                                    economy.depositPlayer(commandReceiver, playerBalance);
                                    economy.withdrawPlayer(player, playerBalance);
                                    sendMessage(player, "&aYou sent &6" + playerBalance + "&a to &7" + commandReceiver.getName());
                                    return true;
                                }
                                return false;
                            }
                            return false;
                        case "set":
                            commandReceiver = Bukkit.getPlayer(args[1]);
                            double balance = Double.valueOf(args[2]);
                            if (commandReceiver instanceof Player) {
                                economy.withdrawPlayer(commandReceiver, economy.getBalance(commandReceiver));
                                economy.depositPlayer(commandReceiver, balance);
                                sendMessage(sender,"&aSetting player &7" + commandReceiver.getName() + "'s&a balance to &6" +balance);
                            }
                            return true;
                        default:
                            return false;
                    }
                default:
                    return false;
            }
        }
        return false;
    }
}