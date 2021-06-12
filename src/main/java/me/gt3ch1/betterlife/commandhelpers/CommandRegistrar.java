package me.gt3ch1.betterlife.commandhelpers;

import com.google.common.collect.ImmutableMap;
import me.gt3ch1.betterlife.main.BetterLife;
import me.gt3ch1.betterlife.commands.*;

import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;

public class CommandRegistrar {

    private final BetterLife main;
    private final CommandRouter commandRouter;
    private final Map<String, BetterLifeCommand> commandMap;

    @Inject
    public CommandRegistrar(BlCommand blCommand, EcoCommand ecoCommand, HomeCommand homeCommand,
                            MuteCommand muteCommand, RoadboostCommand roadboostCommand,
                            SpawnCommand spawnCommand, TeleportCommand teleportCommand,
                            ToggledownfallCommand toggledownfallCommand, TrailCommand trailCommand,
                            WarpCommand warpCommand, BetterLife main) {
        this.main = main;

        commandMap = new ImmutableMap.Builder<String, BetterLifeCommand>()
                .put("bl", blCommand)
                .put("eco", ecoCommand)
                .put("home", homeCommand)
                .put("mute", muteCommand)
                .put("roadboost", roadboostCommand)
                .put("spawn", spawnCommand)
                .put("teleport", teleportCommand)
                .put("toggledownfall", toggledownfallCommand)
                .put("trail", trailCommand)
                .put("warp", warpCommand)
                .build();

        commandRouter = new CommandRouter(commandMap);
    }

    public void register() {
        commandMap.keySet().forEach(cmd -> {
            Objects.requireNonNull(main.getCommand(cmd), "Command not registered in plugin.yml").setExecutor(commandRouter);
            Objects.requireNonNull(main.getCommand(cmd), "Command not registered in plugin.yml").setTabCompleter(commandRouter);
        });
    }
}
