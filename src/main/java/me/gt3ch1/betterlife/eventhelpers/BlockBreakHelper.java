/*
package me.gt3ch1.betterlife.eventhelpers;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

*/
/**
 * This class contains methods relating to the BetterLife anti-grief system, which is used to prevent
 * player modification of player-defined locations.
 *//*

public class BlockBreakHelper extends CommandUtils {

    private static double maxX;
    private static double maxY;
    private static double maxZ;

    private static double minX;
    private static double minY;
    private static double minZ;
    private static UUID worldUniqueId;

    protected Object[] playerUUIDS = pch.antiGriefLocation1PerPlayer.keySet().toArray();

    */
/**
     * Returns whether or not the Block b resides within the two points given.
     *
     * @param firstPoint
     * @param secondPoint
     * @param b
     * @return
     *//*

    public static boolean isWithinClaimedArea(Location firstPoint, Location secondPoint, Block b) {
        worldUniqueId = firstPoint.getWorld().getUID();

        maxX = Math.max(firstPoint.getX(), secondPoint.getX());
        maxY = Math.max(firstPoint.getY(), secondPoint.getY());
        maxZ = Math.max(firstPoint.getZ(), secondPoint.getZ());

        minX = Math.min(firstPoint.getX(), secondPoint.getX());
        minY = Math.min(firstPoint.getY(), secondPoint.getY());
        minZ = Math.min(firstPoint.getZ(), secondPoint.getZ());

        Location block = b.getLocation();
        return block.getWorld().getUID().equals(worldUniqueId)
                && block.getX() >= minX && block.getX() <= maxX
                && block.getY() >= minY && block.getY() <= maxY
                && block.getZ() >= minZ && block.getZ() <= maxZ;

    }

    */
/**
     * Returns whether or not playerInteracting can interact with ownerUUID's claim.  If the playerInteracting's UUID
     * and ownerUUID's UUID matches, then this method returns true.  It will also return true if the player is OP'd, or
     * if the player has the ability to bypass restrictions.a
     *
     * @param ownerUUID
     * @param playerInteracting
     * @return
     *//*

    public static boolean playerCanBreakBlock(UUID ownerUUID, Player playerInteracting) {

        return (playerInteracting.getUniqueId().toString().equalsIgnoreCase(ownerUUID.toString()))
                || (playerInteracting.isOp() || playerInteracting.hasPermission("betterlife.antigrief.bypass")) &&
                CommandUtils.ch.getCustomConfig().getStringList("zoneprotection.worlds")
                        .contains(playerInteracting.getWorld().getName());

    }

    */
/**
     * Checks if the player can break a block.
     *
     * @param playerUUIDS
     * @param block
     * @param player
     * @param e
     *//*

    public static void checkPlayersBreakBlock(Object[] playerUUIDS, Block block, Player player, PlayerInteractEvent e) {
        Location loc1, loc2;
        for (Object uuid : playerUUIDS) {

            UUID playerUUID = player.get;

            loc1 = pch.antiGriefLocation1PerPlayer.get(playerUUID);
            loc2 = pch.antiGriefLocation2PerPlayer.get(playerUUID);

            if (isWithinClaimedArea(loc1, loc2, block))
                if (!playerCanBreakBlock(playerUUID, player)) {
                    CommandUtils.sendMessage(player, "&bHey! You can't do that here!", true);
                    e.setCancelled(true);
                }
        }
    }

}
*/
