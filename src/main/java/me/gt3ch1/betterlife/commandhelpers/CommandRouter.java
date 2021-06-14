package me.gt3ch1.betterlife.commandhelpers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandRouter implements CommandExecutor, TabCompleter {

    private final Map<String, BetterLifeCommand> map;

    public CommandRouter(Map<String, BetterLifeCommand> map) {
        this.map = map;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        BetterLifeCommand cmd = map.get(command.getName());
        if (cmd == null) {
            CommandUtils.sendMessage(commandSender, "Command not found.", true);
            return true;
        }

        if (commandSender.hasPermission(cmd.getPermission())) {
            return cmd.onCommand(commandSender, args);
        } else {
            CommandUtils.sendPermissionErrorMessage(commandSender);
            return true;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        BetterLifeCommand cmd = map.get(command.getName());
        if (cmd == null) {
            return Collections.emptyList();
        }

        return cmd.tabComplete(commandSender, args);
    }
}
