package me.gt3ch1.betterlife.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

import me.gt3ch1.betterlife.Main.Main;

public class blockFade implements Listener {
	Main m = new Main();
	public blockFade(Main m) {
		this.m = m;
	}
	@EventHandler
	public void onCropTrample(BlockFadeEvent e) {

		Material currentBlock = e.getBlock().getType();

		if (materialEquals(currentBlock, Material.FARMLAND)) {
			e.setCancelled(true);
		}
	}

	private boolean materialEquals(Material m1, Material m2) {
		return (m1 == m2);
	}
}
