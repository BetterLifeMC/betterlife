package me.gt3ch1.betterlife.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;
import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BL_HOME {
    private final Sql sql = Main.sql;
    private ResultSet rs;

    public LinkedHashMap<String, Location> getHomes(UUID playerUUID) {
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
                    rs.getFloat("Pitch"),
                    rs.getFloat("Yaw")));
            }
            return homeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
        sql.addHome(query, home);
    }
}
