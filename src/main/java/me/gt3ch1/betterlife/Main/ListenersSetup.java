package me.gt3ch1.betterlife.Main;

import me.gt3ch1.betterlife.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Enabled the listeners needed for BetterLife to run properly.
 *
 * @author gt3ch1
 */
public class ListenersSetup {


    /**
     * Enables the needed listeners for BetterLife.
     *
     * @param plugin
     */
    public ListenersSetup(BetterLife plugin) {


    }

    public static void setupListeners(BetterLife plugin) {
        plugin.listeners.add(new BlockFade());
        plugin.listeners.add(new PlayerMove());
        plugin.listeners.add(new PlayerJoin());
        plugin.listeners.add(new PlayerChatEvent());
        /*plugin.listeners.add(new PlayerInteract());
        plugin.listeners.add(new BlockBreak());
        plugin.listeners.add(new BlockExplode());
        plugin.listeners.add(new BlockIgnite());*/

        for (Listener listener : plugin.listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
            BetterLife.doBukkitLog("Enabling listener: " + ChatColor.GOLD + listener.toString());
        }
    }

    public static void disableListeners(BetterLife plugin) {
        HandlerList.unregisterAll(plugin);
        BetterLife.doBukkitLog("Unregistering event listeners.");
    }
}
