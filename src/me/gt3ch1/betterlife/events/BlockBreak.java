package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockBreak implements Listener {

    PlayerConfigurationHandler playerConfigs = CommandUtils.getPlayerConfiguration();
    Object[] playerUUIDS;
    private static BlockBreakHelper bbh;

    @EventHandler
    public void betterLifeAntigriefBlockBreakEvent(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        playerUUIDS = playerConfigs.getCustomConfig().getConfigurationSection("player").getKeys(false).toArray();
        try {
            Location location = block.getLocation();
            double locationX = location.getX();
            double locationZ = location.getZ();
            for (int i = 0; i < playerUUIDS.length; i++) {
                String playerUUID = playerUUIDS[i].toString();
                boolean antiGriefEnabledPerPlayer = playerConfigs.getCustomConfig().isConfigurationSection("player." + playerUUID + ".antigrief");
                if (antiGriefEnabledPerPlayer) {

                    Object[] locationXList = playerConfigs.getCustomConfig().getList("player." + playerUUID + ".antigrief.location.1").toArray();
                    Object[] locationZList =  playerConfigs.getCustomConfig().getList("player." + playerUUID + ".antigrief.location.2").toArray();

                    double x1 = (Double)locationXList[0];
                    double z1 = (Double)locationXList[1];

                    double x2 = (Double)locationZList[0];
                    double z2 = (Double)locationZList[1];

                    if (bbh.isWithinClaimedArea(x1,x2,z1,z2,locationX,locationZ)) {
                        if (bbh.playerCanBreakBlock(playerUUID,player)) {
                            CommandUtils.sendBannerMessage(player, "&cHey! you can't do that here!");
                            e.setCancelled(true);
                        }
                    }
                }
            }
        } catch (NullPointerException ex) {
        }


    }
}
