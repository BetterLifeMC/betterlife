package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        Particle newPlayerParticle = Particle.valueOf(CommandUtils.getMainConfiguration().getCustomConfig().getString("defaultParticle").toUpperCase());

        try {

            newPlayerParticle = Particle.valueOf(CommandUtils.getPlayerConfiguration().getCustomConfig().getString("player." + p.getUniqueId().toString() + ".trail"));

        } catch (Exception ex) {

            try {
                CommandUtils.getPlayerConfiguration().setValue("trails.trail",newPlayerParticle.toString(),p.getUniqueId().toString());
                CommandUtils.getPlayerConfiguration().setValue("trails.enabled",false,p.getUniqueId().toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }
    }
}
