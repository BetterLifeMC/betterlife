package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.main.BetterLife;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import me.gt3ch1.betterlife.configuration.ConfigurationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class BlCommand implements BetterLifeCommand {

    private final BetterLife m;
    private final ConfigurationManager configurationManager;

    @Inject
    public BlCommand(BetterLife m, ConfigurationManager configurationManager) {
        this.m = m;
        this.configurationManager = configurationManager;
    }

    /**
     * Sends the current version of the plugin to the sender.
     *
     * @param sender Who sent the command.
     */
    private void sendVersion(CommandSender sender) {
        sendMessage(sender, "&7Version &6" + m.getDescription().getVersion() + " &7of BetterLife installed.", true);
    }

    /**
     * Reloads the configuration and sends messages to the sender.
     *
     * @param sender Who sent the command.
     */
    private void reloadConfig(CommandSender sender) {
        sendMessage(sender, "&eConfiguration file reloading...", true);
        configurationManager.reloadConfiguration();
        sendMessage(sender, "&aConfiguration file reloaded!", true);
    }

    @Override
    public String getPermission() {
        return "betterlife.bl";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "The primary command for BetterLife.";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        switch (args.length) {
            case 0 -> sendVersion(sender);
            case 1 -> {
                switch (args[0]) {
                    case "reload" -> {
                        if (sender instanceof Player p) {
                            if (p.hasPermission("betterlife.reload")) {
                                reloadConfig(p);
                            } else {
                                sendPermissionErrorMessage(p);
                            }
                        } else {
                            reloadConfig(sender);
                        }
                    }
                    case "version" -> sendVersion(sender);
                }
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        final List<String> subCommands = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("betterlife.reload")) {
                subCommands.add("reload");
            }
            subCommands.add("version");
        }
        return subCommands;
    }
}
