package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.UUID;

/**
 * Contains method listener for when a block caches fire within a defined region.
 * @author gt3ch1
 */
public class BlockIgnite implements Listener {

    Object[] playerUUIDS;
    private static BlockBreakHelper bbh = new BlockBreakHelper();
    private Location loc1,loc2;
    private boolean antiGriefEnabledPerPlayer;
    @EventHandler
    public void antiGriefBlockIgnite(BlockIgniteEvent e) {

        PlayerConfigurationHandler playerConfigs = CommandUtils.getPlayerConfiguration();
        try {
            playerUUIDS = playerConfigs.getCustomConfig().getConfigurationSection("player").getKeys(false).toArray();
        }catch(Exception ex){
            playerUUIDS = playerConfigs.getSqlRow("uuid").toArray();
        }
        if (Main.isUsingSql)
            playerUUIDS = playerConfigs.getSqlRow("uuid").toArray();
        Block b = e.getBlock();

        try {
            for (int i = 0; i < playerUUIDS.length; i++) {

                UUID playerUUID = UUID.fromString(playerUUIDS[i].toString());
                antiGriefEnabledPerPlayer = playerConfigs.getBooleanValue("antigrief.enabled", playerUUID);

                if (antiGriefEnabledPerPlayer) {
                    if (Main.isUsingSql) {
                        loc1 = bbh.parseLocation("a",playerUUID);
                        loc2 = bbh.parseLocation("b",playerUUID);
                    } else {
                        loc1 = (Location) playerConfigs.get("antigrief.location.a", playerUUID);
                        loc2 = (Location) playerConfigs.get("antigrief.location.b", playerUUID);
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
