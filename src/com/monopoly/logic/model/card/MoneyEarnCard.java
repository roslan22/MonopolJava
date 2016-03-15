package com.monopoly.logic.model.card;

import com.monopoly.logic.model.Board;
import com.monopoly.logic.model.player.Player;

public class MoneyEarnCard extends SurpriseCard
{ 
    private boolean isFromOtherPlayers;
    private int     amount;

    public MoneyEarnCard(boolean isFromOtherPlayers, int amount)
    {
        this.isFromOtherPlayers = isFromOtherPlayers;
        this.amount = amount;
    }

    @Override
    public void perform(Player player, Board board)
    {
        if (isFromOtherPlayers)
        {
            board.transferOtherPlayersMoneyTo(player, amount);
        }
        else
        {
            player.receiveMoney(amount);
        }
    }
    
}
