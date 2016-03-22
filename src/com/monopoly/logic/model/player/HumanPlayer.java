package com.monopoly.logic.model.player;

public class HumanPlayer extends Player
{
    public HumanPlayer(String name)
    {
        super(name);
    }

    @Override
    public boolean isWillingToBuyProperty()
    {
        return false;
    }

    @Override
    public boolean isWillingToBuyHouse()
    {
        return false;
    }

    @Override
    public PlayerType getPlayerType() {
        return Player.PlayerType.HUMAN;
    }
        


}
