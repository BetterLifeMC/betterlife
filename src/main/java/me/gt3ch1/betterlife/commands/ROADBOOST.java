package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.BetterLife;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ROADBOOST extends BetterLifeCommands implements CommandExecutor {

    BL_PLAYER playerGetter = BetterLife.bl_player;

    /**
     * Handles the command /roadboost
     *
     * @param permission Permission required to use /home
     * @param cs         Sender of the command.
     * @param c          The command itself.
     * @param label      The string version of the command.
     * @param args       The arguments of the command.
     */
    public ROADBOOST(String permission, CommandSender cs, Command c, String label, String[] args) {

        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);

    }


    /**
     * Toggles whether or not the player can be fast
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String command, String[] args) {

        if (sender instanceof Player p) {
            UUID playerUUID = p.getUniqueId();

            if (p.hasPermission(this.getPermission())) {
                boolean hasToggleSprintEnabled = playerGetter.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER);
                playerGetter.setPlayerToggle(playerUUID, BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER);

                String toggleState = hasToggleSprintEnabled ? "&cdisabled" : "&aenabled";
                sendMessage(p, "&7Roadboost has been " + toggleState + "&7!", true);
            }
        } else {
            // TODO: Add second arg for targeting a player
            sendMessage(sender, "&4This command can only be done in-game!", true);
        }
        return true;
    }
}
