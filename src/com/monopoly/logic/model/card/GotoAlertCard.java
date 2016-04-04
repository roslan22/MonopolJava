package com.monopoly.logic.model.card;

import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.Player;

public class GotoAlertCard extends AlertCard
{
    public GotoAlertCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board) 
    {
        super.perform(player, board);
         board.moveToNextAlertCard(player);
    }
}
