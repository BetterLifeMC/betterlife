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

import java.util.UUID;

/**
 * Contains the events fired that are related to anti-griefing.
 *
 * @author gt3ch1
 */
public class BlockBreak extends BlockBreakHelper implements Listener {

    private static BlockBreakHelper bbh;
    Object[] playerUUIDS;
    boolean antiGriefEnabledPerPlayer = false;
    Location loc1, loc2;

    @EventHandler
    public void betterLifeAntiGriefBlockBreakEvent(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if (Main.isUsingSql)
            playerUUIDS = playerConfig.getSqlRow("uuid").toArray();
        else
            playerUUIDS = playerConfig.getCustomConfig().getConfigurationSection("player").getKeys(false).toArray();
        for (int i = 0; i < playerUUIDS.length; i++) {

            UUID playerUUID = UUID.fromString(playerUUIDS[i].toString());

            try {
                antiGriefEnabledPerPlayer = playerConfig.getBooleanValue("antigrief.enabled", playerUUID);
                loc1 = (Location) playerConfig.get("antigrief.location.a", playerUUID);
                loc2 = (Location) playerConfig.get("antigrief.location.b", playerUUID);
            } catch (Exception ex) {

                loc1 = bbh.parseLocation("a",playerUUID);
                loc2 = bbh.parseLocation("b",playerUUID);

            }
            if (antiGriefEnabledPerPlayer) {
                try {
                    if (bbh.isWithinClaimedArea(loc1, loc2, block)) {
                        if (!bbh.playerCanBreakBlock(playerUUID, player)) {
                            CommandUtils.sendBannerMessage(player, "&bHey! You can't do that here!");
                            e.setCancelled(true);
                        }
                    }
                } catch (NullPointerException npe) {
                }
            }
        }

    }
}
