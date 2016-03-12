package com.monopoly.logic.model.player;

import java.util.Random;


public class ComputerPlayer extends Player
{
    private static int computerNameCount = 0;
    
    private static String getNextName()
    {
        computerNameCount++;
        return "Computer" + String.valueOf(computerNameCount);
    }
    
    public ComputerPlayer()
    {
        super(getNextName());
    }

    @Override
    public boolean isWillingToBuyProperty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isWillingToBuyHouse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
