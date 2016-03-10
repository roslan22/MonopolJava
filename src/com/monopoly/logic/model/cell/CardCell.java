package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.card.Card;
import com.monopoly.logic.model.player.Player;

public abstract class CardCell extends Cell
{    
    public abstract Card getCard();
    
    @Override
    public void perform(Player player)
    {
        getCard().perform(player);
    }
}
