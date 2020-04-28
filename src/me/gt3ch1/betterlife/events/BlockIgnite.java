package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

import java.util.UUID;

/**
 * Contains method listener for when a block caches fire within a defined region.
 *
 * @author gt3ch1
 */
public class BlockIgnite extends BlockBreakHelper implements Listener {

    private static BlockBreakHelper bbh = new BlockBreakHelper();
    Object[] playerUUIDS;
    private Location loc1, loc2;
    private boolean antiGriefEnabledPerPlayer;

    @EventHandler
    public void antiGriefBlockIgnite(BlockIgniteEvent e) {

        try {
            playerUUIDS = playerConfig.getCustomConfig().getConfigurationSection("player").getKeys(false).toArray();
        } catch (Exception ex) {
            playerUUIDS = playerConfig.getRow("uuid").toArray();
        }
        if (Main.isUsingSql)
            playerUUIDS = playerConfig.getRow("uuid").toArray();
        Block b = e.getBlock();

        try {
            for (int i = 0; i < playerUUIDS.length; i++) {

                UUID playerUUID = UUID.fromString(playerUUIDS[i].toString());
                antiGriefEnabledPerPlayer = playerConfig.getBooleanValue("antigrief.enabled", playerUUID);

                if (antiGriefEnabledPerPlayer) {
                    if (Main.isUsingSql) {
                        loc1 = bbh.parseLocation("a", playerUUID);
                        loc2 = bbh.parseLocation("b", playerUUID);
                    } else {
                        loc1 = (Location) playerConfig.get("antigrief.location.a", playerUUID);
                        loc2 = (Location) playerConfig.get("antigrief.location.b", playerUUID);
                    }

                    if (bbh.isWithinClaimedArea(loc1, loc2, b))
                        e.setCancelled(true);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
