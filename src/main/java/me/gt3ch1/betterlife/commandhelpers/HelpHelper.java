package me.gt3ch1.betterlife.commandhelpers;

import java.util.LinkedHashMap;

/**
 * Handles all help messages for BetterLife.
 * @author Starmism
 */
public class HelpHelper {

    private static LinkedHashMap<String, String> trailHelpHash = new LinkedHashMap<>();
    private static LinkedHashMap<String, String> ecoHelpHash = new LinkedHashMap<>();

    /**
     * Returns the help commands for the given command
     * @param hash What help message is to be sent.
     * @return The help message.
     */
    public static LinkedHashMap<String, String> getAHelpHash(String hash) {
        switch(hash) {
            case "trail":
                return trailHelpHash;
            case "eco":
                return ecoHelpHash;
            default:
                return null;
        }
    }

    /**
     * Sets up all of the hashes.
     */
    public static void setupAllHelpHashes() {
        setupTrailHelpHash();
        setupEcoHelpHash();
    }

    private static void setupTrailHelpHash() {
        trailHelpHash.put("set <trail>", "Sets the current player's trail");
        trailHelpHash.put("list", "Lists the enabled trails");
        trailHelpHash.put("toggle", "Toggles your trail");
    }
    private static void setupEcoHelpHash(){
        ecoHelpHash.put("bal [player]", "lists the balance of the given player");
        ecoHelpHash.put("give <player> <bal>", "gives the player some money");
        ecoHelpHash.put("set <player> <bal>", "sets the player's balance.");
    }
}
