package me.gt3ch1.betterlife.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.gt3ch1.betterlife.commands.CommandTemplate;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		
	}
	@Override
	public void onDisable() {
		
	}
	@Override
	public boolean onCommand(CommandSender cs, Command c, String command, String[] args) {
		CommandTemplate.doCommand(cs, c, command, args);
		return true;
	}
}
