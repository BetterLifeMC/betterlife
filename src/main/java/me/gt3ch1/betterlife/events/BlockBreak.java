/*
package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

*/
/**
 * Contains the events fired that are related to anti-griefing.
 *
 * @author gt3ch1
 *//*

public class BlockBreak extends BlockBreakHelper implements Listener {


    @EventHandler
    public void betterLifeAntiGriefBlockBreakEvent(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        Block block;
        try {
            block = e.getClickedBlock();
            checkPlayersBreakBlock(CommandUtils.playerUUIDs, block, player, e);
        }catch(Exception ignored){}
    }
}
*/
