package me.gt3ch1.betterlife.commands;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import me.gt3ch1.betterlife.eventhelpers.PlayerTeleportHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;
import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendPermissionErrorMessage;

/**
 * @author gt3ch1
 * @author Starmism
 */
public class TeleportCommand implements BetterLifeCommand {

    private final PlayerTeleportHelper teleportHelper;

    @Inject
    public TeleportCommand(PlayerTeleportHelper teleportHelper) {
        this.teleportHelper = teleportHelper;
    }


    @Override
    public String getPermission() {
        return "betterlife.teleport";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Allows players to teleport to each other, or to teleport one person to another person";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1 -> {
                if (!(sender instanceof Player player)) {
                    sendMessage(sender, "Console can't teleport!", true);
                    return true;
                }

                Player otherPlayer = Bukkit.getPlayer(args[0]);
                if (otherPlayer == null) {
                    sendMessage(player, "You have teleported to someone that doesn't exist..?", false);
                    return true;
                }
                if (otherPlayer.getName().equalsIgnoreCase(player.getName())) {
                    sendMessage(player, "You have teleported to yourself..?", false);
                    return true;
                }

                teleportHelper.teleportPlayer(player, otherPlayer);
            }
            case 2 -> {
                Player firstPlayer = Bukkit.getPlayer(args[0]);
                Player secondPlayer = Bukkit.getPlayer(args[1]);
                if (firstPlayer == null || secondPlayer == null) {
                    sendMessage(sender, "You have teleported to someone that doesn't exist..?", false);
                    return true;
                }

                if (sender.hasPermission("betterlife.teleport.others")) {
                    teleportHelper.teleportPlayer(firstPlayer, secondPlayer);
                } else {
                    sendPermissionErrorMessage(sender);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        //TODO: do this
        return Collections.emptyList();
    }
}
