package com.monopoly.logic.model.player;

import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.model.cell.City;
import com.monopoly.logic.model.cell.Property;

public class HumanPlayer extends Player
{

    public HumanPlayer(String name, int playerId, MonopolyEngine engine)
    {
        super(name, playerId, engine);
    }

    @Override
    public void askToBuyProperty(Property property)
    {
        engine.askToBuyProperty(this,
                                property.getPropertyGroup().getName(),
                                property.getName(),
                                property.getPrice(),
                                buyDecision -> {
                                    if (buyDecision)
                                    {
                                        engine.addAssetBoughtEvent(this, property.getName());
                                        property.buyProperty(HumanPlayer.this);
                                    }
                                });
    }

    @Override
    public void askToBuyHouse(City city)
    {
        engine.askToBuyHouse(this, city.getName(), city.getHousePrice(), buyDecision -> {
            if (buyDecision)
            {
                engine.addHouseBoughtEvent(this, city.getName());
                city.buyHouse(HumanPlayer.this);
            }
        });
    }
}
