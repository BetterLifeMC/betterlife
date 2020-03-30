package me.gt3ch1.betterlife.tabcompletor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;

public class TabCompletorHelper implements TabCompleter {
	// Initialize a string of variables
	private List<String> subCommands = new ArrayList<>();
	private Main plugin;
	private List<String> newList = new ArrayList<>();

	public TabCompletorHelper(Main m) {
		this.plugin = m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
		newList.clear();
		String cmd = command.getLabel();
		if (cmd.equalsIgnoreCase("trail") && commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("set")) {
					subCommands = (List<String>) (plugin.getMainConfiguration().getCustomConfig()
							.getList("enabledParticles"));
					for (int i = 0; i < subCommands.size(); i++) {
						String particle = subCommands.get(i);
						if (player.hasPermission("betterlife.command.trail.particle." + particle.toLowerCase())) {
							newList.add(particle);				
						}
					}
					subCommands = newList;

				} else if (args[0].equalsIgnoreCase("rm")) {
					subCommands = (List<String>) (plugin.getMainConfiguration().getCustomConfig()
							.getList("enabledParticles"));
				}
			}
			return subCommands;
		}
		return null;
	}

}
