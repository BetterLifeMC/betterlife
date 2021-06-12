package me.gt3ch1.betterlife.configuration;

import me.gt3ch1.betterlife.main.BetterLife;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class handles all of the configuration files for the player particles.
 */
@Singleton
public class ParticleConfigurationHandler extends YamlConfigurationHandler {

    /**
     * Creates a new configuration handler for particles (particles.yml)
     */
    @Inject
    public ParticleConfigurationHandler(BetterLife m) {
        super("particles", m);
    }
}
