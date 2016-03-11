package com.monopoly.logic.model.player;

public abstract class Player
{
    private static final int START_MONEY_AMOUNT = 1500;
    
    private String name;
    private int money = START_MONEY_AMOUNT;

    public Player(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getMoney() 
    {
        return money;
    }

    public void setMoney(int money) 
    {
        this.money = money;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Player))
        {
            return false;
        }

        Player player = (Player) o;

        return getName().equals(player.getName());

    }

    @Override
    public int hashCode()
    {
        return getName().hashCode();
    }

    public void receiveMoney(int moneyEarned)
    {
        money += moneyEarned;
    }
}
