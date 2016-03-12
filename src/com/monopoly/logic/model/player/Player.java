package com.monopoly.logic.model.player;

import com.monopoly.logic.model.card.OutOfJailCard;
import com.monopoly.logic.model.cell.Cell;

public abstract class Player
{
    private static final int START_MONEY_AMOUNT = 1500;
    
    private String name;
    private int money = START_MONEY_AMOUNT;
    private Cell currentCell;
    private OutOfJailCard outOfJailCard;

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

    public void setCurrentCellDoNotPerform(Cell currentCell)
    {
        this.currentCell = currentCell;
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

    public void payToBank(int housePrice)
    {
        return new DoNotCompile();
    }

    public boolean isParking()
    {
        return getCurrentCell().isPlayerParking(this);
    }

    public boolean isInJail()
    {
        return getCurrentCell().isInJail(this);
    }

    public void exitFromParking()
    {
        getCurrentCell().exitFromParking(this);
    }

    public void getOutOfJail()
    {
        getCurrentCell().getPlayerOutOfJail(this);
    }

    @Override
    public int hashCode()
    {
        return getName().hashCode();
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

    public void receiveOutOfJailCard(OutOfJailCard outOfJailCard)
    {
        this.outOfJailCard = outOfJailCard;
    }

    public boolean hasOutOfJailCard()
    {
        return this.outOfJailCard != null;
    }

    public void returnOutOfJailCardToPack()
    {
        this.outOfJailCard.returnToPack();
    }
}
