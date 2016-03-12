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

    public void startGame()
    {
        createPlayers();
        engine.initialize(XmlMonopolyInitReader.getSettings(PATH));
        
        while (engine.isStillPlaying())
        {
            Player player = engine.getCurrentPlayer();
            
            CubesResult cr = engine.throwCubes();
            engine.movePlayer(player, cr.getResult());
            
            engine.nextPlayer();
        }
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
