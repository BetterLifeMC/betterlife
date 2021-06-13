package me.gt3ch1.betterlife.events;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import me.gt3ch1.betterlife.data.BL_WARP;
import me.gt3ch1.betterlife.data.Sql;
import me.gt3ch1.betterlife.eventhelpers.PlayerTeleportHelper;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {

    private final BL_PLAYER playerGetter;
    private final MainConfigurationHandler ch;
    private final Sql sql;
    private final BL_PLAYER blPlayer;
    private final BL_WARP blWarp;
    private final PlayerTeleportHelper teleportHelper;

    @Inject
    public PlayerJoin(BL_PLAYER playerGetter, MainConfigurationHandler ch, Sql sql, BL_PLAYER blPlayer,
                      BL_WARP blWarp, PlayerTeleportHelper teleportHelper) {
        this.playerGetter = playerGetter;
        this.ch = ch;
        this.sql = sql;
        this.blPlayer = blPlayer;
        this.blWarp = blWarp;
        this.teleportHelper = teleportHelper;
    }

    /**
     * Sets up the player configuration for the player that has joined.
     * @param e PlayerJoinEvent to check.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!sql.isSqlConnected()) {
            return;
        }

        Player p = e.getPlayer();
        UUID playerUUID = p.getUniqueId();

        Particle newPlayerParticle = Particle.valueOf(ch.getCustomConfig().getString("defaultParticle").toUpperCase());
        blPlayer.setupPlayerConfig(playerUUID);

        if (playerGetter.getPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER) == null) {
            playerGetter.setPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER, newPlayerParticle.toString());
        }
        if (!p.hasPlayedBefore() && blWarp.getWarps().containsKey("spawn")) {
            teleportHelper.teleportPlayer(p, blWarp.getWarps().get("spawn"), "spawn", true);
        }
    }
}
