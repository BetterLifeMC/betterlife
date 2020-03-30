package me.gt3ch1.betterlife.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

import me.gt3ch1.betterlife.Main.Main;

public class BlockFade implements Listener {
	Main m;
	public BlockFade(Main m) {
		this.m = m;
	}

	@EventHandler
	public void onCropTrample(BlockFadeEvent e) {

		Material currentBlock = e.getBlock().getType();
		boolean cropTrampleEnabled = m.getMainConfiguration().getCustomConfig().getBoolean("events.croptrample");
		if (currentBlock == Material.FARMLAND && cropTrampleEnabled) {
			e.setCancelled(true);
		}
	}
}
