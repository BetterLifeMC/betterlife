package me.gt3ch1.betterlife.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import me.gt3ch1.betterlife.Main.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * @author gt3ch1
 * @author starmism
 * @version 12/2/20
 * Project betterlife
 */
public class BL_WARP {
    private final Sql sql = Main.sql;
    private ResultSet rs;

    public static LinkedHashMap<String, Location> warps = new LinkedHashMap<>();


    /**
     * Gets all of the warps.
     *
     * @return All of the warps.
     */
    public LinkedHashMap<String, Location> getWarps() {
        if (warps.size() == 0)
            warps = getWarpsSql();
        return warps;
    }

    /**
     * Gets all the warps from the SQL backend.
     * @return All of the warps from the SQL backend.
     */
    private LinkedHashMap<String, Location> getWarpsSql() {
        String query = "SELECT * FROM `BL_WARP`;";
        LinkedHashMap<String, Location> warpList = new LinkedHashMap<>();

        try {
            rs = sql.executeQuery(query);
            while (rs.next()) {
                warpList.put(rs.getNString("Name"), new Location(
                        Bukkit.getWorld(rs.getString("World")),
                        rs.getDouble("X"),
                        rs.getDouble("Y"),
                        rs.getDouble("Z"),
                        rs.getFloat("Yaw"),
                        rs.getFloat("Pitch")));
            }
            return warpList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a new warp at the players location with the given warp name.
     *
     * @param player Player who sat the warp.
     * @param warp   Name of the warp.
     */
    public void addWarp(Player player, String warp) {

        String query = "INSERT INTO BL_WARP VALUES ("
                + "'" + warp + "',"
                + player.getLocation().getX() + ","
                + player.getLocation().getY() + ","
                + player.getLocation().getZ() + ","
                + "'" + player.getLocation().getWorld().getName() + "',"
                + player.getLocation().getYaw() + ","
                + player.getLocation().getPitch()
                + ")";
        warps.put(warp, player.getLocation());
        sql.executeUpdate(query);
        Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
    }

    /**
     * Deletes the given warp
     *
     * @param home   Name of the warp to delete.
     * @return True if the given warp exists and was deleted.
     */
    public boolean delWarp(String home) {
        if (warps.containsKey(home))
            if (warps.containsKey(home)) {
                String query = "DELETE FROM BL_WARP WHERE `Name` = '" + home + "' AND `Name` = ?;";
                sql.modifyHome(query, home);
                Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
                warps.remove(home);
                return true;
            }

        return false;
    }
}
