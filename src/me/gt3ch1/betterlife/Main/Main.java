package me.gt3ch1.betterlife.Main;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.commandhelpers.HelpHelper;
import me.gt3ch1.betterlife.events.*;
import me.gt3ch1.betterlife.commandhelpers.TabCompleterHelper;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JavaPlugin {

    protected ArrayList<Listener> listeners = new ArrayList<>();
    public static Main m;
    public static Economy economy;

    @Override
    public void onEnable() {

        m = this;

        CommandUtils.enableConfiguration();
        new ListenersSetup(m);

        for (String command : CommandUtils.getEnabledTabCommands()) {
            getCommand(command).setTabCompleter(new TabCompleterHelper());
        }

        HelpHelper.setupAllHelpHashes();
        setupEconomy();

    }

    /**
     * Prep the plugin for shutdown.
     */
    @Override
    public void onDisable() {
        // Set the configuration managers to null
        CommandUtils.disableConfiguration();
        // Set all listeners to null
        for (Listener l : listeners) {
            l = null;
        }
        // Log output
        getLogger().info("Goodbye!");
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
     * @return
     */
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            getLogger().info("Using economy provider: " + economy.toString());
        }

        return (economy != null);
    }

    public static Economy getEconomy() {
        return economy;
    }
}
