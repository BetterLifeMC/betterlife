package me.gt3ch1.betterlife.commandhelpers;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.configuration.PlayerConfigurationHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashMap;

/**
 * @author gt3ch1
 * This class is what all commands will extend.  Each command that extends this class must provide all of
 * the parameters required for the initalizer.  All commands must also call this.onCommand to run the
 * method.
 *
 */
public abstract class BetterLifeCommands extends CommandUtils {
	private String permission;
	private CommandSender cs;
	private Command c;
	private String label;
	private String[] args;
	protected Economy economy = Main.getEconomy();
	public LinkedHashMap<String, String> helpHash;
	/**
	 * @param permission
	 * @param cs
	 * @param c
	 * @param label
	 * @param args
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
	 * @return CommandSender
	 */
	public CommandSender getCs() {
		return cs;
	}
	/**
	 * Returns the Command
	 * @return Command
	 */
	public Command getC() {
		return c;
	}
	/**
	 * Returns the Command Label 
	 * @return label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Returns the arguments
	 * @return args
	 */
	public String[] getArgs() {
		return args;
	}
	/**
	 * Returns the permission
	 * @return
	 */
	public String getPermission() {
		return "betterlife." + permission;
	}
	/**
	 * This needs to be implemented if the a class extends BetterLifeCommands.  It is
	 * the boolean needed for CommandExecutor.
	 * @param sender
	 * @param c
	 * @param command
	 * @param args
	 * @return
	 */
	public abstract boolean onCommand(CommandSender sender, Command c, String command, String[] args);
}
