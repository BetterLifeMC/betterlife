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
            case 1:
                Player otherPlayer = Bukkit.getPlayer(args[0]);
                if (!otherPlayer.isOnline()) {
                    sendMessage(sender, args[0] + " must be online to toggle mute.", true);
                    return false;
                }
                boolean getCurrentMuteState = togglePlayerMute(otherPlayer);
                String muteUnmute = (getCurrentMuteState ? "muted" : "unmuted");
                sendMessage(sender, ChatColor.YELLOW + "Player " + ChatColor.LIGHT_PURPLE + args[0] +
                        ChatColor.YELLOW + " has been " + ChatColor.AQUA + muteUnmute + ChatColor.YELLOW + ".", true);
                sendMessage(otherPlayer, ChatColor.YELLOW + "You have been " +
                        ChatColor.AQUA + muteUnmute + ChatColor.YELLOW + " by: " + ChatColor.LIGHT_PURPLE + sender.getName(), true);
                return true;
            default:
                sendMessage(sender, "Invalid usage!", true);
                return false;
        }
    }

    /**
     * Toggles the players mute status.
     *
     * @param p Player to toggle the mute status of.
     * @return The new state of the player mute.
     */
    private boolean togglePlayerMute(Player p) {
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.MUTE_PER_PLAYER;
        Main.bl_player.setPlayerToggle(p.getUniqueId(), type);
        return Main.bl_player.getPlayerToggle(p.getUniqueId(), type);
    }
}
