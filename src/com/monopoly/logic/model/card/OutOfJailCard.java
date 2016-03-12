package com.monopoly.logic.model.card;

import com.monopoly.logic.model.player.Player;

public class OutOfJailCard extends SurpriseCard
{
    @Override
    public void perform(Player player)
    {
        removeCardFromSurprisePack(this);
        player.receiveOutOfJailCard(this);
    }

    public void returnToPack()
    {
        returnCardToSurprisePack(this);
    }
}
