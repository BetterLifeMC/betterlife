package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_HOME;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SETHOME extends BetterLifeCommands implements CommandExecutor {
    BL_HOME homeGetter = Main.bl_home;

    public SETHOME(String permission, CommandSender cs, Command c, String label, String[] args) {
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

        switch (args.length) {
            case 0:
                homeGetter.addHome(player, "home");
                sendMessage(player, "&7Home created!", true);
                break;
            case 1:
                homeGetter.addHome(player, args[0]);
                sendMessage(player, "&7 Home " + args[0] + " created!", true);
                break;
            default:
                sendMessage(player, "&4Too many arguments!", true);
                return false;
        }
        return true;
    }
}
