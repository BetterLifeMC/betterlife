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
		switch (args.length) {
			case 0:
				if (sender instanceof Player) {
					Player player = (Player) sender;
					World world = player.getWorld();
					toggleWeather(world, player);
				} else {
					World world = Bukkit.getServer().getWorlds().get(0);
					toggleWeather(world, sender);
				}
				break;
			case 1:
				try {
					World world = Bukkit.getServer().getWorld(args[0]);

					if (world.getEnvironment() != Environment.NETHER && world.getEnvironment() != Environment.THE_END)
						toggleWeather(world, sender);
					else
						sendBannerMessage(sender, "&4Can't change weather in that world!");
					break;

				} catch (NullPointerException ex) {
					sendBannerMessage(sender, "&4That world doesn't exist!");
					break;
				}

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
