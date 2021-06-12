package me.gt3ch1.betterlife.commands;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import me.gt3ch1.betterlife.data.BL_WARP;
import me.gt3ch1.betterlife.eventhelpers.PlayerTeleportHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;

/**
 * @author gt3ch1
 * @author Starmism
 */
public class SpawnCommand implements BetterLifeCommand {

    private final BL_WARP blWarp;
    private final PlayerTeleportHelper teleportHelper;

    @Inject
    public SpawnCommand(BL_WARP blWarp, PlayerTeleportHelper teleportHelper) {
        this.blWarp = blWarp;
        this.teleportHelper = teleportHelper;
    }

    @Override
    public String getPermission() {
        return "betterlife.spawn";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Teleports you to spawn!";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sendMessage(sender, "Console cannot set spawn!", true);
            return true;
        }

        switch (args.length) {
            case 0 -> {
                if (player.hasPermission(getPermission())) {
                    teleportHelper.teleportPlayer(player, blWarp.getWarps().get("spawn"), "spawn");
                }
            }
            case 1 -> {
                if (args[0].equalsIgnoreCase("set")) {
                    if (player.hasPermission(getPermission() + ".setspawn")) {
                        blWarp.setWarp(player, "spawn");
                        sendMessage(sender, "Spawn has been set to your location!", true);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        //TODO: do tab complete
        return Collections.emptyList();
    }
}
