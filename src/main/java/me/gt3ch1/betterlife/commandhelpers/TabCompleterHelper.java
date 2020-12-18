package me.gt3ch1.betterlife.commandhelpers;

import me.gt3ch1.betterlife.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Handles the ability of tab completion for commands as designated in CommandUtils.
 *
 * @author gt3ch1
 * @author Starmism
 */
public class TabCompleterHelper implements TabCompleter {

    private List<String> subCommands = new ArrayList<>();
    private List<String> newList = new ArrayList<>();

    /**
     * When we can tab complete commands.
     * NOTE: Must be enabled in CommandUtils!
     */
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
                            if (Arrays.stream(args).anyMatch("set"::contains))
                                subCommands.add("set");

                            if (Arrays.stream(args).anyMatch("list"::contains))
                                subCommands.add("list");

                            if (Arrays.stream(args).anyMatch("help"::contains))
                                subCommands.add("help");

                            if (Arrays.stream(args).anyMatch("toggle"::contains))
                                subCommands.add("toggle");

                        case 2:
                            // If it is set, return the list of currently enabled particles,
                            // and append them to the tablist list.
                            List<String> allowedParticles = CommandUtils.partch.getCustomConfig()
                                    .getStringList("particle");
                            if ("set".equals(args[0])) {
                                for (String particle : allowedParticles)
                                    if (Arrays.stream(args).anyMatch(particle::contains) && player
                                            .hasPermission("betterlife.trail.particle." + particle.toLowerCase()))
                                        newList.add(particle);
                                subCommands = newList;
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
                                && player.hasPermission("betterlife.toggledownfall"))
                            subCommands.add(w.getName());

                    }
                    break;
                case "bl":
                    if (args.length == 1) {
                        if (player.hasPermission("betterlife.reload") || player.hasPermission("betterlife.admin"))
                            if (Arrays.stream(args).anyMatch("reload"::contains))
                                subCommands.add("reload");
                        if (Arrays.stream(args).anyMatch("version"::contains))
                            subCommands.add("version");

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
                                    if (player.hasPermission("betterlife.eco.bal"))
                                        for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers())
                                            if (Arrays.stream(args).anyMatch(Objects.requireNonNull(p.getName())::contains))
                                                subCommands.add(p.getName());
                                    break;
                                case "set":
                                    if (player.hasPermission("betterlife.eco.set"))
                                        for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers())
                                            if (Arrays.stream(args).anyMatch(Objects.requireNonNull(p.getName())::contains))
                                                subCommands.add(p.getName());
                                case "give":
                                    if (player.hasPermission("betterlife.eco.give"))
                                        for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers())
                                            if (Arrays.stream(args).anyMatch(Objects.requireNonNull(p.getName())::contains))
                                                subCommands.add(p.getName());
                                default:
                                    break;
                            }
                        default:
                            break;
                    }
                case "home":
                    switch (args.length) {
                        case 1:
                            if (player.hasPermission("betterlife.home.set"))
                                if (Arrays.stream(args).anyMatch("set"::contains))
                                    subCommands.add("set");
                            if (player.hasPermission("betterlife.home.del"))
                                if (Arrays.stream(args).anyMatch("del"::contains))
                                    subCommands.add("del");
                            if (player.hasPermission("betterlife.home.list"))
                                if (Arrays.stream(args).anyMatch("list"::contains))
                                    subCommands.add("list");
                            if (player.hasPermission("betterlife.home.home")) {
                                LinkedHashMap<String, Location> homes = Main.bl_home.getHomes(player.getUniqueId());
                                for (String homeName : homes.keySet())
                                    if (Arrays.stream(args).anyMatch(homeName::contains))
                                        subCommands.add(homeName);

                            }
                            break;
                        case 2:
                            if(player.hasPermission("betterlife.home.del")){
                                LinkedHashMap<String, Location> homes = Main.bl_home.getHomes(player.getUniqueId());
                                for (String homeName : homes.keySet())
                                    if (Arrays.stream(args).anyMatch(homeName::contains))
                                        subCommands.add(homeName);
                            }
                            break;
                    }
                case "warp":
                    switch (args.length) {
                        case 1:
                            if (player.hasPermission("betterlife.warp.set"))
                                if (Arrays.stream(args).anyMatch("set"::contains))
                                    subCommands.add("set");
                            if (player.hasPermission("betterlife.warp.del"))
                                if (Arrays.stream(args).anyMatch("del"::contains))
                                    subCommands.add("del");
                            if (player.hasPermission("betterlife.warp.list"))
                                if (Arrays.stream(args).anyMatch("list"::contains))
                                    subCommands.add("list");
                            if (player.hasPermission("betterlife.warp")) {
                                LinkedHashMap<String, Location> warps = Main.bl_warp.getWarps();
                                for (String warpName : warps.keySet())
                                    if(player.hasPermission("betterlife.warp." + warpName))
                                        if (Arrays.stream(args).anyMatch(warpName::contains))
                                            subCommands.add(warpName);


                            }
                            break;
                        case 2:
                            if(player.hasPermission("betterlife.warp.del")){
                                LinkedHashMap<String, Location> warps = Main.bl_warp.getWarps();
                                for (String warpName : warps.keySet())
                                    if (Arrays.stream(args).anyMatch(warpName::contains))
                                        subCommands.add(warpName);
                            }
                            break;
                    }
                default:
                    break;

            }
            return subCommands;
        }
        return null;

    }

}
