package me.gt3ch1.betterlife.commandhelpers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class BetterLifeCommands extends CommandUtils {
	private String permission;
	private CommandSender cs;
	private Command c;
	private String label;
	private String[] args;
	public BetterLifeCommands(String permission, CommandSender cs, Command c, String label, String[] args) {
		this.permission = permission;
		this.cs = cs;
		this.label = label;
		this.args = args;
	}

	public CommandSender getCs() {
		return cs;
	}
	public Command getC() {
		return c;
	}
	public String getLabel() {
		return label;
	}
	public String[] getArgs() {
		return args;
	}
	public String getPermission() {
		return "betterlife." + permission;
	}
	
	public abstract boolean onCommand(CommandSender sender, Command c, String command, String[] args);
}
