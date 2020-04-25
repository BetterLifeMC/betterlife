package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Contains the events fired that are related to anti-griefing.
 *
 * @author gt3ch1
 */
public class BlockBreak implements Listener {

    private static BlockBreakHelper bbh;
    PlayerConfigurationHandler playerConfigs = CommandUtils.getPlayerConfiguration();
    Object[] playerUUIDS;
    boolean antiGriefEnabledPerPlayer = false;
    Location loc1, loc2;

    @EventHandler
    public void betterLifeAntiGriefBlockBreakEvent(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if (Main.isUsingSql)
            playerUUIDS = playerConfigs.getSqlRow("uuid").toArray();
        else
            playerUUIDS = playerConfigs.getCustomConfig().getConfigurationSection("player").getKeys(false).toArray();
        for (int i = 0; i < playerUUIDS.length; i++) {

            String playerUUID = playerUUIDS[i].toString();

            try {
                antiGriefEnabledPerPlayer = Boolean.valueOf(playerConfigs.getBooleanValue("antigrief.enabled", playerUUID));
                loc1 = (Location) playerConfigs.get("antigrief.location.a", playerUUID);
                loc2 = (Location) playerConfigs.get("antigrief.location.b", playerUUID);
            } catch (Exception ex) {
                String locationString1 = playerConfigs.get("antigrief.location.a", playerUUID).toString()
                        .replace("Location{world=CraftWorld{name", "").replace("}", "");
                String[] splitLocString1 = locationString1.split(",");
                String[] newLoc1 = new String[splitLocString1.length];

                String locationString2 = playerConfigs.get("antigrief.location.b", playerUUID).toString()
                        .replace("Location{world=CraftWorld{name", "").replace("}", "");
                String[] splitLocString2 = locationString2.split(",");
                String[] newLoc2 = new String[splitLocString2.length];

                for (int x = 0; x < splitLocString1.length; x++) {
                    newLoc1[x] = splitLocString1[x].split("=")[1];
                }
                for (int x = 0; x < splitLocString2.length; x++) {
                    newLoc2[x] = splitLocString2[x].split("=")[1];
                }
                loc1 = new Location(Bukkit.getWorld(newLoc1[0]), Double.valueOf(newLoc1[1]), Double.valueOf(newLoc1[2]), Double.valueOf(newLoc1[3]), Float.valueOf(newLoc1[4]), Float.valueOf(newLoc1[5]));
                loc2 = new Location(Bukkit.getWorld(newLoc2[0]), Double.valueOf(newLoc2[1]), Double.valueOf(newLoc2[2]), Double.valueOf(newLoc2[3]), Float.valueOf(newLoc2[4]), Float.valueOf(newLoc2[5]));

            }
            if (antiGriefEnabledPerPlayer) {
                try {
                    if (bbh.isWithinClaimedArea(loc1, loc2, block)) {
                        if (!bbh.playerCanBreakBlock(playerUUID, player)) {
                            CommandUtils.sendBannerMessage(player, "&bHey! You can't do that here!");
                            e.setCancelled(true);
                        }
                        System.out.println("Is in claimed area!");
                    }
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }else{
                System.out.println("Not enabled for player : " + playerUUID);
            }
        }

    }
}
