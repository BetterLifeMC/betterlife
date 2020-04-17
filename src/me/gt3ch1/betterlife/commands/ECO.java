package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.HelpHelper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                            sendBannerMessage(player, "&aBalance: " + economy.getBalance(player));
                            return true;
                        case "give":
                        case "set":
                            sendHelpMessage(sender, "eco", helpHash);
                            return true;
                        default:
                            return false;
                    }
                case 2:
                    switch (args[0]){
                        case "give":
                            Player commandReceiver = Bukkit.getPlayer(args[1]);
                            if (commandReceiver instanceof Player && args.length==3){
                                double playerBalance = Double.valueOf(args[2]);
                                if((economy.getBalance(player) - playerBalance) > 0) {
                                    economy.bankDeposit(commandReceiver.getName(), playerBalance);
                                    economy.withdrawPlayer(commandReceiver, playerBalance);
                                    sendBannerMessage(player, "&aYou sent &e" + playerBalance +"&a to " + commandReceiver.getName());
                                    return true;
                                }
                                return false;
                            }
                            return false;
                        case "set":
                            sendHelpMessage(sender, "eco", helpHash);
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