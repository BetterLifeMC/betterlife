package me.gt3ch1.betterlife.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.gt3ch1.betterlife.Main.Main;

public abstract class BetterLifeCommands extends CommandUtils {
	private String permission;
	private CommandSender cs;
	private Command c;
	private String label;
	private String[] args;
	private Main m;
	public BetterLifeCommands(Main m, String permission, CommandSender cs, Command c, String label, String[] args) {
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
		return "betterlife.command." + permission;
	}
	public Main getPlugin() {
		return m;
	}
	public abstract boolean onCommand(CommandSender sender, Command c, String command, String[] args);
}
