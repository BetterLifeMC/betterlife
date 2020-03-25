package me.gt3ch1.betterlife.commands.toggledownfall;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;

public class ToggleDownfall extends Command {
	public ToggleDownfall() {
		super("toggledownfall");
        this.description = "Toggles rain on/off on a given world";
        this.usageMessage = "/toggledownfall";
        this.setPermission("bukkit.command.toggledownfall");
	}
//	public static void voidDoCommand(CommandSender cs, Command c, String command, String[] args) {
//		if(c.getName().equalsIgnoreCase("toggledownfall")) {
//			Player p = (Player) cs;
//			p.getWorld().get
//		}
//	}
	@Override
	public boolean execute(CommandSender cs, String command, String[] args) {
		Player p = (Player)cs;
		return false;
	}
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        return ImmutableList.of();
    }
}
