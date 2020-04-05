package me.gt3ch1.betterlife.commandhelpers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author gcpease
 * This class is what all commands will extend.
 *
 */
/**
 * @author gcpease
 *
 */
public abstract class BetterLifeCommands extends CommandUtils {
	private String permission;
	private CommandSender cs;
	private Command c;
	private String label;
	private String[] args;
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
	 * This needs to be implemented if the a class extends this one.  It is 
	 * the boolean needed for CommandExecutor.
	 * @param sender
	 * @param c
	 * @param command
	 * @param args
	 * @return
	 */
	public abstract boolean onCommand(CommandSender sender, Command c, String command, String[] args);
}
