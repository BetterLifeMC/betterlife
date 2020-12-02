package me.gt3ch1.betterlife.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.ChatColor;

public class BL_PLAYER {

    private final Sql sql = Main.sql;
    public HashMap<UUID, Boolean> trailEnabledPerPlayer = new HashMap<>();
    public HashMap<UUID, String> trailPerPlayer = new HashMap<>();
    public HashMap<UUID, Boolean> roadboostPerPlayer = new HashMap<>();
    public HashMap<UUID,Boolean> mutePerPlayer = new HashMap<>();
    private ResultSet rs;

    /**
     * @param playerUUID The UUID we are getting the toggle for.
     * @param type       The Type of the toggle
     * @return Varies, depending on the context that the player sat.
     */
    public boolean getPlayerToggle (UUID playerUUID, BL_PLAYER_ENUM type) {
        switch (type) {
            case TRAIL_ENABLED_PER_PLAYER:
                if (!trailEnabledPerPlayer.containsKey(playerUUID)) {
                    trailEnabledPerPlayer.put(playerUUID, getPlayerToggleSQL(playerUUID, type));
                }
                return trailEnabledPerPlayer.get(playerUUID);
            case ROADBOOST_PER_PLAYER:
                if (!roadboostPerPlayer.containsKey(playerUUID)) {
                    roadboostPerPlayer.put(playerUUID, getPlayerToggleSQL(playerUUID, type));
                }
                return roadboostPerPlayer.get(playerUUID);
            case MUTE_PER_PLAYER:
                if(!mutePerPlayer.containsKey(playerUUID)){
                    mutePerPlayer.put(playerUUID, getPlayerToggleSQL(playerUUID, type));
                }
                return mutePerPlayer.get(playerUUID);
            default:
                return false;
        }
    }

    /**
     * Gets the given toggle from the backend SQL database.
     *
     * @param playerUUID PlayerUUID to pull information from.
     * @param toggle     Toggle to search for.
     * @return Varies, depending on the context that the player sat.
     */
    private boolean getPlayerToggleSQL(UUID playerUUID, BL_PLAYER_ENUM toggle) {
        String query =
                "SELECT `" + toggle.getColumn() + "` FROM `" + toggle.getTable() + "` WHERE `UUID` = '"
                        + playerUUID.toString()
                        + "'";

        try {
            rs = sql.executeQuery(query);
            if (rs.next()) {
                return rs.getBoolean(toggle.getColumn());
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
     * @param type       The Type of the toggle
     * @return Varies, depending on the context that the player sat.
     */
    public String getPlayerString(UUID playerUUID, BL_PLAYER_ENUM type) {
        switch (type) {
            case TRAIL_PER_PLAYER:
                if (!trailPerPlayer.containsKey(playerUUID)) {
                    trailPerPlayer
                            .put(playerUUID, getPlayerStringSQL(playerUUID, type.getColumn()));
                }
                return trailPerPlayer.get(playerUUID);
            default:
                return null;
        }
    }

    /**
     * Searches the sql backend for the given player uuid.
     *
     * @param playerUUID PlayerUUID to get.
     * @param string     String to search for.
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

    /**
     * Sets the given BetterLife player boolean toggle.
     *
     * @param playerUUID Player UUID to set the toggle of.
     * @param type       The toggle to switch.
     */
    public void setPlayerToggle(UUID playerUUID, BL_PLAYER_ENUM type) {
        boolean currentVal;
        switch (type) {
            case TRAIL_ENABLED_PER_PLAYER:
                currentVal = getPlayerToggle(playerUUID,type);
                trailEnabledPerPlayer.put(playerUUID, !currentVal);
                setPlayerToggleSQL(playerUUID, type);
                break;
            case ROADBOOST_PER_PLAYER:
                currentVal = getPlayerToggle(playerUUID,type);
                roadboostPerPlayer.put(playerUUID, !currentVal);
                setPlayerToggleSQL(playerUUID, type);
                break;
            case MUTE_PER_PLAYER:
                currentVal = getPlayerToggle(playerUUID,type);
                mutePerPlayer.put(playerUUID, !currentVal);
                setPlayerToggleSQL(playerUUID, type);
                break;
            default:
                break;
        }
    }

    /**
     * SQL helper method for setPlayerToggleSQL - sets the given toggle to the sql backend.
     *
     * @param playerUUID Player UUID we are updating.
     * @param toggle     Toggle item we are setting.
     */
    private void setPlayerToggleSQL(UUID playerUUID, BL_PLAYER_ENUM toggle) {
        String query;

        try {
            rs = sql.executeQuery(
                    "SELECT * FROM `" + toggle.getTable() + "` WHERE `UUID` = '" + playerUUID.toString()
                            + "'");

            if (rs.next()) {
                query = "UPDATE `" + toggle.getTable() + "` SET `" + toggle.getColumn() + "` = '"
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

    /**
     * Sets the given type to the string specified for the player with playerUUID.
     *
     * @param playerUUID  Player UUID we are updating.
     * @param type        String item we are updating.
     * @param stringToSet String we are setting the row of type to.
     */
    public void setPlayerString(UUID playerUUID, BL_PLAYER_ENUM type, String stringToSet) {
        switch (type) {
            case TRAIL_PER_PLAYER:
                trailPerPlayer.put(playerUUID, stringToSet);
                setPlayerStringSQL(playerUUID, type, stringToSet);
                break;
            default:
                break;
        }
    }

    /**
     * SQL helper method for setPlayerString - sets the given string to the row corresponding to
     * type.
     *
     * @param playerUUID Player UUID we are modifying.
     * @param type       The Enum type
     * @param newString  String we are setting the row of type to.
     */
    private void setPlayerStringSQL(UUID playerUUID, BL_PLAYER_ENUM type, String newString) {
        String query;

        try {
            rs = sql.executeQuery(
                    "SELECT * FROM `" + type.getTable() + "` WHERE `UUID` = '" + playerUUID.toString()
                            + "'");

            if (rs.next()) {
                query = "UPDATE `" + type.getTable() + "` SET `" + type.getColumn() + "` = '"
                        + newString + "' WHERE `UUID` = '" + playerUUID.toString() + "'";

                Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
                sql.executeUpdate(query);
            } else {
                insertNewPlayer(playerUUID);
                setPlayerStringSQL(playerUUID, type, newString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new player into BL_PLAYER
     *
     * @param playerUUID
     */
    private void insertNewPlayer(UUID playerUUID) {
        String query =
                "INSERT INTO `" + BL_PLAYER_ENUM.HOME_PER_PLAYER.getTable() + "` (`UUID`) VALUES ('"
                        + playerUUID.toString() + "')";

        Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
        sql.executeUpdate(query);
    }
}
