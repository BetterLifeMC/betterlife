package me.gt3ch1.betterlife.Main;

import java.util.ArrayList;
import me.gt3ch1.betterlife.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

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
    public ListenersSetup(Main plugin) {


    }

    public static void setupListeners(Main plugin) {
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
            Main.doBukkitLog("Enabling listener: " + ChatColor.GOLD + listener.toString());
        }
    }

    public static void disableListeners(Main plugin) {
        HandlerList.unregisterAll(plugin);
        Main.doBukkitLog("Unregistering event listeners.");
    }
}
