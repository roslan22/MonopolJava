package com.monopoly.logic.model.card;

import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.Player;

public class MoneyEarnCard extends SurpriseCard
{ 
    private boolean isFromOtherPlayers;
    private int     amount;

    public MoneyEarnCard(String text, int amount, boolean isFromOtherPlayers)
    {
        super(text);
        this.isFromOtherPlayers = isFromOtherPlayers;
        this.amount = amount;
    }

    @Override
    public void perform(Player player, Board board)
    {
        super.perform(player, board);
        if (isFromOtherPlayers)
        {
            board.transferOtherPlayersMoneyTo(player, amount);
        }
        else
        {
            player.receiveMoneyFromBank(amount);
        }
    }
    
}
