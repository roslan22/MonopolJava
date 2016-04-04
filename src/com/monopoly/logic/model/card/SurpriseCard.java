package com.monopoly.logic.model.card;

import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.Player;

public abstract class SurpriseCard extends Card
{

    public SurpriseCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board)
    {
        board.addSurpriseCardEvent(player, this.getCardText());
    }
}
