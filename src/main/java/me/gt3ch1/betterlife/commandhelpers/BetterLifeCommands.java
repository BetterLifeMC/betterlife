package me.gt3ch1.betterlife.commandhelpers;

import me.gt3ch1.betterlife.Main.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashMap;

/**
 * @author gt3ch1
 * This class is what all commands will extend.  Each command that extends this class must provide all of
 * the parameters required for the initalizer.  All commands must also call this.onCommand to run the
 * method.
 */
public abstract class BetterLifeCommands extends CommandUtils {
    private String permission;
    public CommandSender cs;
    private Command c;
    private String label;
    private String[] args;
    protected Economy economy = Main.economy;
    public LinkedHashMap<String, String> helpHash;

    /**
     * Creates a new BetterLife command.
     *
     * @param permission Permission needed to run the command.
     * @param cs         Person who sent the command.
     * @param c          The command.
     * @param label      The string version of command.
     * @param args       the arguments to the command.
     */
    public BetterLifeCommands(String permission, CommandSender cs, Command c, String label, String[] args) {
        this.permission = permission;
        this.cs = cs;
        this.label = label;
        this.args = args;
        helpHash = HelpHelper.getAHelpHash(this.label.toLowerCase());
    }

    /**
     * Returns the current CommandSender
     *
     * @return CommandSender
     */
    public CommandSender getCs() {
        return cs;
    }

    /**
     * Returns the Command
     *
     * @return Command
     */
    public Command getC() {
        return c;
    }

    /**
     * Returns the Command Label
     *
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the arguments
     *
     * @return args
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Returns the permission
     *
     * @return
     */
    public String getPermission() {
        return "betterlife." + permission;
    }

    /**
     * This needs to be implemented if the a class extends BetterLifeCommands.  It is
     * the boolean needed for CommandExecutor.
     *
     * @param sender  Sender of the command.
     * @param c       The command itself.
     * @param command The string version of the command.
     * @param args    The arguments of the command.
     * @return
     */
    public abstract boolean onCommand(CommandSender sender, Command c, String command, String[] args);
}
