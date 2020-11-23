package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.data.BL_PLAYER;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Handles the events for players joining or quitting the game.
 */
public class PlayerJoin implements Listener {
    BL_PLAYER playerGetter = new BL_PLAYER();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // do something i guess
        // Oh it will do something.
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
        PlayerAccessHelper.setupPlayerConfig(playerUUID);
         */
    }
}
