package me.gt3ch1.betterlife.Main;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.commandhelpers.HelpHelper;
import me.gt3ch1.betterlife.commandhelpers.TabCompleterHelper;
import me.gt3ch1.betterlife.data.BL_HOME;
import me.gt3ch1.betterlife.data.BL_PLAYER;
import me.gt3ch1.betterlife.data.BL_ZONE;
import me.gt3ch1.betterlife.data.BL_ZONE_MEMBER;
import me.gt3ch1.betterlife.data.Sql;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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

    /**
     * Logs to the bukkit console.
     *
     * @param log
     */
    public static void doBukkitLog(String log) {
        Bukkit.getLogger().info(ChatColor.RED + "[" + ChatColor.DARK_AQUA + "BetterLife" + ChatColor.RED + "] " + ChatColor.BLUE + log);
    }

    /**
     * Prep the plugin for startup
     */
    @Override
    public void onEnable() {
        m = this;
        CommandUtils.enableConfiguration();

        String dbType = CommandUtils.ch.getCustomConfig().getString("sql.dbType");
        String host = CommandUtils.ch.getCustomConfig().getString("sql.host");
        String database = CommandUtils.ch.getCustomConfig().getString("sql.database");
        String username = CommandUtils.ch.getCustomConfig().getString("sql.username");
        String password = CommandUtils.ch.getCustomConfig().getString("sql.password");

        sql = new Sql(dbType, host, database, username, password);

        new ListenersSetup(m);

        for (String command : CommandUtils.getEnabledTabCommands())
            getCommand(command).setTabCompleter(new TabCompleterHelper());

        HelpHelper.setupAllHelpHashes();
        setupEconomy();

        bl_home = new BL_HOME();
        bl_player = new BL_PLAYER();
        bl_zone = new BL_ZONE();
        bl_zone_member = new BL_ZONE_MEMBER();

        doBukkitLog(ChatColor.DARK_GREEN + "Enabled!");
    }

    /**
     * Prep the plugin for shutdown.
     */
    @Override
    public void onDisable() {
        CommandUtils.disableConfiguration();

        HandlerList.unregisterAll(m);

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
}
