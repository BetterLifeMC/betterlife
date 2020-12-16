package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author gt3ch1
 * @author starmism
 * @version 12/15/20
 */
public class TELEPORT extends BetterLifeCommands implements CommandExecutor  {
    /**
     * Creates a new BetterLife command.
     *
     * @param permission Permission needed to run the command.
     * @param cs         Person who sent the command.
     * @param c          The command.
     * @param label      The string version of command.
     * @param args       the arguments to the command.
     */
    public TELEPORT(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs,c,label,args);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
        switch(args.length){
            case 1:
                if(!(sender instanceof  Player)){
                    sendMessage(sender,"Console can't teleport!",true);
                    return false;
                }
                Player otherPlayer = Bukkit.getPlayer(args[0]);
                if(otherPlayer.getName().equalsIgnoreCase(sender.getName())) {
                    sendMessage(sender, "You have teleported to yourself..?", false);
                    return false;
                }
                teleportHelper.teleportPlayer((Player)sender,otherPlayer);
                return true;
            default:
                return false;
        }
    }
}
