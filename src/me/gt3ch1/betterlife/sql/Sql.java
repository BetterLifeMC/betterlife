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
        Bukkit.getLogger().info(ChatColor.RED + "Running SQL...");
        connect();
    }

    private void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + server + ":3306/" + database,
                    username, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM betterlife_players");
            Bukkit.getLogger().info(ChatColor.BLUE + "SQL Succeeded...");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Bukkit.getLogger().info(ChatColor.RED + "SQL Failed!...");

        }
    }

    public Statement getStatement() {
        return stmt;
    }

    public void executeQuery(String query) throws SQLException {
        getStatement().executeQuery(query);
    }

    public Object getValue(String row, String playerUUID) {

        row = row.replace(".", "_");
        playerUUID = playerUUID.replace("-", "_");
        try {
            rs = getStatement().executeQuery("SELECT `" + row + "` FROM `betterlife_players` WHERE `uuid` = '" + playerUUID + "'");
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
            rs = getStatement().executeQuery("SELECT `" + row + "` FROM `betterlife_players");
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
        System.out.println(String.format("Row » %s || Player UUID » %s || Value » %s", row, playerUUID, value));
        rs = getStatement().executeQuery("SELECT * FROM `betterlife_players` WHERE `uuid`= '" + playerUUID + "'");
        String statement = "";
        if (rs.next()) {
            String sql = "UPDATE `Minecraft`.`betterlife_players` SET `" + row + "` = '"
                    + value + "' WHERE `uuid` = '" + playerUUID + "'";
            Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE + sql);

            getStatement().executeUpdate(sql);
        } else {
            statement = "INSERT INTO `Minecraft`.`betterlife_players` (`uuid`) VALUES ('"
                    + playerUUID + "')";
            Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE + statement);

            getStatement().executeUpdate(statement);
            setValue(row, value, playerUUID);

        }

    }

}
