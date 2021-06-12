package me.gt3ch1.betterlife.commandhelpers;

import me.gt3ch1.betterlife.Main.BetterLife;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Handles the ability of tab completion for commands as designated in CommandUtils.
 *
 * @author gt3ch1
 * @author Starmism
 */
public class TabCompleterHelper implements TabCompleter {

    private final List<String> subCommands = new ArrayList<>();

    /**
     * When we can tab complete commands. NOTE: Must be enabled in CommandUtils!
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        subCommands.clear();

        if (sender instanceof Player player) {
            switch (command.getLabel()) {
                case "trail" -> {
                    switch (args.length) {
                        case 1 -> {
                            var trailCmds = Arrays.asList("set", "list", "help", "toggle");
                            for (String cmd : trailCmds) {
                                if (player.hasPermission("betterlife.trail." + cmd)) {
                                    subCommands.add(cmd);
                                }
                            }
                        }
                        case 2 -> {
                            // If it is set, return the list of currently enabled particles,
                            // and append them to the tablist list.
                            if (args[0].equalsIgnoreCase("set")) {
                                for (String particle : CommandUtils.partch.getCustomConfig().getStringList("particle")) {
                                    if (player.hasPermission("betterlife.trail.particle." + particle.toLowerCase())) {
                                        subCommands.add(particle);
                                    }
                                }
                            }
                        }
                    }
                }
                case "toggledownfall" -> {
                    if (!player.hasPermission("betterlife.toggledownfall")) {
                        break;
                    }

                    // Only show overworlds
                    for (World w : Bukkit.getServer().getWorlds()) {
                        if (w.getEnvironment() != Environment.NETHER && w.getEnvironment() != Environment.THE_END) {
                            subCommands.add(w.getName());
                        }
                    }
                }
                case "bl" -> {
                    if (args.length == 1) {
                        if (player.hasPermission("betterlife.reload") || player.hasPermission("betterlife.admin")) {
                            subCommands.add("reload");
                        }
                        subCommands.add("version");
                    }
                }
                case "eco" -> {
                    if (BetterLife.economy == null) {
                        break;
                    }

                    switch (args.length) {
                        case 1 -> {
                            var ecoCmds = Arrays.asList("bal");
                            for (String cmd : ecoCmds) {
                                if (player.hasPermission("betterlife.eco." + cmd)) {
                                    subCommands.add(cmd);
                                }
                            }
                        }
                        case 2 -> {
                            var ecoCmds = Arrays.asList("bal.others");
                            for (String cmd : ecoCmds) {
                                if (player.hasPermission("betterlife.eco." + cmd)) {
                                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                        subCommands.add(p.getName());
                                    }
                                }
                            }
                        }
                    }
                }
                case "home" -> {
                    switch (args.length) {
                        case 1 -> {
                            if (player.hasPermission("betterlife.home")) {
                                subCommands.addAll(BetterLife.bl_home.getHomes(player.getUniqueId()).keySet());
                            }

                            var homeCmds = Arrays.asList("set", "del", "list");
                            for (String cmd : homeCmds) {
                                if (player.hasPermission("betterlife.home." + cmd)) {
                                    subCommands.add(cmd);
                                }
                            }
                        }
                        case 2 -> {
                            switch (args[1]) {
                                case "del" -> {
                                    if (player.hasPermission("betterlife.home.del")) {
                                        subCommands.addAll(BetterLife.bl_home.getHomes(player.getUniqueId()).keySet());
                                    }
                                }
                                case "list" -> {
                                    if (player.hasPermission("betterlife.home.list.others")) {
                                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                            subCommands.add(p.getName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                case "warp" -> {
                    switch (args.length) {
                        case 1 -> {
                            if (player.hasPermission("betterlife.warp")) {
                                for (String warpName : BetterLife.bl_warp.getWarps().keySet()) {
                                    if (player.hasPermission("betterlife.warp." + warpName)) {
                                        subCommands.add(warpName);
                                    }
                                }
                            }

                            var warpCmds = Arrays.asList("set", "del", "list");
                            for (String cmd : warpCmds) {
                                if (player.hasPermission("betterlife.warp." + cmd)) {
                                    subCommands.add(cmd);
                                }
                            }
                        }
                        case 2 -> {
                            if (player.hasPermission("betterlife.warp.del")) {
                                for (String warpName : BetterLife.bl_warp.getWarps().keySet()) {
                                    if (player.hasPermission("betterlife.warp." + warpName)) {
                                        subCommands.add(warpName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return subCommands;
        }
        return null;
    }
}
