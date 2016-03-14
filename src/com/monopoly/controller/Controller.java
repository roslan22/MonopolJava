package com.monopoly.controller;

import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.model.CubesResult;
import com.monopoly.logic.model.player.Player;
import com.monopoly.view.View;

import java.util.List;

public class Controller
{
    private static final String PATH = "";

    private View view;
    private Engine engine;

    public Controller(View view, Engine engine)
    {
        this.view = view;
        this.engine = engine;
    }

    public void play()
    {
        while (engine.isStillPlaying())
        {
            Player player = engine.getCurrentPlayer();
            playTurn(player);
            engine.nextPlayer();
        }
    }

    public void initGame()
    {
        createPlayers();
        engine.initializeBoard(XmlMonopolyInitReader.getSettings(PATH));
        engine.putPlayersAtFirstCell();
    }

    private void playTurn(Player player)
    {
        if (!engine.isPlayerInParking(player))
            movePlayer(player);
        else
            engine.exitPlayerFromParking(player);
    }

    private void movePlayer(Player player)
    {
        CubesResult cr = engine.throwCubes();
        if (engine.isPlayerInJail(player))
        {
            if (cr.isDouble())
                engine.takePlayerOutOfJail(player);
            else
                return;
        }
        engine.movePlayer(player, cr.getResult());
    }

    private void createPlayers()
    {
        int humanPlayersNumber = view.getHumanPlayersNumber();
        int computerPlayersNumber = view.getComputerPlayersNumber();
        // TODO: Check that the sum of the players is 6
        List<String> humanPlayersNames = view.getHumanPlayerNames(humanPlayersNumber);
        engine.createPlayers(humanPlayersNames, computerPlayersNumber);
    }
}
