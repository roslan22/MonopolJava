package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

import java.util.HashSet;
import java.util.Set;

public class Jail extends Cell
{
    Set<Player> playersInJail = new HashSet<>();

    @Override
    public void perform(Player player) {}

    public void putInJail(Player player)
    {
        player.setCurrentCellDoNotPerform(this);
        if (player.hasOutOfJailCard())
            player.returnOutOfJailCardToPack();
        else
            playersInJail.add(player);
    }

    @Override
    public boolean isInJail(Player player)
    {
        return playersInJail.contains(player);
    }

    @Override
    public void getPlayerOutOfJail(Player player)
    {
        if (playersInJail.contains(player))
        {
            playersInJail.remove(player);
        }
    }
}
