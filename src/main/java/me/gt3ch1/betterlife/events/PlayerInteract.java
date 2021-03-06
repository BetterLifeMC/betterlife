//TODO: Re-implement anti grief
/*
package me.gt3ch1.betterlife.events;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

*/
/**
 * Contains listeners that allow a player to claim piece of land to disable outside
 * modifications from other players.
 *
 * @author gt3ch1
 *//*

public class PlayerInteract extends PlayerAccessHelper implements Listener {

    Location loc1 = null, loc2 = null;

    FileConfiguration mainConfig = CommandUtils.ch.getCustomConfig();

    boolean loc1Found = false;
    boolean isEnabled = mainConfig.getBoolean("zoneprotection.enabled");
    boolean playerCanClaim = false;

    double antiGriefCost = mainConfig.getDouble("zoneprotection.cost");
    double landCost;
    Material claimItem = Material.valueOf(mainConfig.getString("zoneprotection.item"));

    @EventHandler
    public void playerClaim(PlayerInteractEvent e) {

        if (claimItem == null)
            claimItem = Material.GOLDEN_SHOVEL;

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        Material itemInHand = player.getInventory().getItemInMainHand().getType();
        boolean isInWorld = CommandUtils.ch.getCustomConfig().getStringList("zoneprotection.worlds").contains(player.getWorld().getName());

        if (block != null && isEnabled && isInWorld) {

            if (itemInHand == claimItem && e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

                if (!loc1Found) {

                    loc1Found = true;
                    loc1 = block.getLocation();

                    CommandUtils.sendMessage(player, "&dLocation &a1&6 sat to : &7X:&6 " + loc1.getX()
                            + "&7 Y:&6 " + loc1.getY() + "&7 Z:&6 " + loc1.getZ(), false);

                } else {

                    loc1Found = false;
                    loc2 = block.getLocation();
                    double area = doMath(loc1.getX(), loc2.getX(), loc1.getZ(), loc2.getZ());
                    CommandUtils.sendMessage(player, "&dLocation &a2&6 sat to : &7X:&6 " + loc2.getX()
                            + "&7 Y:&6 " + loc2.getY() + "&7 Z:&6 " + loc2.getZ(), false);
                    landCost = area * antiGriefCost;
                    CommandUtils.sendMessage(player, "&dDo you want to claim this land?\n It will cost you: &c$&l"
                            + landCost + "&r&d to claim. Right click with the claiming item to claim the land.", true);
                    CommandUtils.sendMessage(player, "&bTotal area: \n" +
                            "  &9→  &7" + area, false);
                    playerCanClaim = true;

                }

                e.setCancelled(true);
                return;

            }
            if (playerCanClaim && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && itemInHand == claimItem) {

                if (Main.economy.getBalance(player) >= landCost) {
                    pch.setValue("antigrief.location.a", loc1, player.getUniqueId());
                    pch.setValue("antigrief.location.b", loc2, player.getUniqueId());
                    pch.setValue("antigrief.enabled", true, player.getUniqueId());
                    try{
                        pch.antiGriefLocation1PerPlayer.replace(player.getUniqueId(), loc1);
                        pch.antiGriefLocation2PerPlayer.replace(player.getUniqueId(), loc2);
                    }catch(Exception ex) {
                        pch.antiGriefLocation1PerPlayer.put(player.getUniqueId(), loc1);
                        pch.antiGriefLocation2PerPlayer.put(player.getUniqueId(), loc2);
                    }
                    Main.economy.withdrawPlayer(player, landCost);
                    CommandUtils.sendMessage(player, "&eYou have successfully claimed your plot! Enjoy!", true);

                } else {

                    CommandUtils.sendMessage(player, "&cYou do not have enough funds!", true);

                }

                playerCanClaim = false;

            }
        }
    }

    public double doMath(double x1, double x2, double z1, double z2) {

        double distanceX;
        double distanceZ;

        x1 = Math.abs(x1);
        x2 = Math.abs(x2);

        z1 = Math.abs(z1);
        z2 = Math.abs(z2);

        if (x1 < x2)
            distanceX = x2 - x1;
        else
            distanceX = x1 - x2;

        if (z1 < z2)
            distanceZ = z2 - z1;
        else
            distanceZ = z1 - z2;

        distanceX++;
        distanceZ++;

        return distanceX * distanceZ;

    }
}
*/
