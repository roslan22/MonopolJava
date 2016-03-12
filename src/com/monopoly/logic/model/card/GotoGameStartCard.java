package com.monopoly.logic.model.card;

import com.monopoly.logic.model.player.Player;

public class GotoGameStartCard extends SurpriseCard
{
    @Override
    public void perform(Player player)
    {
        moveToRoadStart(player);
    }
}
