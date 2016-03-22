package com.monopoly.logic.model.player;

import com.monopoly.logic.model.card.OutOfJailCard;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.cell.Jail;
import com.monopoly.logic.model.cell.Parking;

public abstract class Player
{
    public static enum PlayerType {HUMAN,COMPUTER};
    
    private static final int START_MONEY_AMOUNT = 1500;
    private String name;
    private int playerID;
    private PlayerType playerType;

    private int money = START_MONEY_AMOUNT;
    private Cell currentCell;
    private OutOfJailCard outOfJailCard;


    public OutOfJailCard getOutOfJailCard() {
        return outOfJailCard;
    }

    public void setOutOfJailCard(OutOfJailCard outOfJailCard) {
        this.outOfJailCard = outOfJailCard;
    }

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
    
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
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

    public void payToOtherPlayer(Player player, int amount)
    {
        if (player.equals(this) || money <= 0)
            return;

        int actualPayedAmount = money > amount ? amount : money;
        player.receiveMoney(actualPayedAmount);
        money -= actualPayedAmount;
    }

    public abstract boolean isWillingToBuyHouse();

    public void payToBank(int amount)
    {
        money -= money > amount ? amount : money;
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
        if (getCurrentCell() instanceof Parking)
        {
            ((Parking) getCurrentCell()).exitFromParking(this);
        }
    }

    public void getOutOfJail()
    {
        if (getCurrentCell() instanceof Jail)
        {
            ((Jail) getCurrentCell()).getPlayerOutOfJail(this);
        }
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
    
    abstract public PlayerType getPlayerType();
    
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
}
