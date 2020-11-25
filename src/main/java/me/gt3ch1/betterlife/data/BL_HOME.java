package me.gt3ch1.betterlife.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BL_HOME {
    private final Sql sql = Main.sql;
    private ResultSet rs;

    public HashMap<UUID, LinkedHashMap<String, Location>> homesPerPlayer = new HashMap<>();

    public LinkedHashMap<String, Location> getHomes(UUID playerUUID) {
        if (!homesPerPlayer.containsKey(playerUUID)) {
            homesPerPlayer.put(playerUUID, getHomesSql(playerUUID));
        }
        return homesPerPlayer.get(playerUUID);
    }

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
        homesPerPlayer.get(player.getUniqueId()).put(home, player.getLocation());
        sql.modifyHome(query, home);
    }

    public void delHome(Player player, String home) {
        String query = "DELETE FROM BL_HOME WHERE `UUID` = '" + player.getUniqueId() + "' AND `Home` = ?;";
        sql.modifyHome(query, home);
        Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
        homesPerPlayer.get(p.getUniqueId()).remove(homesPerPlayer.get(p.getUniqueId()).get(home));
        getHomes(p.getUniqueId());
    }
}
