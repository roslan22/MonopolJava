package com.monopoly.logic.model.card;

import com.monopoly.logic.model.Board;
import com.monopoly.logic.model.player.Player;

public class GotoJailCard extends AlertCard
{
    @Override
    public void perform(Player player, Board board)
    {
        board.moveToJail(player);
        //TODO: board.boardChangedNotifier
    }
}
