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
    private UUID mockUUID = UUID.fromString("ed275ae9-e765-408b-b056-3d5a5d540626");
    private PlayerMock player;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = (Main) MockBukkit.load(Main.class);
        plugin.onLoad();
        player = new PlayerMock(server, "testplayer", mockUUID);
        server.addPlayer(player);
        player.setOp(true);
    }

    @Test
    public void testRoadBoost() {
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.ROADBOOST_PER_PLAYER;
        boolean previousRoadBoostState = plugin.getBlPlayer().getPlayerToggle(mockUUID, type);
        assertTrue(player.performCommand("roadboost"));
        boolean currentRoadboostState = plugin.getBlPlayer().getPlayerToggle(mockUUID, type);
        assertTrue(previousRoadBoostState != currentRoadboostState);
    }

    @Test
    public void testSqlConnection(){
        assertTrue(plugin.getSql().isSqlConnected());
    }

    @Test
    public void testPlayerMute(){
        player.performCommand("mute testplayer");
        assertTrue(plugin.getBlPlayer().getPlayerToggle(mockUUID,BL_PLAYER_ENUM.MUTE_PER_PLAYER));
    }

    @Test
    public void testPlayerUnmute(){
        player.performCommand("unmute testplayer");
        assertFalse(plugin.getBlPlayer().getPlayerToggle(mockUUID,BL_PLAYER_ENUM.MUTE_PER_PLAYER));
    }

    @Test
    public void testTrailToggle(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_ENABLED_PER_PLAYER;
        boolean previousTrailState = plugin.getBlPlayer().getPlayerToggle(mockUUID, type);
        player.performCommand("trail toggle");
        boolean currentTrailState = plugin.getBlPlayer().getPlayerToggle(mockUUID, type);
        assertTrue(previousTrailState != currentTrailState);
    }

    @Test
    public void setTrailToCrit(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_PER_PLAYER;
        player.performCommand("trail set crit");
        String currentTrail = plugin.getBlPlayer().getPlayerString(mockUUID,type);
        assertEquals("CRIT",currentTrail);
    }

    @Test
    public void setTrailToNote(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_PER_PLAYER;
        player.performCommand("trail set note");
        String currentTrail = plugin.getBlPlayer().getPlayerString(mockUUID,type);
        assertEquals("NOTE",currentTrail);
    }

    @Test
    public void setTrailToCloud(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_PER_PLAYER;
        player.performCommand("trail set cloud");
        String currentTrail = plugin.getBlPlayer().getPlayerString(mockUUID,type);
        assertEquals("CLOUD",currentTrail);
    }

    @Test
    public void setTrailToInvalid(){
        BL_PLAYER_ENUM type = BL_PLAYER_ENUM.TRAIL_PER_PLAYER;
        String previousTrail = plugin.getBlPlayer().getPlayerString(mockUUID,type);
        player.performCommand("trail set dinkleberg");
        String currentTrail = plugin.getBlPlayer().getPlayerString(mockUUID,type);
        assertEquals(previousTrail,currentTrail);
    }

    @Test
    public void testToggleDownfall(){
        World w = server.getWorld("world");
        w.setStorm(false);
        assertFalse(w.hasStorm());
        player.performCommand("toggledownfall");
        assertTrue(w.hasStorm());
    }

    @Test
    public void createWarp(){
        player.performCommand("warp set test_warp");
        assertTrue(plugin.getBlWarps().getWarps().size() > 0);
        player.performCommand("warp del test_warp");
    }

    @Test
    public void deleteWarp(){
        player.performCommand("warp set test_warp");
        int previousSize = plugin.getBlWarps().getWarps().size();
        player.performCommand("warp del test_warp");
        assertEquals(previousSize-1,plugin.getBlWarps().getWarps().size());
    }

    @Test
    public void createMultipleWarps(){
        int previousSize = plugin.getBlWarps().getWarps().size();
        player.performCommand("warp set test_warp_1");
        player.performCommand("warp set test_warp_2");
        assertEquals(previousSize+2,plugin.getBlWarps().getWarps().size());
        player.performCommand("warp del test_warp_1");
        player.performCommand("warp del test_warp_2");
    }

    @Test
    public void deleteMultipleWarps(){
        int previousSize = plugin.getBlWarps().getWarps().size();
        player.performCommand("warp set test_warp_1");
        player.performCommand("warp set test_warp_2");
        player.performCommand("warp del test_warp_1");
        player.performCommand("warp del test_warp_2");
        assertEquals(previousSize,plugin.getBlWarps().getWarps().size());

    }

    @Test
    public void testWarpTeleport(){
        Location testLocation = new Location(server.getWorld("world"),100,100,100);
        player.teleport(testLocation);
        player.performCommand("warp set test_warp");
        player.teleport(new Location(server.getWorld("world"),0,5,0));
        player.performCommand("warp test_warp");
        assertEquals(testLocation,player.getLocation());
        player.performCommand("warp del test_warp");
    }

    @Test
    public void testWarpSetLocation(){
        Location testLocation = new Location(server.getWorld("world"),100,100,100);
        player.teleport(testLocation);
        player.performCommand("warp set test_warp");
        player.teleport(new Location(server.getWorld("world"),0,5,0));
        assertEquals(testLocation,plugin.getBlWarps().getWarps().get("test_warp"));
        player.performCommand("warp del test_warp");
    }

    @Test
    public void createHome(){
        player.performCommand("home set test_home");
        assertEquals(1,plugin.getBlHomes().getHomes(mockUUID).size());
        player.performCommand("home del test_home");
    }

    @Test
    public void deleteHome(){
        player.performCommand("home set test_home");
        player.performCommand("home del test_home");
        assertEquals(0,plugin.getBlHomes().getHomes(mockUUID).size());
    }

    @Test
    public void createMultipleHomes(){
        player.performCommand("home set test_home_1");
        player.performCommand("home set test_home_2");
        assertEquals(2,plugin.getBlHomes().getHomes(mockUUID).size());
        player.performCommand("home del test_home_1");
        player.performCommand("home del test_home_2");
    }

    @Test
    public void deleteMultipleHomes(){
        player.performCommand("home set test_home_1");
        player.performCommand("home set test_home_2");
        player.performCommand("home del test_home_1");
        player.performCommand("home del test_home_2");
        assertEquals(0,plugin.getBlHomes().getHomes(mockUUID).size());
    }

    @Test
    public void testHomeSetLocation(){
        Location testLocation = new Location(server.getWorld("world"),100,100,100);
        player.teleport(testLocation);
        player.performCommand("home set test_home");
        player.teleport(new Location(server.getWorld("world"),0,5,0));
        assertEquals(testLocation,plugin.getBlHomes().getHomes(mockUUID).get("test_home"));
        player.performCommand("home del test_home");
    }

    @Test
    public void testHomeTeleport(){
        Location testLocation = new Location(server.getWorld("world"),100,100,100);
        player.teleport(testLocation);
        player.performCommand("home set test_home");
        player.teleport(new Location(server.getWorld("world"),0,5,0));
        player.performCommand("home test_home");
        assertEquals(testLocation,player.getLocation());
        player.performCommand("home del test_home");
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }
}