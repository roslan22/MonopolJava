package com.monopoly.logic.model.card;

import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.Player;

public abstract class AlertCard extends Card
{
    public AlertCard(String cardText)
    {
        super(cardText);
    }

    @Override
    public void perform(Player player, Board board)
    {
        board.addAlertCardEvent(player, this.getCardText());
    }
}
