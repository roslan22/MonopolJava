package com.monopoly.controller;

import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.engine.PlayerNameAlreadyExists;
import com.monopoly.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import com.monopoly.logic.events.Event;
import com.monopoly.logic.model.player.Player;
import com.monopoly.utils.Utils;
import com.monopoly.view.View;
import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller
{
    private static final String PATH = "";

    private View   view;
    private MonopolyEngine engine;
    private Map<Player, Integer> lastReceivedEventIds = new HashMap<Player, Integer>();
    private int lastEvent = 0;
    private Event[] events;
    
    public static String GAME_NAME = "Monopoly";
    public static final int MAXIMUM_GAME_PLAYERS = 6;
    public static int DUMMY_PLAYER_ID = 1;
    public static String XML_PATH = Utils.getAbsolutePath(
                      XmlMonopolyInitReader.class, "/com/monopoly/res/monopoly_config.xml");
    public Player currentPlayer = null;
    
    public Controller(View view, MonopolyEngine engine)
    {
        this.view = view;
        this.engine = engine;
        view.setPlayerBuyHouseDecision((int eventID, boolean answer) -> {
            buy(DUMMY_PLAYER_ID, eventID, answer); //TODO: IN EXERCISE 3 CHANGE TO  PLAYERID
        });
        view.setPlayerBuyAssetDecision((int eventID, boolean answer) -> {
            buy(DUMMY_PLAYER_ID, eventID, answer); //TODO: IN EXERCISE 3 CHANGE TO PLAYERID
        });
        
        view.setPlayerResign(() -> {
            resign(DUMMY_PLAYER_ID); //TODO: IN EXERCISE 3 CHANGE TO PLAYERID
        });
    }

    public void play()
    {
        engine.startGame();
        while (engine.isStillPlaying())
        {
            currentPlayer = engine.getCurrentPlayer(); 
            events = engine.getEvents(currentPlayer.getPlayerID(), lastEvent);
            if(events.length > 0)
            {
            lastEvent = events[events.length-1].getEventID();
            //NEXT TWO LINES FOR EX. 3
           // events = engine.getEvents(player.getPlayerID(), lastReceivedEventIds.get(player)); 
           // lastReceivedEventIds.replace(player, events[events.length-1].getEventID());
            view.showEvents(events);
            }
        }
        events = engine.getEvents(DUMMY_PLAYER_ID, lastEvent);
        view.showEvents(events);
    }
    
    public void initGame()
    {
        createPlayers();
        
        try
        {
            System.out.println(XML_PATH);
            engine.initializeBoard(new XmlMonopolyInitReader(XML_PATH)); 
            engine.putPlayersAtFirstCell();
        } catch (CouldNotReadMonopolyInitReader couldNotReadMonopolyInitReader)
        {
            couldNotReadMonopolyInitReader.printStackTrace();
        }
    }
    
    private void createPlayers()
    {
        int gamePlayersQuota = MAXIMUM_GAME_PLAYERS;
        int humanPlayersNumber = view.getHumanPlayersNumber(gamePlayersQuota);
        int computerPlayersNumber = view.getComputerPlayersNumber(gamePlayersQuota - humanPlayersNumber);
        
        List<String> humanPlayersNames = view.getHumanPlayerNames(humanPlayersNumber);
        engine.createGame(GAME_NAME, computerPlayersNumber, humanPlayersNumber);
        addHumanPlayersNames(humanPlayersNames);
        addPlayersToLastEventId();
    }

    private void addHumanPlayersNames(List<String> humanPlayersNames) {
        humanPlayersNames.forEach((playerName)->{
            try {
                engine.joinGame(GAME_NAME, playerName);
            } catch (PlayerNameAlreadyExists ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addPlayersToLastEventId() 
    {
         List<Player> players =  engine.getAllPlayers();
         for(Player player : players)
         {
            lastReceivedEventIds.put(player, 0);
         }
    }
    
    private void buy(int playerID, int eventID, boolean answer) 
    {
        engine.buy(playerID, eventID, answer);
    }

    private void resign(int playerID) 
    {
        if(currentPlayer != null)
        {
          engine.resign(currentPlayer.getPlayerID());
        }
    }
    
}
