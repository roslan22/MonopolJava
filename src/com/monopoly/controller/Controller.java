package com.monopoly.controller;

import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import com.monopoly.logic.events.Event;
import com.monopoly.utils.Utils;
import com.monopoly.view.View;

import java.util.List;

public class Controller
{
    private View   view;
    private Engine engine;
    private int lastEvent = 0;

    public static       String GAME_NAME            = "Monopoly";
    public static final int    MAXIMUM_GAME_PLAYERS = 6;
    public static       int    DUMMY_PLAYER_ID      = 1;
    public static       String XML_PATH             = Utils
            .getAbsolutePath(XmlMonopolyInitReader.class, "/com/monopoly/res/monopoly_config.xml");

    public Controller(View view, MonopolyEngine engine)
    {
        this.view = view;
        this.engine = engine;
        view.setPlayerBuyHouseDecision((eventID, answer) -> buy(DUMMY_PLAYER_ID, eventID, answer));
        view.setPlayerBuyAssetDecision((eventID, answer) -> buy(DUMMY_PLAYER_ID, eventID, answer));
        view.setPlayerResign(() -> resign(DUMMY_PLAYER_ID));
    }

    public void play()
    {
        initGame();
        Event[] events = engine.getEvents(DUMMY_PLAYER_ID, lastEvent);
        while (events.length != 0)
        {
            lastEvent = events[events.length - 1].getEventID();
            //NEXT TWO LINES FOR EX. 3
            // events = engine.getEvents(player.getPlayerID(), lastReceivedEventIds.get(player));
            // lastReceivedEventIds.replace(player, events[events.length-1].getEventID());
            view.showEvents(events);
            events = engine.getEvents(DUMMY_PLAYER_ID, lastEvent);
        }
    }

    private void initGame()
    {
        initBoard();
        createPlayers();
    }

    private void initBoard()
    {
        try
        {
            System.out.println(XML_PATH);
            engine.initializeBoard(new XmlMonopolyInitReader(XML_PATH));
        } catch (CouldNotReadMonopolyInitReader couldNotReadMonopolyInitReader)
        {
            System.out.println(couldNotReadMonopolyInitReader.getMessage());
            initBoard();
        }
    }

    private void createPlayers()
    {
        int humanPlayersNumber = view.getHumanPlayersNumber(MAXIMUM_GAME_PLAYERS);
        int computerPlayersNumber = view.getComputerPlayersNumber(MAXIMUM_GAME_PLAYERS - humanPlayersNumber);

        engine.createGame(GAME_NAME, computerPlayersNumber, humanPlayersNumber);
        addHumanPlayersNames(view.getDistinctHumanPlayerNames(humanPlayersNumber));
    }

    private void addHumanPlayersNames(List<String> humanPlayersNames)
    {
        humanPlayersNames.forEach(p -> engine.joinGame(GAME_NAME, p));
    }

    private void buy(int playerID, int eventID, boolean answer)
    {
        engine.buy(playerID, eventID, answer);
    }

    private void resign(int playerID)
    {
        engine.resign(playerID);
    }

}
