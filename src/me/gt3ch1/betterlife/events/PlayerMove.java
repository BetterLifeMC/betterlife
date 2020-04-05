package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
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

	@EventHandler
	public void roadBoostEvents(PlayerMoveEvent e) {

		// Check if RoadBoost is enabled for the player
		boolean boostEnabled = CommandUtils.getPlayerConfiguration().getCustomConfig().getBoolean("player." + e.getPlayer().getUniqueId().toString() + ".pathboost");

		// Get the block the player is currently standing on
		Location loc = e.getPlayer().getLocation();
		loc.setY(loc.getY() + 0.06250);
		Material currentBlock = loc.getWorld().getBlockAt(loc).getRelative(BlockFace.DOWN).getType();

		// Apply the potion effect
		if (currentBlock == Material.GRASS_PATH && e.getPlayer().isSprinting() && boostEnabled) {
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
		}
	}

	@EventHandler
	public void trailsEvents(PlayerMoveEvent e) {
		// Get the players location
		Location location = e.getPlayer().getLocation();
		// Get whether or not the player has enabled trails
		boolean trailEnabled = CommandUtils.getPlayerConfiguration().getCustomConfig().getBoolean("player." + e.getPlayer().getUniqueId().toString() + ".trails.enabled");
		// If it is
		if (trailEnabled) {
			// Get the yaw of the player
			int direction = (int) location.getYaw();
			// Get some directions
			if (direction < 0) {
				direction += 360;
				direction = (direction + 45) / 90;
			} else {
				direction = (direction + 45) / 90;
			}
			// Set the location of the particle spawn to be directly
			// behind the player at any given time
			if (direction == 1) {
				location.setX(location.getX() + 1);
			} else if (direction == 2) {
				location.setZ(location.getZ() + 1);
			} else if (direction == 3) {
				location.setX(location.getX() - 1);
			} else {
				location.setZ(location.getZ() - 1);
			}
			// Create the particle
			Particle p;
			// Move the particle up one block so it isn't in the ground.
			location.setY(location.getY() + 1);
			try {
				// Actually create the particle based off of the
				// players preference.
				p = Particle.valueOf(CommandUtils.getPlayerConfiguration().getCustomConfig().getString("player." + e.getPlayer().getUniqueId() + ".trail"));
				// Spawn the particle at the location.
				e.getPlayer().getWorld().spawnParticle(p, location, 1);
			} catch (Exception ex) {
				// Get the default particle from the main configuration.
				p = Particle.valueOf(CommandUtils.getMainConfiguration().getCustomConfig().getString("defaultparticle"));
				// Spawn the particle at the location
				e.getPlayer().getWorld().spawnParticle(p, location, 1);
				// The exception is now handled.
			}
		}
	}
}
