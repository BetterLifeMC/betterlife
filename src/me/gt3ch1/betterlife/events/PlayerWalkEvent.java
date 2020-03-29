package me.gt3ch1.betterlife.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
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
		
		Material currentBlock = loc.getWorld().getBlockAt(loc).getRelative(BlockFace.DOWN).getType();

		if (materialEquals(currentBlock, Material.GRASS_PATH) && e.getPlayer().isSprinting()) {
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
		}
	}
	@EventHandler
	public void trailsEvents(PlayerMoveEvent e) {
		Location location = e.getPlayer().getLocation();
		World world = e.getPlayer().getWorld();
		
		int direction = (int)location.getYaw();
		 
		if(direction < 0) {
		    direction += 360;
		    direction = (direction + 45) / 90;
		}else {
		    direction = (direction + 45) / 90;
		}
		if(direction == 1) {
			location.setX(location.getX() + 1);
		}else if(direction == 2) {
			location.setZ(location.getZ() + 1);
		}else if (direction == 3) {
			location.setX(location.getX() - 1);
		}else{
			location.setZ(location.getZ() - 1);
		}
		Particle p;
		location.setY(location.getY() + 1);	
		try {
			p = Particle.valueOf(m.getPlayerConfiguration().getCustomConfig().getString("player."+e.getPlayer().getUniqueId() +".particle"));
			
			e.getPlayer().getWorld().spawnParticle(p, location, 1);
		} catch (Exception ex) {
			p = Particle.valueOf(m.getPlayerConfiguration().getCustomConfig().getString("defaultparticle"));
			e.getPlayer().getWorld().spawnParticle(p, location, 1);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Particle newPlayerParticle = Particle.valueOf(m.getMainConfiguration().getCustomConfig().getString("defaultparticle"));
		try {
			newPlayerParticle = Particle
					.valueOf(m.getPlayerConfiguration().getCustomConfig().getString("player."+p.getUniqueId().toString() + ".particle"));
		} catch (Exception ex) {
			m.getPlayerConfiguration().getCustomConfig().set("player."+p.getUniqueId().toString() + ".particle", newPlayerParticle.toString());
			m.getPlayerConfiguration().saveCustomConfig();
		}
	}
	private boolean materialEquals(Material m1, Material m2) {
		return (m1 == m2);
	}
}
