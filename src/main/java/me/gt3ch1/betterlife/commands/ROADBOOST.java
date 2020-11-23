package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ROADBOOST extends BetterLifeCommands implements CommandExecutor {
    BL_PLAYER playerGetter = new BL_PLAYER();

    /**
     * Handles the command /roadboost
     *
     * @param permission
     * @param cs
     * @param c
     * @param label
     * @param args
     */
    public ROADBOOST(String permission, CommandSender cs, Command c, String label, String[] args) {

        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);

    }


    /**
     * Toggles whether or not the player can be fast
     */
    @Override
    public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            UUID playerUUID = p.getUniqueId();
            if (p.hasPermission(this.getPermission())) {

                boolean hasToggleSprintEnabled = playerGetter.getPlayerToggle(playerUUID, "RoadBoostToggle");
                playerGetter.setPlayerToggle(playerUUID, "RoadBoostToggle");
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