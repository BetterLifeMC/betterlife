package me.gt3ch1.betterlife.eventhelpers;

import org.bukkit.entity.Player;

public class BlockBreakHelper {
    /**
     * Determines whether or not the location? variables are within the area determined
     * by blockLocation?{1..2}
     *
     * @param x1
     * @param x2
     * @param z1
     * @param z2
     * @param locationX
     * @param locationZ
     * @return boolean
     */
    public static boolean isWithinClaimedArea(double x1, double x2, double z1,
                                              double z2, double locationX, double locationZ) {

        boolean xComparator;
        boolean zComparator;
        boolean x1Greater = isX1GreaterOrEqualToThanX2(x1,x2);
        boolean z1Greater = isZ1GreaterOrEqualToThanZ2(z1,z2);
        if(x1Greater)
            xComparator = locationX  >= x2 && locationX <= x1;
        else
            xComparator = locationX >= x1 && locationX <= x2;

        if(z1Greater)
            zComparator = locationZ >= z2 && locationZ <= z1;
        else
            zComparator = locationZ >= z1 && locationZ <= z2;

        return (xComparator && zComparator);

    }

    private static boolean isX1GreaterOrEqualToThanX2(double x1, double x2){
        return(x1>=x2);
    }
    private static boolean isZ1GreaterOrEqualToThanZ2(double z1, double z2){
        return(z1>=z2);
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
        return (!playerInteracting.getUniqueId().toString().equalsIgnoreCase(ownerUUID)
                && !(playerInteracting.isOp() || playerInteracting.hasPermission("betterlife.antigrief.bypass")));
    }
}
