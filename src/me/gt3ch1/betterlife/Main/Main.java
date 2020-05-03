package me.gt3ch1.betterlife.Main;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.commandhelpers.HelpHelper;
import me.gt3ch1.betterlife.commandhelpers.TabCompleterHelper;
import me.gt3ch1.betterlife.eventhelpers.PlayerAccessHelper;
import me.gt3ch1.betterlife.sql.Sql;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for BetterLife. It enables all of the listeners, economy, and tab completion.
 *
 * @author gt3ch1
 * @authoer Starmism
 */
public class Main extends JavaPlugin {

    public static Main m;
    public static Economy economy;
    public static boolean isUsingSql;
    public static Sql sql;
    public static List<String> enabledParticles;
    protected ArrayList<Listener> listeners = new ArrayList<>();

    /**
     * Logs to the bukkit console.
     *
     * @param log
     */
    public static void doBukkitLog(String log) {
        Bukkit.getLogger().info(ChatColor.RED + "[" + ChatColor.DARK_AQUA + "BetterLife" + ChatColor.RED + "] " + ChatColor.BLUE + log);
    }

    public static Economy getEconomy() {
        return economy;
    }

    /**
     * Prep the plugin for startup
     */
    @Override
    public void onEnable() {
        m = this;
        CommandUtils.enableMainConfiguration();

        isUsingSql = CommandUtils.ch.getCustomConfig().getBoolean("sql.enabled");
        if (isUsingSql) {
            String username = CommandUtils.ch.getCustomConfig().getString("sql.username");
            String password = CommandUtils.ch.getCustomConfig().getString("sql.password");
            String database = CommandUtils.ch.getCustomConfig().getString("sql.database");
            String server = CommandUtils.ch.getCustomConfig().getString("sql.server");
            sql = new Sql(database, username, password, server);
        }

        CommandUtils.enableConfiguration();
        new ListenersSetup(m);

        for (String command : CommandUtils.getEnabledTabCommands())
            getCommand(command).setTabCompleter(new TabCompleterHelper());

        HelpHelper.setupAllHelpHashes();
        setupEconomy();
        enabledParticles = CommandUtils.partch.getRow("particle");

        for (Player p : this.getServer().getOnlinePlayers())
            PlayerAccessHelper.setupPlayerConfig(p.getUniqueId());

        doBukkitLog(ChatColor.DARK_GREEN + "Enabled!");

    }

    /**
     * Prep the plugin for shutdown.
     */
    @Override
    public void onDisable() {
        CommandUtils.disableConfiguration();

        for (Listener l : listeners)
            l = null;

        doBukkitLog(ChatColor.DARK_PURPLE + "Goodbye!");
        PlayerAccessHelper.clearPlayerConfigs();
        sql = null;
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
     * Set's up the economy from vault.
     *
     * @return
     */
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            doBukkitLog("Using economy provider: " + economy.toString());
        }

        return (economy != null);
    }
}
