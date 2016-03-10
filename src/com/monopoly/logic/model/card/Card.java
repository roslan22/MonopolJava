package com.monopoly.logic.model.card;

import com.monopoly.logic.model.player.Player;

public abstract class Card 
{
    private String cardText;

    public String getCardText() 
    {
        return cardText;
    }
    
    public abstract void perform(Player player);
}
