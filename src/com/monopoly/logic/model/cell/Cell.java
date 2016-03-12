package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;


public abstract class Cell 
{
    abstract public void perform(Player player);

    @Override
    public boolean equals(Object obj)
    {
        return this == obj;
    }

    public boolean isPlayerParking(Player player)
    {
        return false;
    }

    public boolean isInJail(Player player)
    {
        return false;
    }

    public void exitFromParking(Player player)
    {
    }

    public void getPlayerOutOfJail(Player player)
    {

    }
}
