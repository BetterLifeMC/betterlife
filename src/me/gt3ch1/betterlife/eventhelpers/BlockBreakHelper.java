package me.gt3ch1.betterlife.eventhelpers;

import org.bukkit.entity.Player;

public class BlockBreakHelper {
    /**
     * Determines whether or not the location? variables are within the area determined
     * by blockLocation?{1..2}
     *
     * @param blockLocationX1
     * @param blockLocationX2
     * @param blockLocationZ1
     * @param blockLocationZ2
     * @param locationX
     * @param locationZ
     * @return boolean
     */
    public static boolean isWithinClaimedArea(double blockLocationX1, double blockLocationX2, double blockLocationZ1,
                                              double blockLocationZ2, double locationX, double locationZ) {
        return ((locationX >= blockLocationX2 && locationX <= blockLocationX1) &&
                (locationZ >= blockLocationZ2 + 1 && locationZ <= blockLocationZ1 + 1));
    }

    /**
     * Returns whether or not playerInteracting can interact with ownerUUID's claim.  If the playerInteracting's UUID
     * and ownerUUID's UUID matches, then this method returns true.  It will also return true if the player is OP'd, or
     * if the player has the ability to bypass restrictions.a
     *
     * @param ownerUUID
     * @param playerInteracting
     * @return
     */
    public static boolean playerCanBreakBlock(String ownerUUID, Player playerInteracting) {
        return (!playerInteracting.getUniqueId().toString().equalsIgnoreCase(ownerUUID)
                && !(playerInteracting.isOp() || playerInteracting.hasPermission("betterlife.antigrief.bypass")));
    }
}
