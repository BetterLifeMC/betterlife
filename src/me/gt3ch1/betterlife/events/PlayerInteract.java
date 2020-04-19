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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerInteract implements Listener {

    Location loc1 = null, loc2 = null;

    FileConfiguration mainConfig = CommandUtils.getMainConfiguration().getCustomConfig();
    FileConfiguration playerConfig = CommandUtils.getPlayerConfiguration().getCustomConfig();

    boolean loc1Found = false;
    boolean isEnabled = mainConfig.getBoolean("zoneprotection.enabled");
    boolean playerCanClaim = false;

    //TODO: Change landCost to antiGriefCost
    double landCost = 0.0;
    int antiGreifCost = mainConfig.getInt("zoneprotection.cost");

    Material claimItem = Material.valueOf(mainConfig.getString("zoneprotection.item"));

    @EventHandler
    public void playerClaim(PlayerInteractEvent e) {

        if (claimItem == null)
            claimItem = Material.GOLDEN_SHOVEL;

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        Material material = e.getMaterial();
        Material itemInHand = player.getInventory().getItemInMainHand().getType();


        if (block != null && material != null && itemInHand != null && isEnabled) {
            if (itemInHand == claimItem && e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (!loc1Found) {
                    loc1Found = true;
                    loc1 = block.getLocation();

                    CommandUtils.sendMessage(player, "&dLocation &a1&6 sat to : &7X:&6 " + loc1.getX()
                            + "&7 Y:&6 " + loc1.getY() + "&7 Z:&6 " + loc1.getZ());
                } else {
                    loc1Found = false;
                    loc2 = block.getLocation();
                    double area = doMath(loc1.getX(), loc2.getX(), loc1.getZ(), loc2.getZ());
                    CommandUtils.sendMessage(player, "&dLocation &a2&6 sat to : &7X:&6 " + loc2.getX()
                            + "&7 Y:&6 " + loc2.getY() + "&7 Z:&6 " + loc2.getZ());
                    //TODO: Change to antiGreifCost
                    landCost = area * 25;
                    CommandUtils.sendBannerMessage(player, "&dDo you want to claim this land?\n It will cost you: &c$&l"
                            + landCost + "&r&d to claim. Right click with the claiming item to claim the land.");
                    CommandUtils.sendMessage(player, "&bTotal area: \n" +
                            "  &9→  &7" + area);
                    playerCanClaim = true;
                }
                e.setCancelled(true);
                return;

            }
            if (playerCanClaim && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && itemInHand == claimItem) {
                if (Main.getEconomy().getBalance(player) >= landCost) {
                    String playerString = "player." + player.getUniqueId() + ".antigrief.";
                    List<Double> locationXList = new ArrayList<>();
                    List<Double> locationZList = new ArrayList<>();
                    Collections.sort(locationXList);
                    Collections.sort(locationZList);
                    locationXList.add(loc1.getX());
                    locationXList.add(loc2.getX());


                    locationZList.add(loc1.getZ());
                    locationZList.add(loc2.getZ());

                    playerConfig.set(playerString + "location.x", locationXList);
                    playerConfig.set(playerString + "location.z", locationZList);
                    CommandUtils.getPlayerConfiguration().saveCustomConfig();
                    Main.getEconomy().withdrawPlayer(player, landCost);
                    CommandUtils.sendBannerMessage(player,"&eYou have successfully claimed your plot! Enjoy!");
                } else {
                    CommandUtils.sendBannerMessage(player, "&cYou do not have enough funds!");
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
