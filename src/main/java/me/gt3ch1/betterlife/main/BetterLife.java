package me.gt3ch1.betterlife.main;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.gt3ch1.betterlife.commandhelpers.*;
import me.gt3ch1.betterlife.configuration.ConfigurationManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for BetterLife. Starts up Guice and sets up the economy.
 *
 * @author gt3ch1
 * @author Starmism
 */
public class BetterLife extends JavaPlugin {

    @Inject
    private CommandRegistrar commandRegistrar;
    @Inject
    private ConfigurationManager configurationManager;
    @Inject
    private ListenerManager listenerManager;

    /**
     * Logs to the bukkit console.
     * @param log The string to log
     */
    public static void doBukkitLog(String log) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + ChatColor.DARK_AQUA + "BetterLife" + ChatColor.RED + "] "
            + ChatColor.BLUE + log);
    }

    /**
     * Prep the plugin for startup
     */
    @Override
    public void onEnable() {
        Binder binder = new Binder(this, fetchEconomy());
        Injector injector = binder.createInjector();
        injector.injectMembers(this);

        commandRegistrar.register();

        if (!configurationManager.enableConfiguration(this)) {
            doBukkitLog(ChatColor.DARK_RED + "Configuration failed to load! Shutting down...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        listenerManager.setupListeners(this);

        doBukkitLog(ChatColor.DARK_GREEN + "Enabled!");
    }

    /**
     * Prep the plugin for shutdown.
     */
    @Override
    public void onDisable() {
        ListenerManager.disableListeners(this);

        doBukkitLog(ChatColor.DARK_PURPLE + "Goodbye!");
    }

    /**
     * Sets up the economy from vault.
     */
    public Economy fetchEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            doBukkitLog("Vault not found. Please install Vault to use this plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return null;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            doBukkitLog("Vault failed to register.");
            getServer().getPluginManager().disablePlugin(this);
            return null;
        }

        doBukkitLog("Using economy provider: " + rsp.getProvider().getName());
        return rsp.getProvider();
    }
}
