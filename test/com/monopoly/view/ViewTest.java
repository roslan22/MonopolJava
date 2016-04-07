package com.monopoly.view;

import com.monopoly.logic.events.Event;
import com.monopoly.logic.events.EventType;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ViewTest
{
    private View view;

    @Before
    public void setUp() throws Exception
    {
        view = new View();
    }
    
    @Test
    public void testGameOverGameStartMsg() throws Exception
    {
      List<Event> eventsList = new ArrayList<>();
      eventsList.add(new Event(100, EventType.GAME_OVER));
      eventsList.add(new Event(100, EventType.GAME_START));
      
      view.showEvents(eventsList.toArray(new Event[2]));
      //assertTrue("", );

    }
}
