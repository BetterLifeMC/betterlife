package me.gt3ch1.betterlife.sql;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sql {
    private String database, username, password, server;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public Sql(String database, String username, String password, String server) {
        this.database = database;
        this.username = username;
        this.password = password;
        this.server = server;
        doBukkitLog(ChatColor.RED + "Running SQL...");
        connect();
    }
    public void doBukkitLog(String log){
        Bukkit.getLogger().info(log);
    }
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

    public Statement getStatement() {
        return stmt;
    }

    public ResultSet executeQuery(String query) {
        try {
            doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            return getStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int executeUpdate(String query) {
        try {
            doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            return getStatement().executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

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
