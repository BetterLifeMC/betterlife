package me.gt3ch1.betterlife.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.gt3ch1.betterlife.Main.Main;

public class PlayerWalkEvent implements Listener {
	Main m;

	public PlayerWalkEvent(Main m) {
		this.m = m;
	}

	@EventHandler
	public void roadBoostEvents(PlayerMoveEvent e) {

		Location loc = e.getPlayer().getLocation();
		loc.setY(loc.getY() + 0.06250);
		boolean boostEnabled = m.getPlayerConfiguration().getCustomConfig()
				.getBoolean("player." + e.getPlayer().getUniqueId().toString() + ".pathboost");

		Material currentBlock = loc.getWorld().getBlockAt(loc).getRelative(BlockFace.DOWN).getType();

		if (materialEquals(currentBlock, Material.GRASS_PATH) && e.getPlayer().isSprinting() && boostEnabled) {
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

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Particle newPlayerParticle = Particle
				.valueOf(m.getMainConfiguration().getCustomConfig().getString("defaultparticle"));

		try {
			newPlayerParticle = Particle.valueOf(m.getPlayerConfiguration().getCustomConfig()
					.getString("player." + p.getUniqueId().toString() + ".trail"));
		} catch (Exception ex) {
			m.getPlayerConfiguration().getCustomConfig().set("player." + p.getUniqueId().toString() + ".trail",
					newPlayerParticle.toString());
			m.getPlayerConfiguration().getCustomConfig().set("player." + p.getUniqueId().toString() + ".trails.enabled",
					false);
			m.getPlayerConfiguration().saveCustomConfig();
			p.sendMessage(ChatColor.BLUE + "Want to enable trail particles? Try /trail help!");
		}
	}

	private boolean materialEquals(Material m1, Material m2) {
		return (m1 == m2);
	}
}
