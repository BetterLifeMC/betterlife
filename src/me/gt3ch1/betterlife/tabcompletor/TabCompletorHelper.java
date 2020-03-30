package me.gt3ch1.betterlife.tabcompletor;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.gt3ch1.betterlife.Main.Main;

public class TabCompletorHelper implements TabCompleter {
	// Initialize a string of variables
	private List<String> subCommands = null;
	private Main plugin;

	public TabCompletorHelper(Main m) {
		this.plugin = m;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command command, String alias, String[] args) {
		String cmd = command.toString();
		if (cmd.equalsIgnoreCase("cmd")) {
			if (args[0].equalsIgnoreCase("set")) {
				subCommands = (List<String>) (plugin.getMainConfiguration().getCustomConfig()
						.getList("enabledParticles"));
			} else if (args[0].equalsIgnoreCase("rm")) {
				subCommands = (List<String>) (plugin.getMainConfiguration().getCustomConfig()
						.getList("enabledParticles"));
			}
		}
		return subCommands;
	}

}
