package com.monopoly.logic.model.card;

import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.Player;

public class OutOfJailCard extends SurpriseCard
{
    private Board board;

    public OutOfJailCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board)
    {
        this.board.removeCardFromSurprisePack(this);
        player.receiveOutOfJailCard(this);
    }

    public void returnToPack()
    {
        if (board == null)
            return;
        board.returnCardToSurprisePack(this);
        board = null;
    }
}
