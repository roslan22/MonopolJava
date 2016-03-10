package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.card.Card;

public class SurpriseCell extends CardCell
{
    @Override
    public Card getCard() 
    {
        return getSurpriseCardPack().getNext();
    }
}
