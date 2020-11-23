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
        setupPlayerConfig(playerUUID);
        if (playerGetter.getPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER) == null) {
            playerGetter.setPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER,newPlayerParticle.toString());
        }

    }

    /** Set's up the cache for all player data
     * @param playerUUID Player UUID
     */
    public static void setupPlayerConfig(UUID playerUUID) {
        playerGetter.trailEnabledPerPlayer.put(playerUUID, playerGetter.getPlayerToggle(playerUUID,BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER));
        playerGetter.trailPerPlayer.put(playerUUID, playerGetter.getPlayerString(playerUUID,BL_PLAYER_ENUM.TRAIL_PER_PLAYER));
        playerGetter.roadboostPerPlayer.put(playerUUID, playerGetter.getPlayerToggle(playerUUID,BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER));
    }

    /**
     * Clears the player configuration cache
     */
    public static void clearPlayerConfigs() {
        try {
            playerGetter.trailEnabledPerPlayer.clear();
            playerGetter.trailPerPlayer.clear();
            playerGetter.roadboostPerPlayer.clear();
        } catch (Exception e) {

        }
    }

    /**
     * Clears the cache for the player
     * @param uuid Player UUID
     */
    public static void clearPlayerConfigs(UUID uuid) {
        try {
            playerGetter.trailEnabledPerPlayer.remove(uuid);
            playerGetter.trailPerPlayer.remove(uuid);
            playerGetter.roadboostPerPlayer.remove(uuid);
        } catch (Exception e) {
        }
    }
}
