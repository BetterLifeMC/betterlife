package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {
    PlayerConfigurationHandler playerConfig  = CommandUtils.getPlayerConfiguration();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        Particle newPlayerParticle = Particle.valueOf(CommandUtils.getMainConfiguration().getCustomConfig().getString("defaultParticle").toUpperCase());
        
        try{
            playerConfig.getStringValue("trails.enabled", p.getUniqueId());
        }catch (Exception ex){
            playerConfig.setValue("trails.trail", newPlayerParticle.toString(), p.getUniqueId());
            playerConfig.setValue("trails.enabled", false, p.getUniqueId());
        }
        playerConfig.trailEnabledPerPlayer.put(p.getUniqueId(),playerConfig.getBooleanValue("trails.enabled",p.getUniqueId()));
        playerConfig.trailPerPlayer.put(p.getUniqueId(),playerConfig.getStringValue("trails.trail",p.getUniqueId()));
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        playerConfig.trailEnabledPerPlayer.remove(uuid);
        playerConfig.trailPerPlayer.remove(uuid);
    }
}
