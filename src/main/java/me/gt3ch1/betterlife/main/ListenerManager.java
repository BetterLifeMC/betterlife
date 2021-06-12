package me.gt3ch1.betterlife.main;

import com.google.inject.Inject;
import me.gt3ch1.betterlife.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;


import static me.gt3ch1.betterlife.main.BetterLife.doBukkitLog;

/**
 * ListenerManager methods for the plugin.
 *
 * @author gt3ch1
 * @author Starmism
 */
public class ListenerManager {

    private final PlayerJoin playerJoin;
    private final PlayerMove playerMove;
    private final BlockFade blockFade;
    private final PlayerChatEvent playerChatEvent;

    @Inject
    public ListenerManager(PlayerJoin playerJoin, PlayerMove playerMove,
                           BlockFade blockFade, PlayerChatEvent playerChatEvent) {
        this.playerJoin = playerJoin;
        this.playerMove = playerMove;
        this.blockFade = blockFade;
        this.playerChatEvent = playerChatEvent;
    }

    public void setupListeners(BetterLife plugin) {
        plugin.listeners.add(blockFade);
        plugin.listeners.add(playerMove);
        plugin.listeners.add(playerJoin);
        plugin.listeners.add(playerChatEvent);
        /*plugin.listeners.add(new PlayerInteract());
        plugin.listeners.add(new BlockBreak());
        plugin.listeners.add(new BlockExplode());
        plugin.listeners.add(new BlockIgnite());*/

        for (Listener listener : plugin.listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
            doBukkitLog("Enabling listener: " + ChatColor.GOLD + listener);
        }
    }

    public static void disableListeners(BetterLife plugin) {
        HandlerList.unregisterAll(plugin);
        doBukkitLog("Unregistering event listeners.");
    }
}
