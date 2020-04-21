package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        Particle newPlayerParticle = Particle.valueOf(CommandUtils.getMainConfiguration().getCustomConfig().getString("defaultParticle").toUpperCase());


        try {

            newPlayerParticle = Particle.valueOf(CommandUtils.getPlayerConfiguration().getCustomConfig().getString("player." + p.getUniqueId().toString() + ".trail"));

        } catch (Exception ex) {

            CommandUtils.getPlayerConfiguration().getCustomConfig().set("player." +
                    p.getUniqueId().toString() + ".trail", newPlayerParticle.toString());
            CommandUtils.getPlayerConfiguration().getCustomConfig().set("player." +
                    p.getUniqueId().toString() + ".trails.enabled", false);
            CommandUtils.getPlayerConfiguration().saveCustomConfig();

        }
    }
}
