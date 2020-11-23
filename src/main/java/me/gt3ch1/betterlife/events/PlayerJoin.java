package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.data.BL_PLAYER;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

/**
 * Handles the events for players joining or quitting the game.
 */
public class PlayerJoin implements Listener {
    // TODO: This needs to be stored in like main or something.  A class where we can access it.
    // We cannot make new BL_PLAYER's for every class that needs access to the methods.  It will break a lot.
    private static BL_PLAYER playerGetter = new BL_PLAYER();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // do something i guess
        // Oh it will do something.
        Player p = e.getPlayer();
        UUID playerUUID = p.getUniqueId();
        //TODO: Make this function with the redesign.
        /*
         Player p = e.getPlayer();
        Particle newPlayerParticle = Particle.valueOf(CommandUtils.ch.getCustomConfig().getString("defaultParticle").toUpperCase());
        UUID playerUUID = p.getUniqueId();
        try {
            pch.getStringValue("trails.enabled", playerUUID);
        } catch (Exception ex) {
            pch.setValue("trails.trail", newPlayerParticle.toString(), playerUUID);
            pch.setValue("trails.enabled", false, playerUUID);
        }
        setupPlayerConfig(playerUUID);
         */
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
