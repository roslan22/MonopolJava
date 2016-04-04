package com.monopoly.logic.model.card;

import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.Player;

public class MoneyPenaltyCard extends AlertCard
{
    private boolean isToOtherPlayers;
    private int moneyPenalty;

    public MoneyPenaltyCard(String text, int moneyPenalty, boolean isToOtherPlayers)
    {
        super(text);
        this.isToOtherPlayers = isToOtherPlayers;
        this.moneyPenalty = moneyPenalty;
    }

    @Override
    public void perform(Player player, Board board)
    {
        super.perform(player, board);
        if (isToOtherPlayers)
        {
            board.payToEveryoneElse(player, moneyPenalty);
        }
        else
        {
            player.payToBank(moneyPenalty);
        }
    }
}
