package me.gt3ch1.betterlife.sql;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** This class contains methods needed to provided SQL support for the BetterLife plugin.
 * @author gt3ch1
 */
public class Sql {
    private String database, username, password, server;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    /** Initializes support for an SQL database.
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
        doBukkitLog(ChatColor.RED + "Running SQL...");
        connect();
    }

    /** Logs to the bukkit console.
     * @param log
     */
    public void doBukkitLog(String log){
        Bukkit.getLogger().info(log);
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
            doBukkitLog(ChatColor.BLUE + "SQL Succeeded...");

        } catch (SQLException throwables) {

            throwables.printStackTrace();
            doBukkitLog(ChatColor.RED + "SQL Failed!...");

        }
    }

    /** gets the statement
     * @return
     */
    public Statement getStatement() {
        return stmt;
    }

    /**
     * Executes a query, returns the results of said query.
     * @param query
     * @return
     */
    public ResultSet executeQuery(String query) {
        try {
            doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            return getStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Executes the update query provided.
     * @param query
     * @return -1 on failure
     */
    public int executeUpdate(String query) {
        try {
            doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            return getStatement().executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the value of row from playerUUID
     * @param row
     * @param playerUUID
     * @return
     */
    public Object getValue(String row, String playerUUID) {

        row = row.replace(".", "_");
        playerUUID = playerUUID.replace("-", "_");
        String query = "SELECT `" + row + "` FROM `betterlife_players` WHERE `uuid` = '" + playerUUID + "'";

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
     * @param row
     * @return
     */
    public List<String> getRows(String row) {

        List<String> playerUUIDs = new ArrayList<>();
        try {
            rs = executeQuery("SELECT `" + row + "` FROM `betterlife_players");
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
     * @param row
     * @param value
     * @param playerUUID
     * @throws SQLException
     */
    public void setValue(String row, Object value, String playerUUID) throws SQLException {

        row = row.replace(".", "_");
        playerUUID = playerUUID.replace("-", "_");
        rs = executeQuery("SELECT * FROM `betterlife_players` WHERE `uuid`= '" + playerUUID + "'");
        String statement = "";

        if (rs.next()) {
            String sql = "UPDATE `Minecraft`.`betterlife_players` SET `" + row + "` = '"
                    + value + "' WHERE `uuid` = '" + playerUUID + "'";
            doBukkitLog(ChatColor.LIGHT_PURPLE + sql);

            getStatement().executeUpdate(sql);

        } else {

            statement = "INSERT INTO `Minecraft`.`betterlife_players` (`uuid`) VALUES ('"
                    + playerUUID + "')";
            executeUpdate(statement);
            setValue(row, value, playerUUID);

        }

    }

}
