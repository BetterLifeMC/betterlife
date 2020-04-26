package me.gt3ch1.betterlife.eventhelpers;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;

import java.util.UUID;

public class PlayerAccessHelper extends CommandUtils {

    public static void setupPlayerConfig(UUID playerUUID) {
        pch.trailEnabledPerPlayer.put(playerUUID, pch.getBooleanValue("trails.enabled", playerUUID));
        pch.trailPerPlayer.put(playerUUID, pch.getStringValue("trails.trail", playerUUID));
        pch.roadboostPerPlayer.put(playerUUID, pch.getBooleanValue("roadboost", playerUUID));
    }
    public static void clearPlayerConfigs(){
        pch.trailEnabledPerPlayer.clear();
        pch.trailPerPlayer.clear();
        pch.roadboostPerPlayer.clear();
    }
    public static void clearPlayerConfigs(UUID uuid){
        pch.trailEnabledPerPlayer.remove(uuid);
        pch.trailPerPlayer.remove(uuid);
        pch.roadboostPerPlayer.remove(uuid);
    }
}
