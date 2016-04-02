package com.monopoly.logic.events;

import com.monopoly.logic.model.CubesResult;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class EventList
{
    private int         eventIdSequence = -1;
    private List<Event> events          = new ArrayList<>();

    public List<Event> getEventsClone()
    {
        return new ArrayList<>(events);
    }

    public int getLastEventID()
    {
        return getAndIncrementNextEventID() - 1;
    }

    public void addThrowCubesEvent(Player player, CubesResult cubesResult)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.DICE_ROLL)
                .setPlayerName(player.getName()).setEventMessage(player.getName() + " throws:" + cubesResult.toString())
                .setFirstDiceResult(cubesResult.getFirstCubeResult()).setSecondDiceResult(cubesResult.getSecondCuberResult())
                .createGameEvent();
        events.add(e);
    }

    private int getAndIncrementNextEventID()
    {
        eventIdSequence++;
        return eventIdSequence;
    }

    public int size()
    {
        return events.size();
    }
}
