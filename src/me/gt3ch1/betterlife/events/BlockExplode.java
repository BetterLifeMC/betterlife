package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockExplode implements Listener {

    Object[] playerUUIDS;
    private static BlockBreakHelper bbh = new BlockBreakHelper();

    @EventHandler
    public void antiGriefBlockExplode(EntityExplodeEvent e) {

        PlayerConfigurationHandler playerConfigs = CommandUtils.getPlayerConfiguration();
        playerUUIDS = playerConfigs.getCustomConfig().getConfigurationSection("player").getKeys(false).toArray();

        try {
            for (Block b : e.blockList().toArray(new Block[e.blockList().size()])) {

                for (int i = 0; i < playerUUIDS.length; i++) {

                    String playerUUID = playerUUIDS[i].toString();
                    boolean antiGriefEnabledPerPlayer = playerConfigs.getCustomConfig().isConfigurationSection("player." + playerUUID + ".antigrief");

                    if (antiGriefEnabledPerPlayer) {

                        Location loc1 = (Location) playerConfigs.getCustomConfig().get("player." + playerUUID + ".antigrief.location.a");
                        Location loc2 = (Location) playerConfigs.getCustomConfig().get("player." + playerUUID + ".antigrief.location.b");


                        if (bbh.isWithinClaimedArea(loc1,loc2,b))
                            e.blockList().remove(e.blockList().indexOf(b));

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
