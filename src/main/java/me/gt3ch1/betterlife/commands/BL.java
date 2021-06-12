package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.BetterLife;
import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BL extends BetterLifeCommands implements CommandExecutor {

    // Get the plugin from Main
    private final BetterLife m = BetterLife.m;

    /**
     * Handles the /bl command
     *
     * @param permission Permission required for the /bl command.
     * @param cs         The command sender.
     * @param c          The command.
     * @param label      The String version of the command.
     * @param args       The arguments to the command.
     */
    public BL(String permission, CommandSender cs, Command c, String label, String[] args) {
        super(permission, cs, c, label, args);
        this.onCommand(cs, c, label, args);
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
        CommandUtils.reloadConfiguration();
        sendMessage(sender, "&aConfiguration file reloaded!", true);
    }

    /**
     * When /bl is sent
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cs, @NotNull String command, String[] args) {

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
}
