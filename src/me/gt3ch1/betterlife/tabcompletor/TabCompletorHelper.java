package me.gt3ch1.betterlife.tabcompletor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;
import net.minecraft.server.v1_15_R1.Particles;

public class TabCompletorHelper implements TabCompleter {
	// Initialize a string of variables
	private List<String> subCommands = new ArrayList<>();
	private Main plugin;
	private List<String> newList = new ArrayList<>();
	private Particle[] particles = Particle.values();

	public TabCompletorHelper(Main m) {
		this.plugin = m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
		newList.clear();
		subCommands.clear();
		String cmd = command.getLabel();
		if (cmd.equalsIgnoreCase("trail") && commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("set")) {
					subCommands = (List<String>) (plugin.getMainConfiguration().getCustomConfig()
							.getList("trail.enabledParticles"));
					for (int i = 0; i < subCommands.size(); i++) {
						String particle = subCommands.get(i);
						if (Arrays.stream(args).anyMatch(particle::contains) && player
								.hasPermission("betterlife.command.trail.particle." + particle.toLowerCase())) {
							newList.add(particle);
						}
					}
					subCommands = newList;

				} else if (args[0].equalsIgnoreCase("rm")) {
					subCommands = (List<String>) (plugin.getMainConfiguration().getCustomConfig()
							.getList("trail.enabledParticles"));
					for (int i = 0; i < subCommands.size(); i++) {
						if (Arrays.stream(args).anyMatch(subCommands.get(i)::contains)) {
							newList.add(subCommands.get(i));
						}
					}
					subCommands = newList;
				} else if (args[0].equalsIgnoreCase("add")) {
					subCommands = (List<String>) (plugin.getMainConfiguration().getCustomConfig()
							.getList("trail.enabledParticles"));
					for (Particle p : particles) {
						if (!subCommands.contains(p.toString())
								&& Arrays.stream(args).anyMatch(p.toString()::contains)) {
							newList.add(p.toString());
						}
					}
					subCommands = newList;
				}
			}
			if (args.length == 1) {
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
				if (player.hasPermission("betterlife.command.trail.admin")) {
					if (Arrays.stream(args).anyMatch("add"::contains)) {
						subCommands.add("add");
					}
					if (Arrays.stream(args).anyMatch("rm"::contains)) {
						subCommands.add("rm");
					}
				}

			}
			return subCommands;
		}
		return null;
	}

}
