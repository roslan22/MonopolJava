package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.card.Card;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;

public class SurpriseCell extends CardCell
{
    private CardPack<SurpriseCard> surpriseCardPack;

    public SurpriseCell(CardPack<SurpriseCard> surpriseCardPack)
    {
        this.surpriseCardPack = surpriseCardPack;
    }

    @Override
    public Card getCard() 
    {
        return surpriseCardPack.getNext();
    }

    @Override
    public CellType getType()
    {
        return CellType.SURPRISE_CELL;
    }
}
