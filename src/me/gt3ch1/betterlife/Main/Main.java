package me.gt3ch1.betterlife.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.gt3ch1.betterlife.commands.CommandTemplate;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		new CommandTemplate(this);
	}

	@Override
	public void onDisable() {

	}

}
