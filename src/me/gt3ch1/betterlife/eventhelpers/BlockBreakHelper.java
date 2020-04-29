package me.gt3ch1.betterlife.eventhelpers;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

/**
 * This class contains methods relating to the BetterLife anti-grief system, which is used to prevent
 * player modification of player-defined locations.
 */
public class BlockBreakHelper extends CommandUtils {

    private static double maxX;
    private static double maxY;
    private static double maxZ;

    private static double minX;
    private static double minY;
    private static double minZ;
    private static UUID worldUniqueId;

    protected Object[] playerUUIDS = pch.antiGriefLocation1PerPlayer.keySet().toArray();

    /**
     * Returns whether or not the Block b resides within the two points given.
     *
     * @param firstPoint
     * @param secondPoint
     * @param b
     * @return
     */
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
    public static boolean playerCanBreakBlock(UUID ownerUUID, Player playerInteracting) {

        return (playerInteracting.getUniqueId().toString().equalsIgnoreCase(ownerUUID.toString()))
                || (playerInteracting.isOp() || playerInteracting.hasPermission("betterlife.antigrief.bypass")) &&
                CommandUtils.getMainConfiguration().getCustomConfig().getStringList("zoneprotection.worlds")
                        .contains(playerInteracting.getWorld().getName());

    }

    /**
     * Converts a String into a location.
     *
     * @param antigriefLocation
     * @param playerUUID
     * @return
     */
    public static Location parseLocation(String antigriefLocation, UUID playerUUID) {
        // God help me.
        String locationString1 = CommandUtils.getPlayerConfiguration().get("antigrief.location." + antigriefLocation, playerUUID).toString()
                .replace("Location{world=CraftWorld{name", "").replace("}", "");
        String[] splitLocString1 = locationString1.split(",");
        String[] newLoc1 = new String[splitLocString1.length];

        for (int x = 0; x < splitLocString1.length; x++)
            newLoc1[x] = splitLocString1[x].split("=")[1];
        return new Location(Bukkit.getWorld(newLoc1[0]), Double.valueOf(newLoc1[1]), Double.valueOf(newLoc1[2]), Double.valueOf(newLoc1[3]), Float.valueOf(newLoc1[4]), Float.valueOf(newLoc1[5]));
    }

    /**
     * Sets up the antigrief feature
     *
     * @return
     */
    public static Object[] setUpAntiGrief() {
        return CommandUtils.pch.antiGriefLocation1PerPlayer.keySet().toArray();
    }

    /**
     * Checks if the player can break a block.
     *
     * @param playerUUIDS
     * @param block
     * @param player
     * @param e
     */
    public static void checkPlayersBreakBlock(Object[] playerUUIDS, Block block, Player player, PlayerInteractEvent e) {
        Location loc1, loc2;
        for (int i = 0; i < playerUUIDS.length; i++) {

            UUID playerUUID = UUID.fromString(playerUUIDS[i].toString());

            loc1 = pch.antiGriefLocation1PerPlayer.get(playerUUID);
            loc2 = pch.antiGriefLocation2PerPlayer.get(playerUUID);

            if (isWithinClaimedArea(loc1, loc2, block))
                if (!playerCanBreakBlock(playerUUID, player)) {
                    CommandUtils.sendBannerMessage(player, "&bHey! You can't do that here!");
                    e.setCancelled(true);
                }


        }
    }

}
