package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.Board;
import com.monopoly.logic.model.card.Card;
import com.monopoly.logic.model.player.Player;

public abstract class CardCell extends Cell
{   
    private Board board;
    public abstract Card getCard();
    
    @Override
    public void perform(Player player)
    {
        getCard().perform(player, board);
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
