package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

import java.util.HashSet;
import java.util.Set;

public class Parking extends Cell
{
    Set<Player> parkingPlayers = new HashSet<>();

    @Override
    public void perform(Player player)
    {
        parkingPlayers.add(player);
    }

    @Override
    public boolean isPlayerParking(Player player)
    {
        if (parkingPlayers.contains(player))
        {
            return false;
        }
        return true;
    }

    @Override
    public void exitFromParking(Player player)
    {
        parkingPlayers.remove(player);
    }
}
