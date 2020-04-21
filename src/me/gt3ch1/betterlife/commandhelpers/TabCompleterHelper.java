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
import java.util.Collection;
import java.util.List;

/**
 * Handles the ability of tab completion for commands as designated in CommandUtils.
 * @author gt3ch1
 * @author Starmism
 */
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
        newList.clear();
        subCommands.clear();
        String cmd = command.getLabel();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (cmd) {
                case "trail":
                    switch (args.length) {
                        case 1:
                            // Add set, list, help, toggle, and if the player
                            // has permissions, add and rm to the tab completion.
                            if (Arrays.stream(args).anyMatch("set"::contains)) {
                                subCommands.add("set");
                                System.out.println("Is set?!");
                                System.out.println(subCommands.toString());
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
                        case 2:
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
                case "eco":
                    switch (args.length) {
                        case 1:
                            if (player.hasPermission("betterlife.eco.bal"))
                                if (Arrays.stream(args).anyMatch("bal"::contains))
                                    subCommands.add("bal");
                            if (player.hasPermission("betterlife.eco.give"))
                                if (Arrays.stream(args).anyMatch("give"::contains))
                                    subCommands.add("give");
                            if (player.hasPermission("betterlife.eco.set"))
                                if (Arrays.stream(args).anyMatch("set"::contains))
                                    subCommands.add("set");
                            break;
                        case 2:
                            switch (args[0]) {
                                case "bal":
                                    if (player.hasPermission("betterlife.eco.bal")) {
                                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                            if (Arrays.stream(args).anyMatch(p.getName().toString()::contains))
                                                subCommands.add(p.getName().toString());

                                        }
                                    }

                            }
                            break;
                        default:
                            break;
                    }
            }
            return subCommands;
        }
        return null;

    }

}
