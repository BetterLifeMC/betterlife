package me.gt3ch1.betterlife.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;

public class TOGGLEDOWNFALL extends BetterLifeCommands implements CommandExecutor {
	
	public TOGGLEDOWNFALL(Main m, String permission, CommandSender cs, Command c, String label, String[] args) {
		super(m, permission, cs, c, label, args);
		System.out.println(this.getClass().toString() + " :: permission -> " + getPermission());
		
		this.onCommand(cs, c, label, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission(this.getPermission())) {
				World w = p.getWorld();
				if (w.hasStorm() || w.isThundering()) {
					w.setStorm(false);
					w.setThundering(false);
					CommandTemplate.sendMessageToPlayer(p, "Setting weather to clear.", ChatColor.GREEN);

				} else {
					w.setStorm(true);
					w.setThundering(true);
					CommandTemplate.sendMessageToPlayer(p, "Setting weather to storm.", ChatColor.GREEN);

				}
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "This must be done from in game!");
		}
		return true;
	}
}
