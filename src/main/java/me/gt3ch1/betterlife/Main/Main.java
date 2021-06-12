package me.gt3ch1.betterlife.Main;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.commandhelpers.HelpHelper;
import me.gt3ch1.betterlife.commandhelpers.TabCompleterHelper;
import me.gt3ch1.betterlife.data.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Main class for BetterLife. It enables all of the listeners, economy, and tab completion.
 *
 * @author gt3ch1
 * @author Starmism
 */
public class Main extends JavaPlugin {

    public static Main m;
    public static Economy economy;
    public static Sql sql;
    protected ArrayList<Listener> listeners = new ArrayList<>();
    public static BL_PLAYER bl_player;
    public static BL_HOME bl_home;
    public static BL_ZONE bl_zone;
    public static BL_ZONE_MEMBER bl_zone_member;
    public static BL_WARP bl_warp;
    public static boolean isTesting = false;

    public Main() {
        super();
    }

    protected Main(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        isTesting = true;
        setSql(System.getenv("BL_SQL_HOST"), System.getenv("BL_SQL_DB"),
            System.getenv("BL_SQL_USER"), System.getenv("BL_SQL_PASS"), Integer.parseInt(System.getenv("BL_SQL_PORT")));
        onEnable();
    }

    public boolean isTesting() {
        return isTesting;
    }

    /**
     * Get's the BL_PLAYER.
     *
     * @return BL_PLAYER
     */
    public BL_PLAYER getBlPlayer() {
        return bl_player;
    }

    /**
     * Get's the BL_HOME
     *
     * @return bl_home
     */
    public BL_HOME getBlHomes() {
        return bl_home;
    }

    /**
     * Get's the BL_WARP
     *
     * @return bl_warp
     */
    public BL_WARP getBlWarps() {
        return bl_warp;
    }

    /**
     * Logs to the bukkit console.
     *
     * @param log
     */
    public static void doBukkitLog(String log) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + ChatColor.DARK_AQUA + "BetterLife" + ChatColor.RED + "] "
            + ChatColor.BLUE + log);
    }

    public void setSql(String host, String database, String username, String password, int port) {
        sql = new Sql(host, database, username, password, port);
    }

    /**
     * Prep the plugin for startup
     */
    @Override
    public void onEnable() {
        m = this;
        if (!CommandUtils.enableConfiguration() && !isTesting()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        String host = CommandUtils.ch.getCustomConfig().getString("sql.host");
        String database = CommandUtils.ch.getCustomConfig().getString("sql.database");
        String username = CommandUtils.ch.getCustomConfig().getString("sql.username");
        String password = CommandUtils.ch.getCustomConfig().getString("sql.password");
        int port = CommandUtils.ch.getCustomConfig().getInt("sql.port");
        if (sql == null) {
            sql = new Sql(host, database, username, password, port);
        }

        bl_home = new BL_HOME();
        bl_player = new BL_PLAYER();
        bl_zone = new BL_ZONE();
        bl_zone_member = new BL_ZONE_MEMBER();
        bl_warp = new BL_WARP();

        ListenersSetup.setupListeners(this);
        if (!isTesting()) {
            for (String command : CommandUtils.getEnabledTabCommands()) {
                getCommand(command).setTabCompleter(new TabCompleterHelper());
            }
        }

        HelpHelper.setupAllHelpHashes();
        setupEconomy();

        doBukkitLog(ChatColor.DARK_GREEN + "Enabled!");
    }

    /**
     * Prep the plugin for shutdown.
     */
    @Override
    public void onDisable() {
        CommandUtils.disableConfiguration();

        ListenersSetup.disableListeners(this);

        doBukkitLog(ChatColor.DARK_PURPLE + "Goodbye!");
        sql = null;
        bl_home = null;
        bl_player = null;
        bl_zone = null;
        bl_zone_member = null;
    }

    /**
     * Runs a command designated for the plugin
     */
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        try {
            /*
             * This will set the command executor for the command passed in.
             * Once it is set, this does nothing.  The commands class file
             * is located in package me.gt3ch1.betterlife.commands.LABEL
             * where label is the name of the command in caps.
             */
            this.getCommand(label).setExecutor((CommandExecutor) Class
                .forName("me.gt3ch1.betterlife.commands." + label.toUpperCase())
                .getConstructor(String.class, CommandSender.class, Command.class, String.class, String[].class)
                .newInstance(label.toLowerCase(), cs, cmd, label, args));
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Sets up the economy from vault.
     */
    private void setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            doBukkitLog("Vault not found. No economy provider enabled.");
            return;
        }

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider == null) {
            doBukkitLog("Vault failed to register.");
            return;
        }
        economy = economyProvider.getProvider();
        doBukkitLog("Using economy provider: " + economy.toString());
    }

    /**
     * Sets up and caches all of the currently online players.
     */
    public static void setupOnlinePlayers() {
        doBukkitLog("Setting up all online (" + Bukkit.getOnlinePlayers().size() + ") players...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            setupPlayerConfig(p.getUniqueId());
        }
        doBukkitLog("Done.");
    }

    /**
     * Sets up the cache for the given player uuid.
     *
     * @param playerUUID Player's UUID to cache.
     */
    public static void setupPlayerConfig(UUID playerUUID) {
        bl_player.trailEnabledPerPlayer.put(playerUUID, bl_player.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER));
        bl_player.trailPerPlayer.put(playerUUID, bl_player.getPlayerString(playerUUID, BL_PLAYER_ENUM.TRAIL_PER_PLAYER));
        bl_player.roadboostPerPlayer.put(playerUUID, bl_player.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER));
        bl_player.mutePerPlayer.put(playerUUID, bl_player.getPlayerToggle(playerUUID, BL_PLAYER_ENUM.MUTE_PER_PLAYER));
    }

    /**
     * Get's the plugin's sql connection.
     *
     * @return Current sql connection.
     */
    public Sql getSql() {
        return sql;
    }

}
