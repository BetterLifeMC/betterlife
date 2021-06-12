package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class represents the /mute command for BetterLife.
 */
public class MUTE extends BetterLifeCommands implements CommandExecutor {

    /**
     * Handles the command /mute
     *
     * @param permission Permission required to use /home
     * @param cs         Sender of the command.
     * @param c          The command itself.
     * @param label      The string version of the command.
     * @param args       The arguments of the command.
     */
    public MUTE(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);
    }

    /**
     * Runs the /mute command
     *
     * @param sender  Sender of the command.
     * @param c       The command itself.
     * @param command The string version of the command.
     * @param args    The arguments of the command.
     * @return True if the command was successful.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
        switch (args.length) {
            case 1 -> {
                Player otherPlayer = Bukkit.getPlayer(args[0]);

                if (otherPlayer == null) {
                    sendMessage(sender, "&4Player doesn't exist!", true);
                    return true;
                }

                if (!otherPlayer.isOnline()) {
                    sendMessage(sender, "&e" + otherPlayer.getName() + " &4must be online to be muted.", true);
                    return true;
                }

                boolean getCurrentMuteState = Main.bl_player.getPlayerToggle(otherPlayer.getUniqueId(), BL_PLAYER_ENUM.MUTE_PER_PLAYER);
                String newMuteStatus = getCurrentMuteState ? "unmuted" : "muted";

                sendMessage(sender, "&ePlayer &d" + otherPlayer.getName() + "&e has been &b" + newMuteStatus + "&e.", true);
                sendMessage(otherPlayer, "&eYou have been &b" + newMuteStatus + "&e by: &d" + sender.getName(), true);
                Main.bl_player.setPlayerToggle(otherPlayer.getUniqueId(), BL_PLAYER_ENUM.MUTE_PER_PLAYER);
                return true;
            }
            default -> {
                sendMessage(sender, "Invalid usage!", true);
                return false;
            }
        }
    }
}
