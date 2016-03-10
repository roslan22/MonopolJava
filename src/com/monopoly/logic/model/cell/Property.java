package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

public abstract class Property extends Cell
{
    private Player owner;
    private int price;
    
    public abstract int getRentPrice();
    
    public boolean isPropertyAvailable()
    {
        return owner == null;
    }

    public Player getOwner() 
    {
        return owner;
    }

    public void setOwner(Player owner) 
    {
        this.owner = owner;
    }

    public int getPrice() 
    {
        return price;
    }

    public void setPrice(int price) 
    {
        this.price = price;
    }
    
    @Override
    public void perform(Player player) {
        if (isPropertyAvailable()) 
        {
            buyProperty(player);
        } 
        else 
        {
            player.payToOtherPlayer(getOwner(), getRentPrice());
        }
    }

    private void buyProperty(Player player) 
    {
        if (player.getMoney() >= getPrice() && offerToBuyProperty(player))
        {
            
            setOwner(player);
        }
    }
}
