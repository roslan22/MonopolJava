package com.monopoly.logic.engine;


import com.monopoly.controller.XmlMonopolyInitReader;
import com.monopoly.controller.XmlMonopolyInitReaderTest;
import static com.monopoly.controller.XmlMonopolyInitReaderTest.systemFilePath;
import com.monopoly.logic.events.EventType;
import com.monopoly.logic.model.CubesResult;
import com.monopoly.logic.model.player.ComputerPlayer;
import com.monopoly.logic.model.player.Player;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class MonopolyEngineTest
{
    public static final  int    HUMAN_PLAYERS_COUNT               = 3;
    public static final  int    COMPUTER_PLAYERS_COUNT            = 3;
    public static final  int    MAX_CUBE_RESULT                   = 6;
    public static final  int    MIN_CUBE_RESULT                   = 1;
    public static final  String GAME_NAME                         = "GameName";
    public static final  String PLAYER_NAME_1                     = "PlayerName1";
    private static final String PLAYER_NAME_2                     = "PlayerName2";
    private static final String PLAYER_NAME_3                     = "PlayerName3";
    public static final  int    CUBE_THROWS_FOR_DISTRIBUTION_TEST = 100000;
    public String systemFilePath;

    private MonopolyEngine engine;


    @Before
    public void setUp() throws Exception
    {
        engine = new MonopolyEngine();
        systemFilePath = new File("").getAbsolutePath(); 
        engine.initializeBoard(new XmlMonopolyInitReader(systemFilePath + XmlMonopolyInitReaderTest.VALID_XML_RELAVITE_PATH));
        engine.createGame(GAME_NAME, COMPUTER_PLAYERS_COUNT, HUMAN_PLAYERS_COUNT);
    }

    @Test
    public void testInitializeBoard_shouldNotThrowException() throws Exception
    {
        engine.initializeBoard(new XmlMonopolyInitReader(systemFilePath + XmlMonopolyInitReaderTest.VALID_XML_RELAVITE_PATH));
    }

    @Test
    public void testCreateGameComputerPlayersCreated() throws Exception
    {
        long computerPlayersCount = engine.getAllPlayers().stream().filter(p -> p instanceof ComputerPlayer).count();
        assertEquals(computerPlayersCount, COMPUTER_PLAYERS_COUNT);
    }

    @Test
    public void testCreateGameComputerPlayersWithUniqueNames() throws Exception
    {
        Set<Player> s = new HashSet<>(engine.getAllPlayers());
        assertTrue("All the names should be unique", s.size() == engine.getAllPlayers().size());
    }

    @Test
    public void testCreateGame_shouldNotStartTheGame() throws Exception
    {
        MonopolyEngine engine = new MonopolyEngine();
        engine.createGame("GameName", COMPUTER_PLAYERS_COUNT, HUMAN_PLAYERS_COUNT);
        assertTrue("Game shouldn't start while not all of the human players has joined the game",
                   Arrays.stream(engine.getEvents(1, 0)).noneMatch(e -> e.getEventType() == EventType.GAME_START));
    }

    @Test
    public void testCreateGameJoinPlayer() throws Exception
    {
        engine.joinGame(GAME_NAME, PLAYER_NAME_1);
        assertEquals(engine.getAllPlayers().size(), COMPUTER_PLAYERS_COUNT + 1);
        assertTrue(engine.getAllPlayers().stream().anyMatch(p -> p.getName().equals(PLAYER_NAME_1)));
    }

    @Test(expected = PlayerNameAlreadyExists.class)
    public void testCreateGameWithTwoPlayersWithSameName() throws Exception
    {
        engine.joinGame(GAME_NAME, PLAYER_NAME_1);
        engine.joinGame(GAME_NAME, PLAYER_NAME_1);
    }

    @Test
    public void testCreateGameAllPlayersJoined() throws Exception
    {
        addAllHumanPlayers();
        assertTrue("Game should start when all of the human players has joined the game",
                   Arrays.stream(engine.getEvents(1, 0)).anyMatch(e -> e.getEventType() == EventType.GAME_START));
    }

    private void addAllHumanPlayers() throws PlayerNameAlreadyExists
    {
        engine.joinGame(GAME_NAME, PLAYER_NAME_1);
        engine.joinGame(GAME_NAME, PLAYER_NAME_2);
        engine.joinGame(GAME_NAME, PLAYER_NAME_3);
    }

    @Test
    public void testFirstCubeResult() throws Exception
    {
        addAllHumanPlayers();
        assertTrue("Should throw cubes after all the players joined the game",
                   Arrays.stream(engine.getEvents(1, 0)).anyMatch(p -> p.getEventType() == EventType.DICE_ROLL));
    }

    @Test
    public void testThrowCubes() throws Exception
    {
        addAllHumanPlayers();
        Map<Integer, Integer> cubeNumberToResultCount = new HashMap<>();
        IntStream.range(0, CUBE_THROWS_FOR_DISTRIBUTION_TEST).forEach(i -> incrementCubeResult(cubeNumberToResultCount));
        testCubeResultDistribute(cubeNumberToResultCount);
    }

    private void incrementCubeResult(Map<Integer, Integer> cubeNumberToResultCount)
    {
        CubesResult result = engine.throwCubes();
        testCubeResultAddToCounter(result.getFirstCubeResult());
        cubeNumberToResultCount.put(result.getFirstCubeResult(), cubeNumberToResultCount.getOrDefault(result, 0));
        testCubeResultAddToCounter(result.getSecondCuberResult());
        cubeNumberToResultCount.put(result.getSecondCuberResult(), cubeNumberToResultCount.getOrDefault(result, 0));
    }

    private void testCubeResultDistribute(Map<Integer, Integer> cubeNumberToResultCount)
    {
        int minValue = Collections.min(cubeNumberToResultCount.values());
        int maxValue = Collections.max(cubeNumberToResultCount.values());
        int maximumDelta = 7000;
        assertNotEquals(maximumDelta, maxValue - minValue);
    }

    private void testCubeResultAddToCounter(int cubeResult)
    {
        assertFalse("Cubes result " + cubeResult + " is too high", cubeResult > MAX_CUBE_RESULT);
        assertFalse("Cubes result " + cubeResult + " is too small", cubeResult < MIN_CUBE_RESULT);
    }
}