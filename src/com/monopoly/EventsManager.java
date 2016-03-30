package com.monopoly;

import java.util.List;

public class EventsManager {
    private List<GameEvent> events;
    private int lastEventID = 0;
    
    public void addEvent(GameEvent event)
    {
        events.add(event);
        lastEventID++;
    }
    
    public int getLastEventID() {
        return lastEventID;
    }
    
    public List<GameEvent> getEvents() {
        return events;
    }
    
    public int getNewEventId() {
        return lastEventID + 1;
    }
}
