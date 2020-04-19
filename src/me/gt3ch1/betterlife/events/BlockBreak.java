package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

                    Object[] locationXList = playerConfigs.getCustomConfig().getList("player." + playerUUID + ".antigrief.location.x").toArray();
                    Object[] locationZList =  playerConfigs.getCustomConfig().getList("player." + playerUUID + ".antigrief.location.z").toArray();

                    double x1 = (Double)locationXList[0];
                    double x2 = (Double)locationXList[1];

                    double z1 = (Double)locationZList[0];
                    double z2 = (Double)locationZList[1];

                    if (bbh.isWithinClaimedArea(x1,x2,z1,z2,locationX,locationZ)) {
                        if (bbh.playerCanBreakBlock(playerUUID,player)) {
                            CommandUtils.sendBannerMessage(player, "&cHey! you can't do that here!");
                            e.setCancelled(true);
                            break;
                        }
                    }
                }
            }
        } catch (NullPointerException ex) {
        }


    }
}
