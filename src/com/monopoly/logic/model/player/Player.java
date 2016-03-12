package com.monopoly.logic.model.player;

import com.monopoly.logic.model.cell.Cell;

public abstract class Player
{
    private static final int START_MONEY_AMOUNT = 1500;
    
    private String name;
    private int money = START_MONEY_AMOUNT;
    private Cell currentCell;

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

    public Cell getCurrentCell()
    {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell)
    {
        this.currentCell = currentCell;
        currentCell.perform(this);
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

    public void receiveMoney(int moneyEarned)
    {
        money += moneyEarned;
    }

    public abstract boolean isWillingToBuyProperty();

    public void payToOtherPlayer(Player owner, int rentPrice)
    {
        return new DoNotCompile();
    }

    public abstract boolean isWillingToBuyHouse();
}
