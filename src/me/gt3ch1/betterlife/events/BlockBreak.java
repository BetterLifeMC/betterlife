package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/** Contains the events fired that are related to anti-griefing.
 * @author gt3ch1
 */
public class BlockBreak implements Listener {

    PlayerConfigurationHandler playerConfigs = CommandUtils.getPlayerConfiguration();
    Object[] playerUUIDS;
    private static BlockBreakHelper bbh;

    @EventHandler
    public void betterLifeAntigriefBlockBreakEvent(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        playerUUIDS = playerConfigs.getCustomConfig().getConfigurationSection("player").getKeys(false).toArray();
        Location location = null;
        double locationX = 0.0;
        double locationZ = 0.0;
        try {
            location = block.getLocation();
            locationX = location.getX();
            locationZ = location.getZ();
        } catch (NullPointerException ex) {}

            for (int i = 0; i < playerUUIDS.length; i++) {

                String playerUUID = playerUUIDS[i].toString();
                Location loc1,loc2;

                boolean antiGriefEnabledPerPlayer = false;
                try {
                    antiGriefEnabledPerPlayer = playerConfigs.getCustomConfig().isConfigurationSection("player." + playerUUID + ".antigrief");
                    loc1 = (Location) playerConfigs.getCustomConfig().get("player." + playerUUID + ".antigrief.location.a");
                    loc2 = (Location) playerConfigs.getCustomConfig().get("player." + playerUUID + ".antigrief.location.b");
                }catch(Exception ex){
                    break;
                }
                if (antiGriefEnabledPerPlayer) {
                    //TODO:
                    // https://www.spigotmc.org/threads/checking-if-location-is-in-an-area.293771/?__cf_chl_jschl_tk__=804fccba25a70d6d60a8506cc7ab2884f7570d2c-1587361040-0-Aej_8dExCvRix2dyw9P9yuv5HG-sWxa2nH2qnSJoMay_Vc50OEUKGpctbazC4wfo54vw-EsZUombuhncVUNG-BgtkMhuHjA4RpiOkhphGStZ9hnlCsJZ6YeNj_J81sDjaLkACEvyMOcKaVDMbf0VDRNicYy3Kn0fOdiUrVoyeNlb1D9dB8b0-5KHwGu7kO2VCTo7s3sFrX1MNA-Bb1n05Ysl8DbrkvIeiY-QmGD82D1na4Zu0Zw7QtZqtC-_S9DORS0_bOQcOxcIJs8n_uOwopJFQS4dm1bBeJW6cL992r10nXNjKKjbYyZIPhf8Xf5VmXAFX3a8LlI2T89NtnexWRw

                    try {
                        if (bbh.isWithinClaimedArea(loc1,loc2,block))
                            if (!bbh.playerCanBreakBlock(playerUUID, player)) {
                                CommandUtils.sendBannerMessage(player,"&bHey! You can't do that here!t");
                                e.setCancelled(true);
                            }
                    }catch(NullPointerException npe){}
                }
            }

    }
}
