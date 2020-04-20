package me.gt3ch1.betterlife.eventhelpers;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BlockBreakHelper {
    /**
     * Determines whether or not the location? variables are within the area
     *
     * @param x1
     * @param x2
     * @param z1
     * @param z2
     * @param locationX
     * @param locationZ
     * @return boolean
     */

      private static double maxX;
      private static double maxY;
      private static double maxZ;

      private static double minX;
      private static double minY;
      private static double minZ;

      private static UUID worldUniqueId;


    public static boolean isWithinClaimedArea(Location firstPoint, Location secondPoint, Block b) {
        worldUniqueId = firstPoint.getWorld().getUID();

        maxX = Math.max(firstPoint.getX(), secondPoint.getX());
        maxZ = Math.max(firstPoint.getZ(), secondPoint.getZ());

        minX = Math.min(firstPoint.getX(), secondPoint.getX());
        minZ = Math.min(firstPoint.getZ(), secondPoint.getZ());
        Location loc = b.getLocation();
        return loc.getWorld().getUID().equals(worldUniqueId)
                && loc.getX() >= minX && loc.getX() <= maxX
                && loc.getZ() >= minZ && loc.getZ() <= maxZ;

    }

    /**
     * Returns whether or not playerInteracting can intxeract with ownerUUID's claim.  If the playerInteracting's UUID
     * and ownerUUID's UUID matches, then this method returns true.  It will also return true if the player is OP'd, or
     * if the player has the ability to bypass restrictions.a
     *
     * @param ownerUUID
     * @param playerInteracting
     * @return
     */
    public static boolean playerCanBreakBlock(String ownerUUID, Player playerInteracting) {

        return (playerInteracting.getUniqueId().toString().equalsIgnoreCase(ownerUUID))
          || (playerInteracting.isOp() || playerInteracting.hasPermission("betterlife.antigrief.bypass")) &&
                        CommandUtils.getMainConfiguration().getCustomConfig().getStringList("zoneprotection.worlds")
                                .contains(playerInteracting.getWorld().getName());

    }

}
