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
        this.onCommand(cs, c, label, args);
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
                            if(player.hasPermission(getPermission()+".bal"))
                                sendMessage(player, "&aBalance of &7" + player.getName() + "&7:&a " + economy.getBalance(player));
                            else
                                sendPermissionErrorMessage(sender);
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
                            if(player.hasPermission(getPermission()+".bal.others")) {
                                commandReceiver = Bukkit.getPlayer(args[1]);
                                double bal = economy.getBalance(args[1]);
                                // This needs to be redone, but I can't care at the moment.
                                if (commandReceiver != null || bal > 0) {
                                    sendMessage(player, "&aBalance of&7 " + args[1] + "&7:&a " + bal);
                                } else {
                                    sendMessage(player, "&4Player not found!");
                                }
                                return true;
                            }else{
                                sendBannerMessage(sender,"&4You do not have permissions!");
                            }
                        default:
                            return false;
                    }
                case 3:
                    switch (args[0]) {
                        case "give":
                            commandReceiver = Bukkit.getPlayer(args[1]);
                            if (commandReceiver instanceof Player && args.length >= 2 && player.hasPermission(getPermission()+".give")) {
                                double playerBalance = Double.valueOf(args[2]);
                                if ((economy.getBalance(player) - playerBalance) > 0) {
                                    economy.depositPlayer(commandReceiver, playerBalance);
                                    economy.withdrawPlayer(player, playerBalance);
                                    sendMessage(player, "&aYou sent &6" + playerBalance + "&a to &7" + commandReceiver.getName());
                                    return true;
                                }
                                return false;
                            }
                            if(!player.hasPermission(getPermission()+".give")){
                                sendPermissionErrorMessage(sender);
                                return false;
                            }
                            return false;
                        case "set":
                            commandReceiver = Bukkit.getPlayer(args[1]);
                            double balance = Double.valueOf(args[2]);
                            if (commandReceiver instanceof Player && player.hasPermission(getPermission()+".set")) {
                                economy.withdrawPlayer(commandReceiver, economy.getBalance(commandReceiver));
                                economy.depositPlayer(commandReceiver, balance);
                                sendMessage(sender, "&aSetting player &7" + commandReceiver.getName() + "'s&a balance to &6" + balance);
                            }
                            if(!player.hasPermission(getPermission()+".set")){
                                sendPermissionErrorMessage(sender);
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