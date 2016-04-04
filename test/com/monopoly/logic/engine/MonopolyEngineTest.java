package com.monopoly.logic.engine;


import com.monopoly.controller.XmlMonopolyInitReader;
import com.monopoly.controller.XmlMonopolyInitReaderTest;
import com.monopoly.logic.events.EventType;
import com.monopoly.logic.model.player.ComputerPlayer;
import com.monopoly.logic.model.player.Player;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class MonopolyEngineTest
{
    public static final  int    HUMAN_PLAYERS_COUNT    = 3;
    public static final  int    COMPUTER_PLAYERS_COUNT = 3;
    public static final  int    MAX_CUBE_RESULT        = 6;
    public static final  int    MIN_CUBE_RESULT        = 1;
    public static final  String GAME_NAME              = "GameName";
    public static final  String PLAYER_NAME_1          = "PlayerName1";
    private static final String PLAYER_NAME_2          = "PlayerName2";
    private static final String PLAYER_NAME_3          = "PlayerName3";

    private MonopolyEngine engine;


    @Before
    public void setUp() throws Exception
    {
        engine = new MonopolyEngine();
        engine.initializeBoard(new XmlMonopolyInitReader(XmlMonopolyInitReaderTest.VALID_XML_FILE_PATH));
        engine.createGame(GAME_NAME, COMPUTER_PLAYERS_COUNT, HUMAN_PLAYERS_COUNT);
    }

    @Test
    public void testInitializeBoard_shouldNotThrowException() throws Exception
    {
        engine.initializeBoard(new XmlMonopolyInitReader(XmlMonopolyInitReaderTest.VALID_XML_FILE_PATH));
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

    }

    private void testCubeResultDistribute(Map<Integer, Integer> cubeNumberToResultCount)
    {
        int minValue = Collections.min(cubeNumberToResultCount.values());
        int maxValue = Collections.max(cubeNumberToResultCount.values());
        int maximumDelta = 7000;
        assertNotEquals(maximumDelta, maxValue - minValue);
    }

    private Map<Integer, Integer> addAvailableCubeResults(Map<Integer, Integer> cubeNumberToResultCount)
    {
        cubeNumberToResultCount.put(1, 0);
        cubeNumberToResultCount.put(2, 0);
        cubeNumberToResultCount.put(3, 0);
        cubeNumberToResultCount.put(4, 0);
        cubeNumberToResultCount.put(5, 0);
        cubeNumberToResultCount.put(6, 0);
        return cubeNumberToResultCount;
    }

    private void testCubeResultAddToCounter(Map<Integer, Integer> cubeNumberToResultCount, int cubeResult)
    {
        assertFalse("Cubes result is too high", cubeResult > MAX_CUBE_RESULT);
        assertFalse("Cubes result is too small", cubeResult < MIN_CUBE_RESULT);
        cubeNumberToResultCount.put(cubeResult, cubeNumberToResultCount.get(cubeResult) + 1);
    }
}