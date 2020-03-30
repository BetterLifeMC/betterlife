package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerWalk implements Listener {
	Main m;

	public PlayerWalk(Main m) {
		this.m = m;
	}

	@EventHandler
	public void roadBoostEvents(PlayerMoveEvent e) {

		// Check if RoadBoost is enabled for the player
		boolean boostEnabled = m.getPlayerConfiguration().getCustomConfig().getBoolean("player." + e.getPlayer().getUniqueId().toString() + ".pathboost");

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
		Location location = e.getPlayer().getLocation();
		boolean trailEnabled = m.getPlayerConfiguration().getCustomConfig()
				.getBoolean("player." + e.getPlayer().getUniqueId().toString() + ".trails.enabled");
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
				p = Particle.valueOf(m.getPlayerConfiguration().getCustomConfig()
						.getString("player." + e.getPlayer().getUniqueId() + ".trail"));

				e.getPlayer().getWorld().spawnParticle(p, location, 1);
			} catch (Exception ex) {
				p = Particle.valueOf(m.getMainConfiguration().getCustomConfig().getString("defaultparticle"));
				e.getPlayer().getWorld().spawnParticle(p, location, 1);
				ex.printStackTrace();
			}
		}
	}
}
