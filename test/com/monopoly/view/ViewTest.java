package com.monopoly.view;

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
    public void viewTest()
    {
        System.out.println(view.getHumanPlayersNumber());
        System.out.println(view.getComputerPlayersNumber());
    }
}
