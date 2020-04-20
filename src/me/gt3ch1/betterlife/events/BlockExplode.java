package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Bukkit;
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

                Location location = b.getLocation();
                double locationX = location.getX();
                double locationZ = location.getZ();
                for (int i = 0; i < playerUUIDS.length; i++) {
                    String playerUUID = playerUUIDS[i].toString();
                    boolean antiGriefEnabledPerPlayer = playerConfigs.getCustomConfig().isConfigurationSection("player." + playerUUID + ".antigrief");
                    if (antiGriefEnabledPerPlayer) {

                        Object[] locationXList = playerConfigs.getCustomConfig().getList("player." + playerUUID + ".antigrief.location.x").toArray();
                        Object[] locationZList = playerConfigs.getCustomConfig().getList("player." + playerUUID + ".antigrief.location.z").toArray();

                        double x1 = (Double) locationXList[0];
                        double x2 = (Double) locationXList[1];

                        double z1 = (Double) locationZList[0];
                        double z2 = (Double) locationZList[1];

                        Bukkit.getLogger().info(String.format("x1 %s | x2 %s :: z1 %s | z2 %s \n is it cancelled? → %s",x1,x2,z1,z2,bbh.isWithinClaimedArea(x1, x2, z1, z2, locationX, locationZ)));

                        if (bbh.isWithinClaimedArea(x1, x2, z1, z2, locationX, locationZ)) {
                            e.blockList().clear();
                            e.setCancelled(true);
                            Bukkit.getLogger().info(String.format("Cancelling event at : %s : %s ", locationX, locationZ));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
