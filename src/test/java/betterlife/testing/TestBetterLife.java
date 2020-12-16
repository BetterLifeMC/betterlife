package betterlife.testing;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.gt3ch1.betterlife.Main.Main;
import me.gt3ch1.betterlife.data.BL_PLAYER_ENUM;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class TestBetterLife {
    private ServerMock server;
    private Main plugin;
    private UUID playerOneUUID = UUID.fromString("ed275ae9-e765-408b-b056-3d5a5d540626");
    private UUID playerTwoUUID = UUID.fromString("ed275ae9-e765-408b-b056-3d5a5d540627");
    private PlayerMock playerOne;
    private PlayerMock playerTwo;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = (Main) MockBukkit.load(Main.class);
        plugin.onLoad();
        playerOne = new PlayerMock(server, "playerone", playerOneUUID);
        playerTwo = new PlayerMock(server, "playertwo", playerTwoUUID);
        server.addPlayer(playerOne);
        playerOne.setOp(true);
    }

    @Test
    public void testRoadBoost() {
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER;
        boolean previousRoadBoostState = plugin.getBlPlayer().getPlayerToggle(playerOneUUID, type);
        assertTrue(playerOne.performCommand("roadboost"));
        boolean currentRoadboostState = plugin.getBlPlayer().getPlayerToggle(playerOneUUID, type);
        assertTrue(previousRoadBoostState != currentRoadboostState);
    }

    @Test
    public void testSqlConnection(){
        assertTrue(plugin.getSql().isSqlConnected());
    }

    @Test
    public void testPlayerMute(){
        playerOne.performCommand("mute testplayer");
        assertTrue(plugin.getBlPlayer().getPlayerToggle(playerOneUUID,BL_PLAYER_ENUM.MUTE_PER_PLAYER));
    }

    @Test
    public void testPlayerUnmute(){
        playerOne.performCommand("unmute testplayer");
        assertFalse(plugin.getBlPlayer().getPlayerToggle(playerOneUUID,BL_PLAYER_ENUM.MUTE_PER_PLAYER));
    }

    @Test
    public void testTrailToggle(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER;
        boolean previousTrailState = plugin.getBlPlayer().getPlayerToggle(playerOneUUID, type);
        playerOne.performCommand("trail toggle");
        boolean currentTrailState = plugin.getBlPlayer().getPlayerToggle(playerOneUUID, type);
        assertTrue(previousTrailState != currentTrailState);
    }

    @Test
    public void setTrailToCrit(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_PER_PLAYER;
        playerOne.performCommand("trail set crit");
        String currentTrail = plugin.getBlPlayer().getPlayerString(playerOneUUID,type);
        assertEquals("CRIT",currentTrail);
    }

    @Test
    public void setTrailToNote(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_PER_PLAYER;
        playerOne.performCommand("trail set note");
        String currentTrail = plugin.getBlPlayer().getPlayerString(playerOneUUID,type);
        assertEquals("NOTE",currentTrail);
    }

    @Test
    public void setTrailToCloud(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_PER_PLAYER;
        playerOne.performCommand("trail set cloud");
        String currentTrail = plugin.getBlPlayer().getPlayerString(playerOneUUID,type);
        assertEquals("CLOUD",currentTrail);
    }

    @Test
    public void setTrailToInvalid(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_PER_PLAYER;
        String previousTrail = plugin.getBlPlayer().getPlayerString(playerOneUUID,type);
        playerOne.performCommand("trail set dinkleberg");
        String currentTrail = plugin.getBlPlayer().getPlayerString(playerOneUUID,type);
        assertEquals(previousTrail,currentTrail);
    }

    @Test
    public void testToggleDownfall(){
        World w = server.getWorld("world");
        w.setStorm(false);
        assertFalse(w.hasStorm());
        playerOne.performCommand("toggledownfall");
        assertTrue(w.hasStorm());
    }

    @Test
    public void createWarp(){
        playerOne.performCommand("warp set test_warp");
        assertTrue(plugin.getBlWarps().getWarps().size() > 0);
        playerOne.performCommand("warp del test_warp");
    }

    @Test
    public void deleteWarp(){
        playerOne.performCommand("warp set test_warp");
        int previousSize = plugin.getBlWarps().getWarps().size();
        playerOne.performCommand("warp del test_warp");
        assertEquals(previousSize-1,plugin.getBlWarps().getWarps().size());
    }

    @Test
    public void createMultipleWarps(){
        int previousSize = plugin.getBlWarps().getWarps().size();
        playerOne.performCommand("warp set test_warp_1");
        playerOne.performCommand("warp set test_warp_2");
        assertEquals(previousSize+2,plugin.getBlWarps().getWarps().size());
        playerOne.performCommand("warp del test_warp_1");
        playerOne.performCommand("warp del test_warp_2");
    }

    @Test
    public void deleteMultipleWarps(){
        int previousSize = plugin.getBlWarps().getWarps().size();
        playerOne.performCommand("warp set test_warp_1");
        playerOne.performCommand("warp set test_warp_2");
        playerOne.performCommand("warp del test_warp_1");
        playerOne.performCommand("warp del test_warp_2");
        assertEquals(previousSize,plugin.getBlWarps().getWarps().size());

    }

    @Test
    public void testWarpTeleport(){
        Location testLocation = new Location(server.getWorld("world"),100,100,100);
        playerOne.teleport(testLocation);
        playerOne.performCommand("warp set test_warp");
        playerOne.teleport(new Location(server.getWorld("world"),0,5,0));
        playerOne.performCommand("warp test_warp");
        assertEquals(testLocation, playerOne.getLocation());
        playerOne.performCommand("warp del test_warp");
    }

    @Test
    public void testWarpSetLocation(){
        Location testLocation = new Location(server.getWorld("world"),100,100,100);
        playerOne.teleport(testLocation);
        playerOne.performCommand("warp set test_warp");
        playerOne.teleport(new Location(server.getWorld("world"),0,5,0));
        assertEquals(testLocation,plugin.getBlWarps().getWarps().get("test_warp"));
        playerOne.performCommand("warp del test_warp");
    }

    @Test
    public void createHome(){
        playerOne.performCommand("home set test_home");
        assertEquals(1,plugin.getBlHomes().getHomes(playerOneUUID).size());
        playerOne.performCommand("home del test_home");
    }

    @Test
    public void deleteHome(){
        playerOne.performCommand("home set test_home");
        playerOne.performCommand("home del test_home");
        assertEquals(0,plugin.getBlHomes().getHomes(playerOneUUID).size());
    }

    @Test
    public void createMultipleHomes(){
        playerOne.performCommand("home set test_home_1");
        playerOne.performCommand("home set test_home_2");
        assertEquals(2,plugin.getBlHomes().getHomes(playerOneUUID).size());
        playerOne.performCommand("home del test_home_1");
        playerOne.performCommand("home del test_home_2");
    }

    @Test
    public void deleteMultipleHomes(){
        playerOne.performCommand("home set test_home_1");
        playerOne.performCommand("home set test_home_2");
        playerOne.performCommand("home del test_home_1");
        playerOne.performCommand("home del test_home_2");
        assertEquals(0,plugin.getBlHomes().getHomes(playerOneUUID).size());
    }

    @Test
    public void testHomeSetLocation(){
        Location testLocation = new Location(server.getWorld("world"),100,100,100);
        playerOne.teleport(testLocation);
        playerOne.performCommand("home set test_home");
        playerOne.teleport(new Location(server.getWorld("world"),0,5,0));
        assertEquals(testLocation,plugin.getBlHomes().getHomes(playerOneUUID).get("test_home"));
        playerOne.performCommand("home del test_home");
    }

    @Test
    public void testHomeTeleport(){
        Location testLocation = new Location(server.getWorld("world"),100,100,100);
        playerOne.teleport(testLocation);
        playerOne.performCommand("home set test_home");
        playerOne.teleport(new Location(server.getWorld("world"),0,5,0));
        playerOne.performCommand("home test_home");
        assertEquals(testLocation, playerOne.getLocation());
        playerOne.performCommand("home del test_home");
    }

    @Test
    public void testPlayerTeleport(){
        Location testLocation = new Location(server.getWorld("world"),100,100,100);
        playerTwo.teleport(testLocation);
        playerOne.performCommand("teleport playertwo");
        assertEquals(testLocation, playerOne.getLocation());
        playerOne.teleport(new Location(server.getWorld("world"),0,5,0));
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}