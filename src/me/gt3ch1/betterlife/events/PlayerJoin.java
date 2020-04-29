package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.eventhelpers.PlayerAccessHelper;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Handles the events for players joining or quitting the game.
 */
public class PlayerJoin extends PlayerAccessHelper implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

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
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        PlayerAccessHelper.clearPlayerConfigs(uuid);
    }
}
