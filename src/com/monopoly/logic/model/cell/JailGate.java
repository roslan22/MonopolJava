package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

public class JailGate extends Cell
{
    private Jail jailCell;

    public JailGate(Jail jailCell)
    {
        this.jailCell = jailCell;
    }

    @Override
    public void perform(Player player)
    {
        jailCell.putInJail(player);
    }
}
