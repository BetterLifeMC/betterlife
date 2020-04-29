package me.gt3ch1.betterlife.sql;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.ChatColor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods needed to provided SQL support for the BetterLife plugin.
 *
 * @author gt3ch1
 */
public class Sql {
    private String database, username, password, server;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    /**
     * Initializes support for an SQL database.
     *
     * @param database
     * @param username
     * @param password
     * @param server
     */
    public Sql(String database, String username, String password, String server) {
        this.database = database;
        this.username = username;
        this.password = password;
        this.server = server;
        Main.doBukkitLog(ChatColor.YELLOW + "Connecting to SQL database...");
        connect();
        setup();
    }


    /**
     * Connects to the SQL database.
     */
    private void connect() {
        try {

            con = DriverManager.getConnection("jdbc:mysql://" + server + ":3306/" + database,
                    username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM betterlife_players");
            Main.doBukkitLog(ChatColor.GREEN + "SQL Connected!");

        } catch (SQLException throwables) {

            throwables.printStackTrace();
            Main.doBukkitLog(ChatColor.RED + "SQL Failed!...");

        }
    }

    public void setup() {
        Main.doBukkitLog(ChatColor.LIGHT_PURPLE + "Setting up databases...");
        String query;
        query = "SELECT `uuid` FROM `betterlife_players` LIMIT 1";

        try {
            getStatement().executeQuery(query);
        } catch (SQLException throwables) {
            query = "CREATE TABLE `betterlife_players` (" +
                    "  `uuid` text COLLATE utf8mb4_unicode_ci NOT NULL," +
                    "  `trails_enabled` text COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'false'," +
                    "  `trails_trail` text COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
                    "  `roadboost` text COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'false'," +
                    "  `antigrief_location_a` text COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
                    "  `antigrief_location_b` text COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
                    "  `antigrief_enabled` text COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'false'," +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT" +
                    ")";
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + " Creating Sql database for players");
            executeUpdate(query);
        }

        query = "SELECT `uuid` FROM `betterlife_homes` LIMIT 1";

        try {
            getStatement().executeQuery(query);
        } catch (SQLException throwables) {
            query = "CREATE TABLE `betterlife_homes` (" +
                    "  `uuid` text COLLATE utf8mb4_unicode_ci NOT NULL," +
                    "  `home` text COLLATE utf8mb4_unicode_ci DEFAULT NULL," +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT" +
                    ")";
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + " Creating Sql database for homes");
            executeUpdate(query);
        }
    }

    /**
     * gets the statement
     *
     * @return
     */
    public Statement getStatement() {
        return stmt;
    }

    /**
     * Executes a query, returns the results of said query.
     *
     * @param query
     * @return
     */
    public ResultSet executeQuery(String query) {
        try {
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            return getStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Executes the update query provided.
     *
     * @param query
     * @return -1 on failure
     */
    public int executeUpdate(String query) {
        try {
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            return getStatement().executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the value of row from playerUUID
     *
     * @param row
     * @param playerUUID
     * @param table
     * @return
     */
    public Object getValue(String row, String playerUUID, String table) {

        row = row.replace(".", "_");
        playerUUID = playerUUID.replace("-", "_");
        String query = "SELECT `" + row + "` FROM `betterlife_" + table + "` WHERE `uuid` = '" + playerUUID + "'";

        try {
            rs = executeQuery(query);
            while (rs.next())
                return rs.getObject(row);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the list of player UUIDs from the row.
     *
     * @param row
     * @param table
     * @return
     */
    public List<String> getRows(String row, String table) {

        List<String> playerUUIDs = new ArrayList<>();
        try {
            rs = executeQuery("SELECT `" + row + "` FROM `betterlife_" + table + "`");
            while (rs.next()) {
                playerUUIDs.add(rs.getString(row).replace("_", "-"));
            }
            return playerUUIDs;
        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }
        return null;
    }

    /**
     * Sets the value of the given row based on the playerUUID.
     *
     * @param row
     * @param value
     * @param playerUUID
     * @param table
     * @throws SQLException
     */
    public void setValue(String row, Object value, String playerUUID, String table) throws SQLException {

        row = row.replace(".", "_");
        playerUUID = playerUUID.replace("-", "_");
        rs = executeQuery("SELECT * FROM `betterlife_" + table + "` WHERE `uuid`= '" + playerUUID + "'");
        String statement = "";

        if (rs.next()) {
            String sql = "UPDATE `Minecraft`.`betterlife_" + table + "` SET `" + row + "` = '"
                    + value + "' WHERE `uuid` = '" + playerUUID + "'";
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + sql);

            getStatement().executeUpdate(sql);

        } else {

            statement = "INSERT INTO `Minecraft`.`betterlife_" + table + "` (`uuid`) VALUES ('"
                    + playerUUID + "')";
            executeUpdate(statement);
            setValue(row, value, playerUUID, table);

        }

    }

}
