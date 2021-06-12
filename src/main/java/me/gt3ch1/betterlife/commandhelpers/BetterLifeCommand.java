package me.gt3ch1.betterlife.commandhelpers;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * @author Starmism
 */
public interface BetterLifeCommand {

    /**
     * @return The permission related to this command
     */
    String getPermission();

    /**
     * Gives you the list of help strings for the subcommands.
     */
    List<String> getHelpList();

    String getDescription();

    boolean onCommand(CommandSender sender, String[] args);

    List<String> tabComplete(@NotNull CommandSender sender, @NotNull final String[] args);
}
