package com.monopoly.logic.model.card;

import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.Player;

public abstract class Card
{
    private String cardText;

    public Card(String cardText)
    {
        this.cardText = cardText;
    }

    public String getCardText()
    {
        return cardText;
    }

    public abstract void perform(Player player, Board board);

    @Override
    public boolean equals(Object o)
    {
        return this == o;
    }

    @Override
    public int hashCode()
    {
        return getCardText() != null ? getCardText().hashCode() : 0;
    }
}
