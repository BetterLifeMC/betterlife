package me.gt3ch1.betterlife.Main;

import me.gt3ch1.betterlife.events.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class ListenersSetup {

    Main plugin;

    public ListenersSetup(Main m){
        this.plugin=m;
        plugin.listeners.add(new BlockFade());
        plugin.listeners.add(new PlayerMove());
        plugin.listeners.add(new PlayerJoin());
        plugin.listeners.add(new PlayerInteract());
        plugin.listeners.add(new BlockBreak());
        plugin.listeners.add(new BlockExplode());


		for (Listener listener : plugin.listeners) {
            Bukkit.getPluginManager().registerEvents(listener, m);
            plugin.getLogger().info("Enabling listener: " + listener);
        }
    }
}
