package betterlife.testing;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import me.gt3ch1.betterlife.Main.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBetterLife {
    private ServerMock server;
    private Main plugin;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = (Main) MockBukkit.load(Main.class);
//        plugin
    }

    @Test
    public void testServer() {
        plugin.onLoad();
        server.addPlayer();
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}