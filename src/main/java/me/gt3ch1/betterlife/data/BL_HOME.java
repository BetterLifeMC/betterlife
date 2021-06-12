package me.gt3ch1.betterlife.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import me.gt3ch1.betterlife.Main.BetterLife;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BL_HOME {

    private final Sql sql = BetterLife.sql;
    private ResultSet rs;

    public HashMap<UUID, LinkedHashMap<String, Location>> homesPerPlayer = new HashMap<>();

    /**
     * Gets all of the homes belonging to player UUID.
     *
     * @param playerUUID Player UUID to find all the homes for.
     * @return All of the homes belonging to the given UUID.
     */
    public LinkedHashMap<String, Location> getHomes(UUID playerUUID) {
        if (!homesPerPlayer.containsKey(playerUUID)) {
            homesPerPlayer.put(playerUUID, getHomesSql(playerUUID));
        }
        return homesPerPlayer.get(playerUUID);
    }

    /**
     * Gets all of the homes for the given UUID from the SQL backend.
     *
     * @param playerUUID Player UUID to fetch all homes for.
     * @return All of the homes from the SQL backend.
     */
    private LinkedHashMap<String, Location> getHomesSql(UUID playerUUID) {
        String query = "SELECT * FROM `BL_HOME` WHERE `UUID` = '" + playerUUID.toString() + "'";
        LinkedHashMap<String, Location> homeList = new LinkedHashMap<>();

        try {
            rs = sql.executeQuery(query);
            while (rs.next()) {
                homeList.put(rs.getNString("Home"), new Location(
                    Bukkit.getWorld(rs.getString("World")),
                    rs.getDouble("X"),
                    rs.getDouble("Y"),
                    rs.getDouble("Z"),
                    rs.getFloat("Yaw"),
                    rs.getFloat("Pitch")));
            }
            return homeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a new home for the player at the players location with the given home name.
     *
     * @param player Player who owns the home.
     * @param home   Name of the home.
     */
    public void addHome(Player player, String home) {

        String query = "INSERT INTO BL_HOME VALUES ("
            + "'" + player.getUniqueId() + "',"
            + "?,"
            + player.getLocation().getX() + ","
            + player.getLocation().getY() + ","
            + player.getLocation().getZ() + ","
            + "'" + player.getLocation().getWorld().getName() + "',"
            + player.getLocation().getYaw() + ","
            + player.getLocation().getPitch()
            + ")";
        homesPerPlayer.get(player.getUniqueId()).put(home, player.getLocation());
        sql.modifyHome(query, home);
    }

    /**
     * Deletes the given home from the player.
     *
     * @param player Player to remove the home from.
     * @param home   Home to remove from the player.
     * @return True if the given home exists and was deleted.
     */
    public boolean delHome(Player player, String home) {
        if (homesPerPlayer.containsKey(player.getUniqueId())) {
            if (homesPerPlayer.get(player.getUniqueId()).containsKey(home)) {
                String query = "DELETE FROM BL_HOME WHERE `UUID` = '" + player.getUniqueId() + "' AND `Home` = ?;";
                sql.modifyHome(query, home);
                homesPerPlayer.get(player.getUniqueId()).remove(home);
                return true;
            }
        }
        return false;
    }
}
