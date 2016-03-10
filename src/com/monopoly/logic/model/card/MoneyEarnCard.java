package com.monopoly.logic.model.card;

import com.monopoly.logic.model.player.Player;

public class MoneyEarnCard extends SurpriseCard
{ 
    private boolean isFromOtherPlayers;
    private int moneyEarned;
            
    @Override
    public void perform(Player player) 
    {
        if (isFromOtherPlayers)
        {
            transferOtherPlayersMoneyTo(player);
        }
        else
        {
            player.reciveMoney(moneyEarned);
        }
    }
    
}
