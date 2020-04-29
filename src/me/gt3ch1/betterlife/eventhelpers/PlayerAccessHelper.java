package me.gt3ch1.betterlife.eventhelpers;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;

import java.util.UUID;

/**
 * This class contains some methods to help with the adding, and removing of a players configuration
 * without the need of querying a database on every event.
 *
 * @author gt3ch1
 */
public class PlayerAccessHelper extends CommandUtils {

    public static void setupPlayerConfig(UUID playerUUID) {
        pch.trailEnabledPerPlayer.put(playerUUID, pch.getBooleanValue("trails.enabled", playerUUID));
        pch.trailPerPlayer.put(playerUUID, pch.getStringValue("trails.trail", playerUUID));
        pch.roadboostPerPlayer.put(playerUUID, pch.getBooleanValue("roadboost", playerUUID));
    }

    public static void clearPlayerConfigs() {
        try {
            pch.trailEnabledPerPlayer.clear();
            pch.trailPerPlayer.clear();
            pch.roadboostPerPlayer.clear();
        } catch (Exception e) {

        }
    }

    public static void clearPlayerConfigs(UUID uuid) {
        try {
            pch.trailEnabledPerPlayer.remove(uuid);
            pch.trailPerPlayer.remove(uuid);
            pch.roadboostPerPlayer.remove(uuid);
        } catch (Exception e) {
        }
    }
}
