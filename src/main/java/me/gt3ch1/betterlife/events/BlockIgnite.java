//TODO: Re-implement anti grief
/*
package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

import java.util.UUID;

*/
/**
 * Contains method listener for when a block caches fire within a defined region.
 *
 * @author gt3ch1
 *//*

public class BlockIgnite extends BlockBreakHelper implements Listener {

    private static BlockBreakHelper bbh;
    private Location loc1, loc2;

    @EventHandler
    public void antiGriefBlockIgnite(BlockIgniteEvent e) {

        Block b = e.getBlock();

        for (int i = 0; i < playerUUIDS.length; i++) {

            UUID playerUUID = UUID.fromString(playerUUIDS[i].toString());

            loc1 = pch.antiGriefLocation1PerPlayer.get(playerUUID);
            loc2 = pch.antiGriefLocation2PerPlayer.get(playerUUID);
            if (bbh.isWithinClaimedArea(loc1, loc2, b))
                e.setCancelled(true);
        }
    }
}
*/
