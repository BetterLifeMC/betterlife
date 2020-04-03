package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendBannerMessage;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Particle newPlayerParticle = Particle.valueOf(CommandUtils.getMainConfiguration().getCustomConfig().getString("defaultParticle").toUpperCase());

        try {
            // Try to fetch the player's current trail config setting
            newPlayerParticle = Particle.valueOf(CommandUtils.getPlayerConfiguration().getCustomConfig().getString("player." + p.getUniqueId().toString() + ".trail"));
        } catch (Exception ex) {
            // If that fails, setup a new config setting for the player
            CommandUtils.getPlayerConfiguration().getCustomConfig().set("player." + p.getUniqueId().toString() + ".trail", newPlayerParticle.toString());
            CommandUtils.getPlayerConfiguration().getCustomConfig().set("player." + p.getUniqueId().toString() + ".trails.enabled", false);
            CommandUtils.getPlayerConfiguration().saveCustomConfig();
            sendBannerMessage(p, ChatColor.AQUA + "Want to enable trail particles? Try /trail help!");
        }
    }
}
