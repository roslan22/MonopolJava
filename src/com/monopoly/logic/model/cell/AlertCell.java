package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.Card;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.player.Player;

public class AlertCell extends CardCell
{
    private CardPack<AlertCard> alertCardPack;

    public AlertCell(CardPack<AlertCard> alertCardPack)
    {
        this.alertCardPack = alertCardPack;
    }

    @Override
    public Card getCard() 
    {
        return alertCardPack.getNext();
    }

    @Override
    public void perform(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
