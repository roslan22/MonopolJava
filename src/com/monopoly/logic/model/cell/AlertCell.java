package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.Card;
import com.monopoly.logic.model.card.CardPack;

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
}
