package com.monopoly.logic.model.card;

import com.monopoly.logic.model.Board;
import com.monopoly.logic.model.player.Player;

public class MoneyEarnCard extends SurpriseCard
{ 
    private boolean isFromOtherPlayers;
    private int moneyEarned;

    public MoneyEarnCard(boolean isFromOtherPlayers, int moneyEarned)
    {
        this.isFromOtherPlayers = isFromOtherPlayers;
        this.moneyEarned = moneyEarned;
    }

    @Override
    public void perform(Player player, Board board)
    {
        if (isFromOtherPlayers)
        {
            board.transferOtherPlayersMoneyTo(player, moneyEarned);
        }
        else
        {
            player.receiveMoney(moneyEarned);
        }
    }
    
}
