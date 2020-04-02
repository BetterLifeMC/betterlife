package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.*;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    Main m;

    public PlayerJoin(Main m) {
        this.m = m;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Particle newPlayerParticle = Particle.valueOf(m.getMainConfiguration().getCustomConfig().getString("defaultParticle").toUpperCase());

        try {
            // Try to fetch the player's current trail config setting
            newPlayerParticle = Particle.valueOf(m.getPlayerConfiguration().getCustomConfig().getString("player." + p.getUniqueId().toString() + ".trail"));
        } catch (Exception ex) {
            // If that fails, setup a new config setting for the player
            m.getPlayerConfiguration().getCustomConfig().set("player." + p.getUniqueId().toString() + ".trail", newPlayerParticle.toString());
            m.getPlayerConfiguration().getCustomConfig().set("player." + p.getUniqueId().toString() + ".trails.enabled", false);
            m.getPlayerConfiguration().saveCustomConfig();
            sendBannerMessage(p, ChatColor.AQUA + "Want to enable trail particles? Try /trail help!");
        }
    }
}
