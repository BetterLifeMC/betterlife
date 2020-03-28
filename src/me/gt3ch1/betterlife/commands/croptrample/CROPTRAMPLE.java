package me.gt3ch1.betterlife.commands.croptrample;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commands.BetterLifeCommands;

public class CROPTRAMPLE extends BetterLifeCommands implements CommandExecutor {
	private Main m;

	public CROPTRAMPLE(Main m, String permission, CommandSender cs, Command c, String label, String[] args) {
		super(m, permission, cs, c, label, args);
		System.out.println(this.getClass().toString() + " :: permission -> " + getPermission());
		this.m = m;
		this.onCommand(cs, c, label, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command c, String command, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission(this.getPermission())) {
				boolean hasCropTrampleEnabled = m.getMainConfiguration().getCustomConfig()
						.getBoolean("events.croptrample");
				m.getMainConfiguration().getCustomConfig().set("events.croptrample", !hasCropTrampleEnabled);
				m.getMainConfiguration().saveCustomConfig();
				if (!hasCropTrampleEnabled)
					sendMessageToPlayer(p, "Anti-Crop-Trample has been enabled!", ChatColor.GREEN);
				else
					sendMessageToPlayer(p, "Anti-Crop-Trample has been disabled!!", ChatColor.RED);

			}
		} else
			sender.sendMessage(ChatColor.DARK_RED + "This must be done from in game!");

		return true;
	}
}
