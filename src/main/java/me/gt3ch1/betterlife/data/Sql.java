package me.gt3ch1.betterlife.data;

import me.gt3ch1.betterlife.main.BetterLife;
import me.gt3ch1.betterlife.configuration.MainConfigurationHandler;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.*;

import static me.gt3ch1.betterlife.main.BetterLife.doBukkitLog;
import static me.gt3ch1.betterlife.main.BetterLife.isTesting;

/**
 * This class contains methods needed to provided SQL support for the BetterLife plugin.
 *
 * @author Starmism
 * @author gt3ch1
 */
@Singleton
public class Sql {

    private final String host, database, username, password;
    private final int port;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private final BetterLife m;

    private boolean isSqlConnected = false;

    @Inject
    public Sql(MainConfigurationHandler ch, BetterLife m) {
        this(
                ch.getCustomConfig().getString("sql.host"),
                ch.getCustomConfig().getString("sql.database"),
                ch.getCustomConfig().getString("sql.username"),
                ch.getCustomConfig().getString("sql.password"),
                ch.getCustomConfig().getInt("sql.port"),
                m
        );
    }

    /**
     * Initializes support for an SQL database.
     *
     * @param host     Host IP Address of the server
     * @param database The database name
     * @param username The login username
     * @param password The login password
     */
    public Sql(String host, String database, String username, String password, int port, BetterLife m) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
        this.m = m;

        BetterLife.doBukkitLog(ChatColor.YELLOW + "Connecting to SQL database...");
        if (isTesting) {
            setupTestSql();
        } else {
            connectAndSetup();
        }
    }

    /**
     * Sets up the SQL database in a way we can test it.
     */
    private void setupTestSql() {
        try {
            con = DriverManager
                .getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
                    username, password);
            stmt = con.createStatement();
            isSqlConnected = true;
            setupTables();
            checkIfColumnsExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects to the SQL database.
     */
    private void connectAndSetup() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    con = DriverManager
                        .getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
                            username, password);
                    stmt = con.createStatement();
                    BetterLife.doBukkitLog(ChatColor.GREEN + "SQL Connected!");
                    isSqlConnected = true;
                    setupTables();
                    checkIfColumnsExists();
                } catch (SQLException e) {
                    BetterLife.doBukkitLog(ChatColor.RED + "SQL Failed!");
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(m);
    }

    /**
     * Checks if all columns in BL_PLAYER exist.  This is just to ensure that if the user updates the plugin, that all of the needed sql columns
     * exist.
     *
     * @throws SQLException If for some reason the connection fails.
     */
    private void checkIfColumnsExists() throws SQLException {
        for (BL_PLAYER_ENUM entry : BL_PLAYER_ENUM.values()) {
            DatabaseMetaData meta;
            meta = con.getMetaData();
            BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + "Checking : " + entry.getColumn());
            try {
                rs = meta.getColumns(null, null, entry.getTable(), entry.getColumn());
            } catch (SQLException e) {
                BetterLife.doBukkitLog(ChatColor.DARK_RED + "Get columns failed!");
            }
            if (!rs.next()) {
                String query = "ALTER TABLE " + entry.getTable() + " ADD " + entry.getColumn() + " "
                    + entry.getSqlType() + " DEFAULT " + entry.getDefault();
                stmt.execute(query);
                BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            }

        }
    }

    /**
     * Sets up the SQL tables for BetterLife's use. This will check to see if all the necessary tables exist, and if they don't, it will create them.
     */
    private void setupTables() {
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
            BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + "Creating Player table.");
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
            BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + "Creating Home table.");
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
            BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + "Creating Zone table.");
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
            BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + "Creating Zone Members table.");
            executeUpdate(query);
        }

        query = "SELECT `Name` FROM `BL_WARP` LIMIT 1";

        try {
            stmt.executeQuery(query);
        } catch (SQLException e) {
            query = "CREATE TABLE IF NOT EXISTS `BL_WARP` ("
                + "`Name` NVARCHAR(30),"
                + "`X` DOUBLE,"
                + "`Y` DOUBLE,"
                + "`Z` DOUBLE,"
                + "`World` NVARCHAR(30),"
                + "`Yaw` FLOAT,"
                + "`Pitch` FLOAT,"
                + "PRIMARY KEY (Name)"
                + ")";
            BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + "Creating Player table.");
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
        if (!isSqlConnected) {
            doBukkitLog(ChatColor.RED + "SQL isn't connected.");
            return null;
        }
        try {
            BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
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
        if (!isSqlConnected) {
            doBukkitLog(ChatColor.RED + "SQL isn't connected.");
            return false;
        }
        try {
            BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Modifies the given home.
     *
     * @param query Query to execute.
     * @param home  Name of the home to update.
     */
    public void modifyHome(String query, String home) {
        if (!isSqlConnected) {
            doBukkitLog(ChatColor.RED + "SQL isn't connected.");
            return;
        }
        if (isTesting) {
            modifyHomeHelper(query, home);
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    modifyHomeHelper(query, home);
                }
            }.runTaskAsynchronously(m);
        }
    }

    /**
     * Helper method for modifyHome
     *
     * @param query Query to execute.
     * @param home  Name of the home to update.
     */
    private void modifyHomeHelper(String query, String home) {
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setNString(1, home);
            BetterLife.doBukkitLog(ChatColor.LIGHT_PURPLE + query);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            BetterLife.doBukkitLog(e.toString());
        }
    }

    /**
     * Returns whether or not sql is connected
     *
     * @return True if SQL is connected.
     */
    public boolean isSqlConnected() {
        return isSqlConnected;
    }
}
