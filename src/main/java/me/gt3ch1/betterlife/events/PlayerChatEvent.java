package me.gt3ch1.betterlife.events;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import me.gt3ch1.betterlife.data.Sql;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Contains listeners for chat events for better life.
 */
public class PlayerChatEvent implements Listener {

    private final Sql sql;
    private final BL_PLAYER blPlayer;

    @Inject
    public PlayerChatEvent(Sql sql, BL_PLAYER blPlayer) {
        this.sql = sql;
        this.blPlayer = blPlayer;
    }

    /**
     * Checks to see if the player is muted, and if so, cancel the chat message.
     *
     * @param e Chat event to check.
     */
    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        if (!sql.isSqlConnected()) {
            return;
        }

        Player p = e.getPlayer();
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.MUTE_PER_PLAYER;
        if (blPlayer.getPlayerToggle(p.getUniqueId(), type) && !p.hasPermission("betterlife.mute.bypass")) {
            e.setCancelled(true);
        }
    }
}
