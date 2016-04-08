package com.monopoly.logic.model.player;

import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.model.card.OutOfJailCard;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.cell.City;
import com.monopoly.logic.model.cell.Jail;
import com.monopoly.logic.model.cell.Parking;
import com.monopoly.logic.model.cell.Property;

public abstract class Player
{
    public static final int START_MONEY_AMOUNT = 1500;
    protected MonopolyEngine engine;
    private   String         name;
    private   int            playerID;

    private int money = START_MONEY_AMOUNT;
    private Cell          currentCell;
    private OutOfJailCard outOfJailCard;

    public Player(String name, int playerID, MonopolyEngine engine)
    {
        this.name = name;
        this.playerID = playerID;
        this.engine = engine;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getMoneyAmount()
    {
        return money;
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

    public int getPlayerID()
    {
        return playerID;
    }

    public void setCurrentCellDoNotPerform(Cell currentCell)
    {
        this.currentCell = currentCell;
    }

    public void receiveMoneyFromBank(int amount)
    {
        money += amount;
        engine.addPaymentFromBankEvent(this, amount);
    }

    public abstract void askToBuyProperty(Property property);

    public void payToOtherPlayer(Player player, int amount)
    {
        if (player.equals(this) || money <= 0)
        {
            return;
        }

        int actualPayedAmount = money > amount ? amount : money;
        player.receiveMoneyFromOtherPlayer(actualPayedAmount);
        money -= actualPayedAmount;
        engine.addPayToOtherPlayerEvent(this, player, actualPayedAmount);
    }

    private void receiveMoneyFromOtherPlayer(int amount)
    {
        money += amount;
    }

    public abstract void askToBuyHouse(City city);

    public void payToBank(int amount)
    {
        int actualAmountPayed = money > amount ? amount : money;
        money -= actualAmountPayed;
        engine.addPayToBankEvent(this, actualAmountPayed);
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
