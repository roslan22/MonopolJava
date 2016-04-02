package com.monopoly.logic.engine;

import com.monopoly.logic.events.Event;

public interface Engine
{
    void createGame(String gameName, int computerPlayers, int humanPlayers);
    int joinGame (String gameName, String playerName);

    Event[] getEvents(int playerID, int eventID);
    void buy(int playerID, int eventID, boolean buy);
    void resign(int playerID);
}
