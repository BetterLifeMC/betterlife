package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

/**
 * This class handles the disabling of farmland trampling.
 */
public class BlockFade implements Listener {

    /**
     * Checks to see if the block was a crop.
     *
     * @param e Block that was faded
     */
    @EventHandler
    public void onCropTrample(BlockFadeEvent e) {

        Material currentBlock = e.getBlock().getType();
        boolean cropTrampleEnabled = CommandUtils.ch.getCustomConfig().getBoolean("events.croptrample");

        if (currentBlock.equals(Material.FARMLAND) && cropTrampleEnabled)
            e.setCancelled(true);

    }
}
