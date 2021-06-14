package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Contains listeners for chat events for better life.
 */
public class PlayerChatEvent implements Listener {

    /**
     * Checks to see if the player is muted, and if so, cancel the chat message.
     * @param e Chat event to check.
     */
    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.MUTE_PER_PLAYER;
        if (Main.bl_player.getPlayerToggle(p.getUniqueId(), type) && !p.hasPermission("betterlife.mute.bypass"))
            e.setCancelled(true);
    }
}
