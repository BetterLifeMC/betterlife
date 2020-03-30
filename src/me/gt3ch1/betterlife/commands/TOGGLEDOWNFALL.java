package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TOGGLEDOWNFALL extends BetterLifeCommands implements CommandExecutor {
	
	public TOGGLEDOWNFALL(Main m, String permission, CommandSender cs, Command c, String label, String[] args) {
		super(m, permission, cs, c, label, args);
		this.onCommand(cs, c, label, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		switch(args.length) {
			case 0:
				if (sender instanceof Player) {
					Player player = (Player) sender;
					World world = player.getWorld();
					toggleWeather(world, player);
					break;
				} else {
					World world = Bukkit.getServer().getWorlds().get(0);
					toggleWeather(world, sender);
					break;
				}
			case 1:
				try {
					World world = Bukkit.getServer().getWorld(args[0]);
					toggleWeather(world, sender);
				} catch (NullPointerException ex) {
					sendBannerMessage(sender, ChatColor.DARK_RED + "That world doesn't exist!");
				}
				break;
			default:
				return false;
		}
		return true;
	}

	private void toggleWeather(World world, CommandSender sender) {
		if (world.hasStorm() || world.isThundering()) {
			world.setStorm(false);
			world.setThundering(false);
			sendBannerMessage(sender, ChatColor.GRAY + "Setting weather to " + ChatColor.GOLD + "clear" + ChatColor.GRAY + " in world " + ChatColor.GOLD + world.getName() + ChatColor.GRAY + "!");

		} else {
			world.setStorm(true);
			world.setThundering(true);
			sendBannerMessage(sender, ChatColor.GRAY + "Setting weather to " + ChatColor.GOLD + "storm" + ChatColor.GRAY + " in world " + ChatColor.GOLD + world.getName() + ChatColor.GRAY + "!");

		}
	}
}
