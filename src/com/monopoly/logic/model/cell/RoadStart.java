package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

public class RoadStart extends Cell
{
    public static final int ROAD_START_MONEY = 400;

    @Override
    public void perform(Player player)
    {
        try
        {
            player.receiveMoneyFromBank(ROAD_START_MONEY);
        }
        catch (NoSuchMethodError e)
        {
            System.out.println("What");
        }
    }
}
