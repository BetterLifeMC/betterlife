package me.gt3ch1.betterlife.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.block.data.CraftBlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ComposterClick implements Listener {

	@EventHandler
	public void onBlockClick(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block b = e.getClickedBlock();
			if(b.getType().equals(Material.COMPOSTER)) {
				int currentLevel = Integer.parseInt(b.getBlockData().toString().split("=")[1].split("]")[0]);
				currentLevel++;
				if(currentLevel > 8) {
					currentLevel = 0;
				}
				b.setBlockData(CraftBlockData.newData(Material.COMPOSTER, "[level="+currentLevel+"]"));
				e.getPlayer().sendMessage("Compost level: " + currentLevel);
			}
		}
		
	}

}
