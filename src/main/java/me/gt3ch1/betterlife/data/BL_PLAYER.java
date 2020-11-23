package me.gt3ch1.betterlife.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class BL_PLAYER {

    private final Sql sql = Main.sql;
    private ResultSet rs;

    //TODO: Re-enable caching
    public HashMap<UUID, Boolean> trailEnabledPerPlayer = new HashMap<>();
    public HashMap<UUID, String> trailPerPlayer = new HashMap<>();
    public HashMap<UUID, Boolean> roadboostPerPlayer = new HashMap<>();
    public HashMap<UUID, Location> antiGriefLocation1PerPlayer = new HashMap<>();
    public HashMap<UUID, Location> antiGriefLocation2PerPlayer = new HashMap<>();


    public boolean getPlayerToggle(UUID playerUUID, String toggle) {
        String query =
            "SELECT `" + toggle + "` FROM `BL_PLAYER` WHERE `UUID` = '" + playerUUID.toString()
                + "'";

        try {
            rs = sql.executeQuery(query);
            if (rs.next()) {
                return rs.getBoolean(toggle);
            } else {
                insertNewPlayer(playerUUID);
                getPlayerToggle(playerUUID, toggle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPlayerString(UUID playerUUID, String string) {
        String query =
            "SELECT `" + string + "` FROM `BL_PLAYER` WHERE `UUID` = '" + playerUUID.toString()
                + "'";

        try {
            rs = sql.executeQuery(query);
            if (rs.next()) {
                return rs.getString(string);
            } else {
                insertNewPlayer(playerUUID);
                getPlayerString(playerUUID, string);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setPlayerToggle(UUID playerUUID, String toggle) {
        String query;

        try {
            rs = sql.executeQuery(
                "SELECT * FROM `BL_PLAYER` WHERE `UUID` = '" + playerUUID.toString() + "'");

            if (rs.next()) {
                query = "UPDATE `BL_PLAYER` SET `" + toggle + "` = '"
                    + (getPlayerToggle(playerUUID, toggle) ? 0 : 1) + "' WHERE `UUID` = '"
                    + playerUUID.toString() + "'";

                Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
                sql.executeUpdate(query);
            } else {
                insertNewPlayer(playerUUID);
                setPlayerToggle(playerUUID, toggle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPlayerString(UUID playerUUID, String string, String newString) {
        String query;

        try {
            rs = sql.executeQuery(
                "SELECT * FROM `BL_PLAYER` WHERE `UUID` = '" + playerUUID.toString() + "'");

            if (rs.next()) {
                query = "UPDATE `BL_PLAYER` SET `" + string + "` = '"
                    + newString + "' WHERE `UUID` = '" + playerUUID.toString() + "'";

                Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
                sql.executeUpdate(query);
            } else {
                insertNewPlayer(playerUUID);
                setPlayerString(playerUUID, string, newString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertNewPlayer(UUID playerUUID) {
        String query = "INSERT INTO `BL_PLAYER` (`UUID`) VALUES ('" + playerUUID.toString() + "')";

        Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
        sql.executeUpdate(query);
    }
}
