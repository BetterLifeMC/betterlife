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

    /**
     * @param playerUUID The UUID we are getting the toggle for.
     * @param type The Type of the toggle
     * @return Varies, depending on the context that the player sat.
     */
    public boolean getPlayerToggle(UUID playerUUID, BL_PLAYER_ENUM type){
        switch(type){
            case TRAIL_ENABLED_PER_PLAYER:
                if(!trailEnabledPerPlayer.containsKey(playerUUID))
                    trailEnabledPerPlayer.put(playerUUID, getPlayerToggleSQL(playerUUID,type.getType()));
                return trailEnabledPerPlayer.get(playerUUID);
            case ROADBOOST_PER_PLAYER:
                if(!roadboostPerPlayer.containsKey(playerUUID))
                    roadboostPerPlayer.put(playerUUID,getPlayerToggleSQL(playerUUID,type.getType()));
                return roadboostPerPlayer.get(playerUUID);
            default:
                return false;
        }
    }

    /**
     * Gets the given toggle from the backend SQL database.
     * @param playerUUID PlayerUUID to pull information from.
     * @param toggle Toggle to search for.
     * @return Varies, depending on the context that the player sat.
     */
    private boolean getPlayerToggleSQL(UUID playerUUID, String toggle) {
        String query =
                "SELECT `" + toggle + "` FROM `BL_PLAYER` WHERE `UUID` = '" + playerUUID.toString()
                        + "'";

        try {
            rs = sql.executeQuery(query);
            if (rs.next()) {
                return rs.getBoolean(toggle);
            } else {
                insertNewPlayer(playerUUID);
                getPlayerToggleSQL(playerUUID, toggle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param playerUUID The UUID we are getting the toggle for.
     * @param type The Type of the toggle
     * @return Varies, depending on the context that the player sat.
     */
    public String getPlayerString(UUID playerUUID, BL_PLAYER_ENUM type) {
        switch (type) {
            case TRAIL_PER_PLAYER:
                if (!trailPerPlayer.containsKey(playerUUID))
                    trailPerPlayer.put(playerUUID, getPlayerStringSQL(playerUUID, type.getType()));
                return trailPerPlayer.get(playerUUID);
            default:
                return null;
        }
    }

    /**
     * Searches the sql backend for the given player uuid.
     * @param playerUUID PlayerUUID to get.
     * @param string String to search for.
     * @return Varies depending on user context.
     */
    private String getPlayerStringSQL(UUID playerUUID, String string) {
        String query =
                "SELECT `" + string + "` FROM `BL_PLAYER` WHERE `UUID` = '" + playerUUID.toString()
                        + "'";

        try {
            rs = sql.executeQuery(query);
            if (rs.next()) {
                return rs.getString(string);
            } else {
                insertNewPlayer(playerUUID);
                getPlayerStringSQL(playerUUID, string);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setPlayerToggle(UUID playerUUID, BL_PLAYER_ENUM type){
        boolean currentVal = false;
        switch(type){
            case TRAIL_ENABLED_PER_PLAYER:
                currentVal = trailEnabledPerPlayer.get(playerUUID);
                trailEnabledPerPlayer.put(playerUUID, !currentVal);
                setPlayerToggleSQL(playerUUID,type);
                break;
            case ROADBOOST_PER_PLAYER:
                currentVal = roadboostPerPlayer.get(playerUUID);
                roadboostPerPlayer.put(playerUUID,!currentVal);
                setPlayerToggleSQL(playerUUID, type);
                break;
            default:
                break;
        }
    }

    private void setPlayerToggleSQL(UUID playerUUID, BL_PLAYER_ENUM toggle) {
        String query;

        try {
            rs = sql.executeQuery(
                    "SELECT * FROM `BL_PLAYER` WHERE `UUID` = '" + playerUUID.toString() + "'");

            if (rs.next()) {
                query = "UPDATE `BL_PLAYER` SET `" + toggle + "` = '"
                        + (getPlayerToggleSQL(playerUUID, toggle) ? 0 : 1) + "' WHERE `UUID` = '"
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
