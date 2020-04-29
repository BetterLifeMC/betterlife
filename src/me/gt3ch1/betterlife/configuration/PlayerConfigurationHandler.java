package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.commandhelpers.CommandUtils;
import me.gt3ch1.betterlife.eventhelpers.BlockBreakHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * This class handles all of the player related configuration settings.  It contains the hashmaps and other
 * related functions to provide configuration support.
 */
public class PlayerConfigurationHandler extends ConfigurationHelper {
    // Declare m
    public HashMap<UUID, Boolean> trailEnabledPerPlayer = new HashMap<>();
    public HashMap<UUID, String> trailPerPlayer = new HashMap<>();
    public HashMap<UUID, Boolean> roadboostPerPlayer = new HashMap<>();
    public HashMap<UUID, Location> antiGriefLocation1PerPlayer = new HashMap<>();
    public HashMap<UUID, Location> antiGriefLocation2PerPlayer = new HashMap<>();

    /**
     * Creates a new configuration handler for players (player_config.yml)
     */
    public PlayerConfigurationHandler() {
        super("players", "player_config", true);
        List<String> playerUUUIDs = this.getRow("uuid");
        Location loc1, loc2;
        for (String playerUUIDString : playerUUUIDs) {
            UUID playerUUID = UUID.fromString(playerUUIDString);
            try {
                loc1 = parseLocation("a", playerUUID);
                loc2 = parseLocation("b", playerUUID);
                antiGriefLocation1PerPlayer.put(playerUUID, loc1);
                antiGriefLocation2PerPlayer.put(playerUUID, loc2);
            }catch(Exception e){

            }


        }
    }

    /**
     * Converts a String into a location.
     *
     * @param antigriefLocation
     * @param playerUUID
     * @return
     */
    public Location parseLocation(String antigriefLocation, UUID playerUUID) {
        String locationString1 = get("antigrief.location." + antigriefLocation, playerUUID).toString()
                .replace("Location{world=CraftWorld{name", "").replace("}", "");
        String[] splitLocString1 = locationString1.split(",");
        String[] newLoc1 = new String[splitLocString1.length];

        for (int x = 0; x < splitLocString1.length; x++)
            newLoc1[x] = splitLocString1[x].split("=")[1];
        return new Location(Bukkit.getWorld(newLoc1[0]), Double.valueOf(newLoc1[1]), Double.valueOf(newLoc1[2]), Double.valueOf(newLoc1[3]), Float.valueOf(newLoc1[4]), Float.valueOf(newLoc1[5]));
    }

}
