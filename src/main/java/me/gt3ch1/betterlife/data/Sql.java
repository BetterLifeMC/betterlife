package me.gt3ch1.betterlife.data;

import static me.gt3ch1.betterlife.Main.Main.m;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.ChatColor;

import java.sql.*;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This class contains methods needed to provided SQL support for the BetterLife plugin.
 *
 * @author gt3ch1
 */
public class Sql {

    private final String dbType, host, database, username, password;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    /**
     * Initializes support for an SQL database.
     *
     * @param host     Host IP Address of the server
     * @param database The database name
     * @param username The login username
     * @param password The login password
     */
    public Sql(String dbType, String host, String database, String username, String password) {
        this.dbType = dbType;
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;

        Main.doBukkitLog(ChatColor.YELLOW + "Connecting to SQL database...");
        connectAndSetup();
    }


    /**
     * Connects to the SQL database.
     */
    private void connectAndSetup() {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {

                    Class.forName("com.mysql.jdbc.Driver");
                    Class.forName("org.mariadb.jdbc.Driver");
                    con = DriverManager
                        .getConnection("jdbc:" + dbType + "://" + host + ":3306/" + database,
                            username, password);
                    stmt = con.createStatement();
                    Main.doBukkitLog(ChatColor.GREEN + "SQL Connected!");
                    setup();
                } catch (ClassNotFoundException | SQLException e) {
                    Main.doBukkitLog(e.toString());
                    Main.doBukkitLog(ChatColor.RED + "SQL Failed!");
                }
            }
        };

        runnable.runTaskAsynchronously(m);
    }

    public void setup() {
        String query;

        query = "SELECT `UUID` FROM `BL_PLAYER` LIMIT 1";

        try {
            stmt.executeQuery(query);
        } catch (SQLException e) {
            query = "CREATE TABLE IF NOT EXISTS `BL_PLAYER` ("
                + "`UUID` VARCHAR(36) PRIMARY KEY,"
                + "`TrailToggle` BOOL DEFAULT false,"
                + "`Trail` NVARCHAR(30),"
                + "`RoadBoostToggle` BOOL DEFAULT false"
                + ")";
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + "Creating Player table.");
            executeUpdate(query);
        }

        query = "SELECT `UUID` FROM `BL_HOME` LIMIT 1";

        try {
            stmt.executeQuery(query);
        } catch (SQLException e) {
            query = "CREATE TABLE IF NOT EXISTS `BL_HOME` ("
                + "`UUID` VARCHAR(36),"
                + "`Home` NVARCHAR(30),"
                + "`X` DOUBLE,"
                + "`Y` DOUBLE,"
                + "`Z` DOUBLE,"
                + "`World` NVARCHAR(30),"
                + "`Yaw` FLOAT,"
                + "`Pitch` FLOAT,"
                + "PRIMARY KEY (UUID,Home),"
                + "FOREIGN KEY (UUID) REFERENCES BL_PLAYER (UUID)"
                + ")";
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + "Creating Home table.");
            executeUpdate(query);
        }

        query = "SELECT `ZoneID` FROM `BL_ZONE` LIMIT 1";

        try {
            stmt.executeQuery(query);
        } catch (SQLException e) {
            query = "CREATE TABLE IF NOT EXISTS `BL_ZONE` ("
                + "`ZoneID` INT PRIMARY KEY AUTO_INCREMENT,"
                + "`AX` DOUBLE,"
                + "`AY` DOUBLE,"
                + "`AZ` DOUBLE,"
                + "`BX` DOUBLE,"
                + "`BY` DOUBLE,"
                + "`BZ` DOUBLE,"
                + "`World` NVARCHAR(30),"
                + "`OwnerUUID` VARCHAR(36),"
                + "FOREIGN KEY (OwnerUUID) REFERENCES BL_PLAYER (UUID)"
                + ")";
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + "Creating Zone table.");
            executeUpdate(query);
        }

        query = "SELECT `ZoneID` FROM `BL_ZONE_MEMBER` LIMIT 1";

        try {
            stmt.executeQuery(query);
        } catch (SQLException e) {
            query = "CREATE TABLE IF NOT EXISTS `BL_ZONE_MEMBER` ("
                + "`ZoneID` INT,"
                + "`MemberUUID` VARCHAR(36),"
                + "PRIMARY KEY (ZoneID, MemberUUID),"
                + "FOREIGN KEY (MemberUUID) REFERENCES BL_PLAYER (UUID)"
                + ")";
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + "Creating Zone Members table.");
            executeUpdate(query);
        }
    }

    /**
     * Executes a query, returns the results of said query.
     *
     * @param query The query to execute
     * @return Results of the query
     */
    public ResultSet executeQuery(String query) {
        try {
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Executes the update query provided.
     *
     * @param query The update query to execute.
     * @return Success of the update
     */
    public boolean executeUpdate(String query) {
        try {
            Main.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addHome(String query, String home) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setNString(1, home);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    Main.doBukkitLog(e.toString());
                }
            }
        };

        runnable.runTaskAsynchronously(m);
    }
}
