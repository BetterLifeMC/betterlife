package me.gt3ch1.betterlife.commands;

import me.gt3ch1.betterlife.commandhelpers.BetterLifeCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.gt3ch1.betterlife.commandhelpers.CommandUtils.sendMessage;

public class ToggledownfallCommand implements BetterLifeCommand {

    /**
     * Toggles the weather in @world
     *
     * @param world  World to change the weather of.
     * @param sender Who sent the command
     */
    private void toggleWeather(@NotNull World world, @NotNull CommandSender sender) {

        if (world.hasStorm() || world.isThundering()) {
            world.setStorm(false);
            world.setThundering(false);
            sendMessage(sender, "&7Setting weather to &6clear &7in world &6" + world.getName() + "&7!", true);
        } else {
            world.setStorm(true);
            world.setThundering(true);
            sendMessage(sender, "&7Setting weather to &6stormy &7in world &6" + world.getName() + "&7!", true);
        }
    }

    @Override
    public String getPermission() {
        return "betterlife.toggledownfall";
    }

    @Override
    public List<String> getHelpList() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Toggles the weather from rainy <-> not";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        switch (args.length) {
            case 0 -> {
                if (sender instanceof Player player) {
                    World world = player.getWorld();
                    toggleWeather(world, player);
                } else {
                    World world = Bukkit.getServer().getWorlds().get(0);
                    toggleWeather(world, sender);
                }
            }
            case 1 -> {
                World world = Bukkit.getServer().getWorld(args[0]);

                if (world == null || world.getEnvironment() == Environment.NETHER || world.getEnvironment() == Environment.THE_END) {
                    sendMessage(sender, "&4Can't change weather in that world!", true);
                    return true;
                }

                toggleWeather(world, sender);
            }
            default -> sendMessage(sender, "&4Too many arguments!", true);
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        final List<String> subCommands = new ArrayList<>();
        if (!sender.hasPermission("betterlife.toggledownfall")) {
            return Collections.emptyList();
        }

        // Only show overworlds
        for (World w : Bukkit.getServer().getWorlds()) {
            if (w.getEnvironment() != Environment.NETHER && w.getEnvironment() != Environment.THE_END) {
                subCommands.add(w.getName());
            }
        }
        return subCommands;
    }
}
