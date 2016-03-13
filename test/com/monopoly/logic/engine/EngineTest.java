package com.monopoly.logic.engine;


import com.monopoly.logic.model.player.Player;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EngineTest
{
    public static final int COMPUTER_PLAYERS_COUNT = 3;

    private Engine engine;
    private List<String> humanNames;

    @Before
    public void setUp() throws Exception
    {
        engine = new Engine();
        humanNames = Arrays.asList("Name1", "Name2", "Name3");
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
        Engine e = createEngineWithDuplicatePlayersNames();
        List<String> createdPlayersNames = e.getAllPlayers().stream().map(Player::getName).collect(Collectors.toList());
        Set<String> noDuplicatesNames = new HashSet<>();
        noDuplicatesNames.addAll(createdPlayersNames);

        assertEquals(noDuplicatesNames.size(), createdPlayersNames.size());
    }

    private Engine createEngineWithDuplicatePlayersNames()
    {
        Engine e = new Engine();
        e.createPlayers(Arrays.asList("Name", "Name", "Name2"), COMPUTER_PLAYERS_COUNT);
        return e;
    }
}