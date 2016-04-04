package com.monopoly.logic.model.player;

import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.model.cell.City;
import com.monopoly.logic.model.cell.Property;

public class ComputerPlayer extends Player
{
    private static int computerNameCount = 0;
    
    private static String getNextName()
    {
        computerNameCount++;
        return "Computer" + String.valueOf(computerNameCount);
    }
    
    public ComputerPlayer(int playerId, MonopolyEngine engine)
    {
        super(getNextName(), playerId, engine);
    }

    @Override
    public void askToBuyProperty(Property property)
    {
        engine.addAssetBoughtEvent(this, property.getName());
        property.buyProperty(this);
    }

    @Override
    public void askToBuyHouse(City city)
    {
        engine.addHouseBoughtEvent(this, city.getName());
        city.buyHouse(this);
    }
}
