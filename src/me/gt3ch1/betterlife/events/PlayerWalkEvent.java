package me.gt3ch1.betterlife.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Location loc = e.getPlayer().getLocation();
		loc.setY(loc.getY() + 0.06250);
		
		Material currentBlock = loc.getWorld().getBlockAt(loc).getRelative(BlockFace.DOWN).getType();

		if (materialEquals(currentBlock, Material.GRASS_PATH) && e.getPlayer().isSprinting()) {
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
		}
	}public

	private boolean materialEquals(Material m1, Material m2) {
		return (m1 == m2);
	}
}
