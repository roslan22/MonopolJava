package com.monopoly.main;

import com.monopoly.controller.Controller;
import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.view.View;


public class Main
{
    public static void main(String[] args)
    {
        playMonopoly();
    }

    private static void playMonopoly()
    {
        try
        {
            startController();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            playMonopoly();
        }
    }

    private static void startController()
    {
        Controller controller = new Controller(new View(), new MonopolyEngine());
        controller.play();
    }
}
