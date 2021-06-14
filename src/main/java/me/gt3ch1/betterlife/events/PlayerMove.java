package me.gt3ch1.betterlife.events;

import java.util.UUID;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This class contains all of the listeners and events for BetterLife's player movement-based modifications.
 */
public class PlayerMove implements Listener {
    BL_PLAYER playerGetter = Main.bl_player;

    /**
     * Checks to even to see if the player has roadboost enabled.
     *
     * @param e Move event to check to see if the player move event.
     */
    @EventHandler
    public void roadBoostEvents(PlayerMoveEvent e) {

        UUID playerUUID = e.getPlayer().getUniqueId();
        boolean boostEnabled = playerGetter.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER);

        Location loc = e.getPlayer().getLocation();
        loc.setY(loc.getY() + 0.06250);
        Material currentBlock = loc.getWorld().getBlockAt(loc).getRelative(BlockFace.DOWN).getType();

        if (currentBlock == Material.GRASS_PATH && e.getPlayer().isSprinting() && boostEnabled)
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));

    }

    /**
     * Checks to see if the player has a trail enabled.
     *
     * @param e Move event to check for trails.
     */
    @EventHandler
    public void trailsEvents(PlayerMoveEvent e) {
        UUID playerUUID = e.getPlayer().getUniqueId();
        boolean trailEnabled;
        Location location = e.getPlayer().getLocation();
        try {
            trailEnabled = playerGetter.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER);
        } catch (NullPointerException npe) {
            trailEnabled = false;
        }
        if (trailEnabled) {

            int direction = (int) location.getYaw();

            if (direction < 0) {

                direction += 360;
                direction = (direction + 45) / 90;

            } else {

                direction = (direction + 45) / 90;

            }

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

                p = Particle.valueOf(playerGetter.getPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER));
                e.getPlayer().getWorld().spawnParticle(p, location, 1);

            } catch (Exception ex) {

                p = Particle.valueOf(CommandUtils.ch.getCustomConfig().getString("defaultParticle"));
                e.getPlayer().getWorld().spawnParticle(p, location, 1);

            }
        }
    }
}

