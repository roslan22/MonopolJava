package com.monopoly.controller;

import com.monopoly.GameEvent;
import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.model.CubesResult;
import com.monopoly.logic.model.player.Player;
import com.monopoly.view.View;
import com.monopoly.view.PlayerBuyHouseDecision;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class Controller
{
    private static final String PATH = "";

    private View view;
    private Engine engine;
    private Map<Player, Integer> lastEventID = new HashMap<Player, Integer>();
    private List<GameEvent> events;
    
    public Controller(View view, Engine engine)
    {
        this.view = view;
        this.engine = engine;
        view.setPlayerBuyHouseDecision(new PlayerBuyHouseDecision() {
            @Override
            public void onAnswer(boolean answer) {
                onPlayerBuyHouseAnswer(answer);
            }
        });
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
        engine.initializeBoard(XmlMonopolyInitReader.getSettings(PATH)); //not compiling, have to implement
        engine.putPlayersAtFirstCell();
    }

    private void playTurn(Player player)
    {
        if (!engine.isPlayerInParking(player))
            movePlayer(player);
        else
            engine.exitPlayerFromParking(player);
        
        events = engine.getEvents(player.getPlayerID(), lastEventID.get(player));
        view.showEvents(events);
    }

    private void movePlayer(Player player)
    {
        CubesResult cr = engine.throwCubes();
        if (engine.isPlayerInJail(player)) //wrong logic of game in controller
        {
            if (cr.isDouble())
                engine.takePlayerOutOfJail(player);
            else
                return;
        }
        engine.movePlayer(player, cr.getResult());
    }
    
    private void onPlayerBuyHouseAnswer(boolean answer)
    {
        engine.onPlayerBuyHouseAnswer(answer);
    }

    private void createPlayers()
    {
        int humanPlayersNumber = view.getHumanPlayersNumber();
        int computerPlayersNumber = view.getComputerPlayersNumber();
        // TODO: Check that the sum of the players is 6
        List<String> humanPlayersNames = view.getHumanPlayerNames(humanPlayersNumber);
        engine.createPlayers(humanPlayersNames, computerPlayersNumber);
        addPlayersToLastEventId();
    }

    private void addPlayersToLastEventId() 
    {
         List<Player> players =  engine.getAllPlayers();
         for(Player player : players)
         {
            lastEventID.put(player, 0);
         }
    }
}
