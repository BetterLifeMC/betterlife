package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.UUID;

/**
 * Contains methods used for when a block or an entity explodes near or within a designated zone.
 *
 * @author gt3ch1
 */
public class BlockExplode extends BlockBreakHelper implements Listener {

    private static BlockBreakHelper bbh;
    private Location loc1, loc2;

    @EventHandler
    public void antiGriefBlockExplode(EntityExplodeEvent e) {

        for (Block b : e.blockList().toArray(new Block[e.blockList().size()]))

            for (int i = 0; i < playerUUIDS.length; i++) {

                UUID playerUUID = UUID.fromString(playerUUIDS[i].toString());

                loc1 = pch.antiGriefLocation1PerPlayer.get(playerUUID);
                loc2 = pch.antiGriefLocation2PerPlayer.get(playerUUID);
                if (bbh.isWithinClaimedArea(loc1, loc2, b))
                    e.blockList().remove(e.blockList().indexOf(b));
            }
    }
}
