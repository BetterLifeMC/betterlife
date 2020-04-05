package me.gt3ch1.betterlife.commandhelpers;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompleterHelper implements TabCompleter {
	// Initialize a string of variables
	private List<String> subCommands = new ArrayList<>();
	private List<String> newList = new ArrayList<>();
	private Particle[] particles = Particle.values();

	/**
	 * When we can tab complete commands.
	 * NOTE: Must be enabled in Main!
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// Clear the lists.
		newList.clear();
		subCommands.clear();
		// Get the command being sent
		String cmd = command.getLabel();
		// If it is a player, great, we can make it work
		if (sender instanceof Player) {
			// Cast it
			Player player = (Player) sender;
			// Test the command
			switch (cmd) {
			// If it is /trail
			case "trail":
				// Test the lengths of the arguments
				switch (args.length) {
					// If it is one..
					case 1:
						// Add set, list, help, toggle, and if the player
						// has permissions, add and rm to the tab completion.
						if (Arrays.stream(args).anyMatch("set"::contains)) {
							subCommands.add("set");
						}
						if (Arrays.stream(args).anyMatch("list"::contains)) {
							subCommands.add("list");
						}
						if (Arrays.stream(args).anyMatch("help"::contains)) {
							subCommands.add("help");
						}
						if (Arrays.stream(args).anyMatch("toggle"::contains)) {
							subCommands.add("toggle");
						}
						break;
					// If there is TWO!
					case 2:
						// Test the arguments.
						switch (args[0]) {
							// If it is set, return the list of currently enabled particles,
							// and append them to the tablist list.
							case "set":
								subCommands = (List<String>) (CommandUtils.getParticleConfiguration().getParticleConfig().getList("enabledParticles"));
								for (int i = 0; i < subCommands.size(); i++) {
									String particle = subCommands.get(i);
									if (Arrays.stream(args).anyMatch(particle::contains) && player
											.hasPermission("betterlife.trail.particle." + particle.toLowerCase())) {
										newList.add(particle);
									}
								}
								subCommands = newList;
								break;
							default:
								break;
						}
					default:
						break;
				}
				break;
			// If it is toggledownfall
			case "toggledownfall":
				// If the player has permission, and the world is an overworld, show that world.
				for (World w : Bukkit.getServer().getWorlds()) {
					if (w.getEnvironment() != Environment.NETHER 
						&& w.getEnvironment() != Environment.THE_END 
						&& player.hasPermission("betterlife.toggledownfall")) {
						subCommands.add(w.getName());
					}
				}
				break;
			// If it is bl
			case "bl":
				// Test the argument length
				switch (args.length) {
					// If it is 1
					case 1:
						// and looks like /reload and the player has permission, append it.
						if (player.hasPermission("betterlife.reload") || player.hasPermission("betterlife.admin")) {
							if (Arrays.stream(args).anyMatch("reload"::contains)) {
								subCommands.add("reload");
							}
						}
						// and looks like version, append it.
						if (Arrays.stream(args).anyMatch("version"::contains)) {
							subCommands.add("version");
						}
						break;
					default:
						break;
				}
				break;
			default:
				break;
			}
			// return the list of subcommands
			return subCommands;
		}
		return null;

	}
}
