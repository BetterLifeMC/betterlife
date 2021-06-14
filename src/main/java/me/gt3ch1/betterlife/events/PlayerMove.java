package me.gt3ch1.betterlife.events;

import java.util.UUID;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import me.gt3ch1.betterlife.data.Sql;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerMove implements Listener {

    private final BL_PLAYER blPlayer;
    private final Sql sql;
    private final MainConfigurationHandler ch;

    @Inject
    public PlayerMove(BL_PLAYER blPlayer, Sql sql, MainConfigurationHandler ch) {
        this.blPlayer = blPlayer;
        this.sql = sql;
        this.ch = ch;
    }

    /**
     * Checks to even to see if the player has roadboost enabled.
     * @param e Move event to check to see if the player move event.
     */
    @EventHandler
    public void roadBoostEvents(PlayerMoveEvent e) {
        if (!sql.isSqlConnected()) {
            return;
        }

        UUID playerUUID = e.getPlayer().getUniqueId();
        boolean boostEnabled = blPlayer.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER);

        Location loc = e.getPlayer().getLocation();
        loc.setY(loc.getY() + 0.06250);
        Material currentBlock = loc.getWorld().getBlockAt(loc).getRelative(BlockFace.DOWN).getType();

        if (currentBlock == Material.DIRT_PATH && e.getPlayer().isSprinting() && boostEnabled) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
        }
    }

    /**
     * Checks to see if the player has a trail enabled.
     * @param e Move event to check for trails.
     */
    @EventHandler
    public void trailsEvents(PlayerMoveEvent e) {
        if (!sql.isSqlConnected()) {
            return;
        }

        UUID playerUUID = e.getPlayer().getUniqueId();
        boolean trailEnabled;
        Location location = e.getPlayer().getLocation();

        try {
            trailEnabled = blPlayer.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER);
        } catch (NullPointerException npe) {
            trailEnabled = false;
        }

        if (trailEnabled) {
            int direction = (int) location.getYaw();

            if (direction < 0) {
                direction += 360;
            }
            direction = (direction + 45) / 90;

            if (direction == 1) {
                location.setX(location.getX() + 1);
            } else if (direction == 2) {
                location.setZ(location.getZ() + 1);
            } else if (direction == 3) {
                location.setX(location.getX() - 1);
            } else {
                location.setZ(location.getZ() - 1);
            }

            Particle p;
            location.setY(location.getY() + 1);

            try {
                p = Particle.valueOf(blPlayer.getPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER));
                e.getPlayer().getWorld().spawnParticle(p, location, 1);
            } catch (Exception ex) {
                p = Particle.valueOf(ch.getCustomConfig().getString("defaultParticle"));
                e.getPlayer().getWorld().spawnParticle(p, location, 1);
            }
        }
    }
}

