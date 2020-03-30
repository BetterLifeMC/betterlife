package me.gt3ch1.betterlife.tabcompletor;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;

public class TabCompletorHelper implements TabCompleter {
	// Initialize a string of variables
	private List<String> subCommands = null;
	private Main plugin;

	public TabCompletorHelper(Main m) {
		this.plugin = m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
		String cmd = command.toString();
		System.out.println(cmd);
		if (cmd.equalsIgnoreCase("trail") && commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("set")) {
					subCommands = (List<String>) (plugin.getMainConfiguration().getCustomConfig()
							.getList("enabledParticles"));
					for (int i = 0; i < subCommands.size(); i++) {
						String particle = subCommands.get(i);
						if (!player.hasPermission("betterlife.command.trail.particle." + particle.toLowerCase())) {
							subCommands.remove(i);
						}
					}
				} else if (args[0].equalsIgnoreCase("rm")) {
					subCommands = (List<String>) (plugin.getMainConfiguration().getCustomConfig()
							.getList("enabledParticles"));

				}
			} else {
				subCommands.add("set");
				subCommands.add("rm");
				subCommands.add("help");
				subCommands.add("add");
				subCommands.add("list");
			}
			subCommands.add("set");
			subCommands.add("rm");
			subCommands.add("help");
			subCommands.add("add");
			subCommands.add("list");
		}
		return subCommands;
	}

}
