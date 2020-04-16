package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.HelpHelper;
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
                    break;
                case 1:
                    switch (args[0]) {
                        case "bal":
                            sendBannerMessage(player, "&a Balance: " + economy.getBalance(player));
                            return true;
                    }
                default:
                    return false;
            }

            return false;
        }
        return false;
    }
}