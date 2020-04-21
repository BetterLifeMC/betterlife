package me.gt3ch1.betterlife.Main;

import me.gt3ch1.betterlife.events.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * Enabled the listeners needed for BetterLife to run properly.
 * @author gt3ch1
 */
public class ListenersSetup {


    /**
     * Enables the needed listeners for BetterLife.
     * @param plugin
     */
    public ListenersSetup(Main plugin){

        plugin.listeners.add(new BlockFade());
        plugin.listeners.add(new PlayerMove());
        plugin.listeners.add(new PlayerJoin());
        plugin.listeners.add(new PlayerInteract());
        plugin.listeners.add(new BlockBreak());
        plugin.listeners.add(new BlockExplode());
        plugin.listeners.add(new BlockIgnite());

		for (Listener listener : plugin.listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
            plugin.getLogger().info("Enabling listener: " + listener.toString());
        }
    }
}
