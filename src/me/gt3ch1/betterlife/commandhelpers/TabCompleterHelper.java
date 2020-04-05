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

	@SuppressWarnings("unchecked")
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		newList.clear();
		subCommands.clear();
		String cmd = command.getLabel();
		if (sender instanceof Player) {
			Player player = (Player) sender;
			switch (cmd) {
			case "trail":
				switch (args.length) {
					case 1:
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
					case 2:
						switch (args[0]) {
							case "set":
								subCommands = (List<String>) (CommandUtils.getMainConfiguration().getCustomConfig()
										.getList("trail.enabledParticles"));
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
			case "toggledownfall":
				for (World w : Bukkit.getServer().getWorlds()) {
					if (w.getEnvironment() != Environment.NETHER && w.getEnvironment() != Environment.THE_END) {
						subCommands.add(w.getName());
					}
				}
				break;
			case "bl":
				switch (args.length) {
					case 1:
						if (player.hasPermission("betterlife.reload") || player.hasPermission("betterlife.admin")) {
							if (Arrays.stream(args).anyMatch("reload"::contains)) {
								subCommands.add("reload");
							}
						}
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
			return subCommands;
		}
		return null;

	}
}
