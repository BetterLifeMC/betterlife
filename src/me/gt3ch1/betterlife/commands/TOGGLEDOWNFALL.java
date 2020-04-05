package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommands;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TOGGLEDOWNFALL extends BetterLifeCommands implements CommandExecutor {

	/**
	 * Runs the command /toggledownfall
	 * @param permission
	 * @param cs
	 * @param c
	 * @param label
	 * @param args
	 */
	public TOGGLEDOWNFALL(String permission, CommandSender cs, Command c, String label, String[] args) {
		super(permission, cs, c, label, args);
		this.onCommand(cs, c, label, args);
	}

	/**
	 * Toggles the rain
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		// Test the length of args...
		switch (args.length) {
			// If it is 0
			case 0:
				// And a player
				if (sender instanceof Player) {
					// Change the rain in their world
					Player player = (Player) sender;
					World world = player.getWorld();
					toggleWeather(world, player);
				// If it isn't.
				} else {
					// toggle it in the default world.
					World world = Bukkit.getServer().getWorlds().get(0);
					toggleWeather(world, sender);
				}
				break;
			// If it is 1
			case 1:
				try {
					// See if the parameter is a world
					World world = Bukkit.getServer().getWorld(args[0]);
					// If it is, and isn't Nether or End, change the rain
					if (world.getEnvironment() != Environment.NETHER && world.getEnvironment() != Environment.THE_END) {
						toggleWeather(world, sender);
					} else {
					// If it is Nether or The End, you can't make it rain.
						sendBannerMessage(sender, "&4Can't change weather in that world!");
					}
					break;
				// If you somehow didn't pass a world in, you screwed up.
				} catch (NullPointerException ex) {
					sendBannerMessage(sender, "&4That world doesn't exist!");
					break;
				}
			// Can't fat-finger this one.
			default:
				sendBannerMessage(sender, "&4Too many arguments!");
				return false;
			}
		return true;
	}

	/**
	 * Toggles the weather in @world
	 * @param world
	 * @param sender
	 */
	private void toggleWeather(World world, CommandSender sender) {
		if (world.hasStorm() || world.isThundering()) {
			world.setStorm(false);
			world.setThundering(false);
			sendBannerMessage(sender, "&7Setting weather to &6clear &7in world &6" + world.getName() + "&7!");
		} else {
			world.setStorm(true);
			world.setThundering(true);
			sendBannerMessage(sender, "&7Setting weather to &6stormy &7in world &6" + world.getName() + "&7!");
		}
	}
}
