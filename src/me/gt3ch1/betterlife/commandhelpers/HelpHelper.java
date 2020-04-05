package me.gt3ch1.betterlife.commandhelpers;

import java.util.LinkedHashMap;

public class HelpHelper {

    private static LinkedHashMap<String, String> trailHelpHash = new LinkedHashMap<String, String>();

    public static LinkedHashMap<String, String> getAHelpHash(String hash) {
        switch(hash) {
            case "trail":
                return trailHelpHash;
        }
        return null;
    }

    public static void setupAllHelpHashes() {
        setupTrailHelpHash();
    }

    private static void setupTrailHelpHash() {
        trailHelpHash.put("set <trail>", "Sets the current player's trail");
        trailHelpHash.put("list", "Lists the enabled trails");
        trailHelpHash.put("toggle", "Toggles your trail");
    }
}
