package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
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
public class BlockBreak extends BlockBreakHelper implements Listener {

    private static BlockBreakHelper bbh;

    @EventHandler
    public void betterLifeAntiGriefBlockBreakEvent(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        playerUUIDS = bbh.setUpAntiGrief();
        bbh.checkPlayersBreakBlock(playerUUIDS, block, player, e);


    }
}
