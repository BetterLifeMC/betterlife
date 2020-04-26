package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Bukkit;
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
public class BlockExplode implements Listener {

    private static BlockBreakHelper bbh = new BlockBreakHelper();
    Object[] playerUUIDS;
    boolean antiGriefEnabledPerPlayer;
    private Location loc1, loc2;

    @EventHandler
    public void antiGriefBlockExplode(EntityExplodeEvent e) {

        PlayerConfigurationHandler playerConfigs = CommandUtils.getPlayerConfiguration();
        try {
            playerUUIDS = playerConfigs.getCustomConfig().getConfigurationSection("player").getKeys(false).toArray();
        }catch(Exception ex){
            playerUUIDS = playerConfigs.getSqlRow("uuid").toArray();
        }
        if (Main.isUsingSql)
            playerUUIDS = playerConfigs.getSqlRow("uuid").toArray();
        try {
            for (Block b : e.blockList().toArray(new Block[e.blockList().size()])) {

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
                            e.blockList().remove(e.blockList().indexOf(b));

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
