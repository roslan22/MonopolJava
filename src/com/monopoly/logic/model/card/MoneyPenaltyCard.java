package com.monopoly.logic.model.card;

import com.monopoly.logic.model.player.Player;

public class MoneyPenaltyCard extends AlertCard
{
    private boolean isToOtherPlayers;
    private int moneyPenalty;

    public MoneyPenaltyCard(boolean isToOtherPlayers, int moneyPenalty)
    {
        this.isToOtherPlayers = isToOtherPlayers;
        this.moneyPenalty = moneyPenalty;
    }

    @Override
    public void perform(Player player)
    {
        if (isToOtherPlayers)
        {
            payToEveryoneElse(player, moneyPenalty);
        }
        else
        {
            player.payToBank(moneyPenalty);
        }
    }
}
