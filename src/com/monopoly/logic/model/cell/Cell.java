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
}
