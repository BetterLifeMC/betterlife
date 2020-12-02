package betterlife.testing;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class TestBetterLife {
    private ServerMock server;
    private Main plugin;
    private UUID mockUUID = UUID.fromString("ed275ae9-e765-408b-b056-3d5a5d540626");
    private PlayerMock player = new PlayerMock(server, "gt3ch1", mockUUID);

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = (Main) MockBukkit.load(Main.class);
        plugin.onLoad();
        server.addPlayer(player);
        player.setOp(true);
    }

    @Test
    public void testRoadBoost() {
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER;
        boolean previousRoadBoostState = plugin.getBlPlayer().getPlayerToggle(mockUUID, type);
        assertTrue(player.performCommand("roadboost"));
        boolean currentRoadboostState = plugin.getBlPlayer().getPlayerToggle(mockUUID, type);
        assertTrue(!previousRoadBoostState != currentRoadboostState);
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}