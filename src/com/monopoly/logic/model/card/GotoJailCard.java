package com.monopoly.logic.model.card;

import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.Player;

public class GotoJailCard extends AlertCard
{
    public GotoJailCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board)
    {
        board.moveToJail(player);
        //TODO: board.boardChangedNotifier
    }
}
