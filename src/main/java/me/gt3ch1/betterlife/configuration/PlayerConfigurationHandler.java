package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

/**
 * This class handles all of the player related configuration settings.  It contains the hashmaps and other
 * related functions to provide configuration support.
 */
public class PlayerConfigurationHandler extends ConfigurationHelper {

    public HashMap<UUID, Boolean> trailEnabledPerPlayer = new HashMap<>();
    public HashMap<UUID, String> trailPerPlayer = new HashMap<>();
    public HashMap<UUID, Boolean> roadboostPerPlayer = new HashMap<>();
    public HashMap<UUID, Location> antiGriefLocation1PerPlayer = new HashMap<>();
    public HashMap<UUID, Location> antiGriefLocation2PerPlayer = new HashMap<>();

    /**
     * Creates a new configuration handler for players (player_config.yml)
     */
    public PlayerConfigurationHandler() {
        super("players", "player_config", Main.isUsingSql);

        UUID[] playerUUIDs = null;
        if (isUsingSql) {
             playerUUIDs = (UUID[]) this.getRow("uuid").toArray();
        } else {
            try {
                playerUUIDs = (UUID[]) this.getCustomConfig().getConfigurationSection("player").getKeys(false).toArray();
            } catch (NullPointerException e) {
                Main.doBukkitLog("Error initializing playerUUIDs.");
            }
        }


        Location loc1, loc2;

        // sets up antigrief locations
        /*
        for (Object uuid : playerUUIDs) {
            UUID playerUUID = (UUID) uuid;
            try {
                loc1 = CommandUtils.parseLocation("antigrief.location.a", playerUUID, this);
                loc2 = CommandUtils.parseLocation("antigrief.location.b", playerUUID, this);
                antiGriefLocation1PerPlayer.put(playerUUID, loc1);
                antiGriefLocation2PerPlayer.put(playerUUID, loc2);
            } catch (Exception ignored) {

            }
        }*/
    }
}
