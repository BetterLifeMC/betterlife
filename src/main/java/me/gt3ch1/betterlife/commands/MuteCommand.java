package me.gt3ch1.betterlife.commands;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;

public class MuteCommand implements BetterLifeCommand {

    private final BL_PLAYER blPlayer;

    @Inject
    public MuteCommand(BL_PLAYER blPlayer) {
        this.blPlayer = blPlayer;
    }

    @Override
    public String getPermission() {
        return "betterlife.mute";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Stops players from chatting unless they have the bypass.";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1 -> {
                Player otherPlayer = Bukkit.getPlayer(args[0]);

                if (otherPlayer == null) {
                    sendMessage(sender, "&4Player doesn't exist!", true);
                    return true;
                }

                //TODO: Add caching so you can mute/unmute offline players
                if (!otherPlayer.isOnline()) {
                    sendMessage(sender, "&e" + otherPlayer.getName() + " &4must be online to be muted.", true);
                    return true;
                }

                boolean getCurrentMuteState = blPlayer.getPlayerToggle(otherPlayer.getUniqueId(), BL_PLAYER_ENUM.MUTE_PER_PLAYER);
                String newMuteStatus = getCurrentMuteState ? "unmuted" : "muted";

                sendMessage(sender, "&ePlayer &d" + otherPlayer.getName() + "&e has been &b" + newMuteStatus + "&e.", true);
                sendMessage(otherPlayer, "&eYou have been &b" + newMuteStatus + "&e by: &d" + sender.getName(), true);
                blPlayer.setPlayerToggle(otherPlayer.getUniqueId(), BL_PLAYER_ENUM.MUTE_PER_PLAYER);
                return true;
            }
            default -> {
                sendMessage(sender, "Invalid usage!", true);
                return false;
            }
        }
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        //TODO: do this
        return Collections.emptyList();
    }
}
