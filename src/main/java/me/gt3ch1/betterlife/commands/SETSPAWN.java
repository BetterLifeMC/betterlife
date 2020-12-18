package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_WARP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author gt3ch1
 * @author starmism
 * @version 12/2/20
 * Project betterlife
 */
public class SETSPAWN extends BetterLifeCommands implements CommandExecutor {
    private BL_WARP warps = Main.bl_warp;

    /**
     * Handles the command /setspawn
     *
     * @param permission Permission needed to run the command.
     * @param cs         Person who sent the command.
     * @param c          The command.
     * @param label      The string version of command.
     * @param args       the arguments to the command.
     */
    public SETSPAWN(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);
    }

    /**
     * Runs the /setspawn command
     *
     * @param sender  Sender of the command.
     * @param c       The command itself.
     * @param command The string version of the command.
     * @param args    The arguments of the command.
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "Console cannot set spawn!", true);
            return false;
        }
        Player player = (Player) sender;
        warps.setWarp(player, "spawn");
        sendMessage(sender, "Spawn has been sat to your location!", true);
        return true;
    }
}
