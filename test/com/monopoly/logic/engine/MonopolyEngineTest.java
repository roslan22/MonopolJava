package com.monopoly.logic.engine;


import com.monopoly.controller.XmlMonopolyInitReader;
import com.monopoly.controller.XmlMonopolyInitReaderTest;
import com.monopoly.logic.model.player.Player;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class MonopolyEngineTest
{
    public static final int COMPUTER_PLAYERS_COUNT = 3;
    public static final int MAX_CUBE_RESULT        = 6;
    public static final int MIN_CUBE_RESULT        = 1;

    private MonopolyEngine engine;
    private List<String>   humanNames;

    @Before
    public void setUp() throws Exception
    {
        engine = new MonopolyEngine();
        humanNames =   Arrays.asList("Name1", "Name2", "Name3");
        engine.createPlayers(humanNames, COMPUTER_PLAYERS_COUNT);
    }

    @Test
    public void testCreatePlayersAmount_shouldCreateRightAmountOfPlayers() throws Exception
    {
        List<Player> createdPlayers = engine.getAllPlayers();
        assertEquals(createdPlayers.size(), humanNames.size() + COMPUTER_PLAYERS_COUNT);
    }

    @Test
    public void testCreatePlayersNames_shouldContainAllThePlayerNames() throws Exception
    {
        List<String> createdPlayersNames = engine.getAllPlayers().stream().map(Player::getName).collect(Collectors.toList());
        for (String humanName : humanNames)
        {
            assertTrue(createdPlayersNames.contains(humanName));
        }
    }

    @Test
    public void testCreatePlayersUnique_allTheNamesShouldBeUnique() throws Exception
    {
        MonopolyEngine e = createEngineWithDuplicatePlayersNames();
        List<String> createdPlayersNames = e.getAllPlayers().stream().map(Player::getName).collect(Collectors.toList());
        Set<String> noDuplicatesNames = new HashSet<>();
        noDuplicatesNames.addAll(createdPlayersNames);

        assertEquals(noDuplicatesNames.size(), createdPlayersNames.size());
    }

    private MonopolyEngine createEngineWithDuplicatePlayersNames()
    {
        MonopolyEngine e = new MonopolyEngine();
        e.createPlayers(Arrays.asList("Name", "Name", "Name2"), COMPUTER_PLAYERS_COUNT);
        return e;
    }


    @Test
    public void testGetCurrentPlayer_shouldNotThrowException() throws Exception
    {
        MonopolyEngine e = new MonopolyEngine();
        e.createPlayers(humanNames, 2);
        e.getCurrentPlayer();
    }

    @Test
    public void testInitializeBoard_shouldNotThrowException() throws Exception
    {
        engine.initializeBoard(new XmlMonopolyInitReader(XmlMonopolyInitReaderTest.VALID_XML_FILE_PATH));
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
        cubeNumberToResultCount.put(1,0);
        cubeNumberToResultCount.put(2,0);
        cubeNumberToResultCount.put(3,0);
        cubeNumberToResultCount.put(4,0);
        cubeNumberToResultCount.put(5,0);
        cubeNumberToResultCount.put(6,0);
        return cubeNumberToResultCount;
    }

    private void testCubeResultAddToCounter(Map<Integer, Integer> cubeNumberToResultCount, int cubeResult)
    {
        assertFalse("Cubes result is too high" ,cubeResult > MAX_CUBE_RESULT);
        assertFalse("Cubes result is too small", cubeResult < MIN_CUBE_RESULT);
        cubeNumberToResultCount.put(cubeResult, cubeNumberToResultCount.get(cubeResult) + 1);
    }
}