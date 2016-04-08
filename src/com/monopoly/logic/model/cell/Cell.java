package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;


public abstract class Cell 
{
    abstract public void perform(Player player);

    public boolean isPlayerParking(Player player)
    {
        return false;
    }

    public boolean isInJail(Player player)
    {
        return false;
    }

    public CellType getType()
    {
        return CellType.DEFAULT;
    }

    public String getCellName()
    {
        return this.getClass().getSimpleName();
    }
}
