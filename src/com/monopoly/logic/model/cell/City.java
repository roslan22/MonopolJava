package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

public class City extends Property
{
    private static final int MAX_HOUSES_AVAILABLE = 3;
    
    private int houseCounter = 0;
    private int housePrice;

    @Override
    public void perform(Player player) 
    {
        if (canBuyHouse(player)) 
        {
            buyHouse(player);
        }
        else
        {
            super.perform(player);
        }
    }

    private boolean canBuyHouse(Player player) 
    {
        return getOwner().equals(player) && hasMonoply(player);
    }

    private void buyHouse(Player player) {
        if (player.getMoney() >= housePrice && houseCounter < MAX_HOUSES_AVAILABLE)
        {
            if (offerToBuyHouse(player))
            {
                
                houseCounter++;
            }
        }
    }

    @Override
    public int getRentPrice() 
    {
        
    }
}
