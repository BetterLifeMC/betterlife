package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class BlockFade implements Listener {

	@EventHandler
	public void onCropTrample(BlockFadeEvent e) {
		// Get the block that faded.
		Material currentBlock = e.getBlock().getType();
		// Get whether or not croptrample is enabled in settings.
		boolean cropTrampleEnabled = CommandUtils.getMainConfiguration().getCustomConfig().getBoolean("events.croptrample");
		if (currentBlock == Material.FARMLAND && cropTrampleEnabled) {
			// Cancel the event.
			e.setCancelled(true);
		}
	}
}
