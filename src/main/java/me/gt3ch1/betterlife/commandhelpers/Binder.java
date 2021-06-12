package me.gt3ch1.betterlife.commandhelpers;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.gt3ch1.betterlife.main.BetterLife;
import net.milkbowl.vault.economy.Economy;

public class Binder extends AbstractModule {

    private final BetterLife bl;
    private final Economy economy;

    public Binder(BetterLife bl, Economy economy) {
        this.bl = bl;
        this.economy = economy;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        this.bind(BetterLife.class).toInstance(this.bl);
        this.bind(Economy.class).toInstance(this.economy);
    }
}
