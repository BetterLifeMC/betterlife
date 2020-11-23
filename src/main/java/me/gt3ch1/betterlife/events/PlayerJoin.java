package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.data.BL_PLAYER;
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
        if (playerGetter.getPlayerString(playerUUID, "Trail") == null) {
            playerGetter.setPlayerString(playerUUID, "Trail", newPlayerParticle.toString());
        }

        setupPlayerConfig(playerUUID);
    }

    /** Set's up the cache for all player data
     * @param playerUUID Player UUID
     */
    public static void setupPlayerConfig(UUID playerUUID) {
        playerGetter.trailEnabledPerPlayer.put(playerUUID, playerGetter.getPlayerToggle(playerUUID,"trails.enabled"));
        playerGetter.trailPerPlayer.put(playerUUID, playerGetter.getPlayerString(playerUUID,"trails.trail"));
        playerGetter.roadboostPerPlayer.put(playerUUID, playerGetter.getPlayerToggle(playerUUID,"roadboost"));
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
