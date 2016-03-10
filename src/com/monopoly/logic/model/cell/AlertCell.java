package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.card.Card;

public class AlertCell extends CardCell
{

    @Override
    public Card getCard() 
    {
        return getAlertCardPack().getNext();
    }
}
