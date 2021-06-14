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
     * @return The list of help strings for the subcommands
     */
    List<String> getHelpList();

    /**
     * @return The command description
     */
    String getDescription();

    /**
     * Called when the command is run by a either a player or the console
     * @param sender Player or console who sent the command
     * @param args The list of arguments ran with the command
     * @return Essentially always true, since we never want to print the usage message from the plugin.yml
     */
    boolean onCommand(CommandSender sender, String[] args);

    /**
     * Called upon every letter that is typed as a command to give information about possible options
     * @param sender Player or console who is typing the command
     * @param args The list of arguments (so far)
     * @return A List containing all the possible options for the next argument to be typed
     */
    List<String> tabComplete(@NotNull CommandSender sender, @NotNull final String[] args);
}
