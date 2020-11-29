package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

/**
 * Handles the events for players joining or quitting the game.
 */
public class PlayerJoin implements Listener {
    private static final BL_PLAYER playerGetter = Main.bl_player;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID playerUUID = p.getUniqueId();

        Particle newPlayerParticle = Particle.valueOf(
            CommandUtils.ch.getCustomConfig().getString("defaultParticle").toUpperCase());
        Main.setupPlayerConfig(playerUUID);
        if (playerGetter.getPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER) == null) {
            playerGetter.setPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER,newPlayerParticle.toString());
        }

    }
}
