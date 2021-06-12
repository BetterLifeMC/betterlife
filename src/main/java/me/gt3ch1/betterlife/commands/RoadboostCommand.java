package me.gt3ch1.betterlife.commands;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;

public class RoadboostCommand implements BetterLifeCommand {

    private final BL_PLAYER blPlayer;

    @Inject
    public RoadboostCommand(BL_PLAYER blPlayer) {
        this.blPlayer = blPlayer;
    }

    @Override
    public String getPermission() {
        return "betterlife.roadboost";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Gives you Speed 1 when sprinting over a path block.";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            UUID playerUUID = player.getUniqueId();

            if (player.hasPermission(this.getPermission())) {
                boolean hasToggleSprintEnabled = blPlayer.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER);
                blPlayer.setPlayerToggle(playerUUID, BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER);

                String toggleState = hasToggleSprintEnabled ? "&cdisabled" : "&aenabled";
                sendMessage(player, "&7Roadboost has been " + toggleState + "&7!", true);
            }
        } else {
            // TODO: Add second arg for targeting a player
            sendMessage(sender, "&4This command can only be done in-game!", true);
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        //TODO: tab completion man
        return Collections.emptyList();
    }
}
