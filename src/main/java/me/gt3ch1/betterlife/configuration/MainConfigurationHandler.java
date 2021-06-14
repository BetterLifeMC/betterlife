package me.gt3ch1.betterlife.configuration;

import com.google.inject.Singleton;
import me.gt3ch1.betterlife.main.BetterLife;

import javax.inject.Inject;

/**
 * This class handles the main configuration of the plugin.
 */
@Singleton
public class MainConfigurationHandler extends YamlConfigurationHandler {

    @Inject
    public MainConfigurationHandler(BetterLife m) {
        super("config", m);
    }

}
