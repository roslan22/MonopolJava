package com.monopoly.logic.model.player;

import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.cell.City;
import com.monopoly.logic.model.cell.Property;
import com.monopoly.logic.model.cell.PropertyGroup;

import java.util.stream.Collectors;

public class ComputerPlayer extends Player
{
    public static final int LOT_OF_MONEY_RATIO = (1 / 3);
    public static final double HIGH_DECISION_RATIO = 0.7;
    public static final double LOW_DECISION_RATIO  = 0.4;
    public static final int LAST_CELL = 1;
    public static final int ALMOST_ALL_CELLS = 2;
    public static final int NONE_CELLS = 0;
    public static final int MIN_HIGH_FREQ_RESULT = 5;
    public static final int MAX_HIGH_FREQ_RESULT = 9;
    private static int computerNameCount = 0;

    private Board board;

    private static String getNextName()
    {
        computerNameCount++;
        return "Computer" + String.valueOf(computerNameCount);
    }

    public ComputerPlayer(int playerId, MonopolyEngine engine, Board board)
    {
        super(getNextName(), playerId, engine);
        this.board = board;
    }

    @Override
    public void askToBuyProperty(Property property)
    {
        if(hasALotOfMoney())
        {
            spendItAllStrategy(property);
        }
        else
        {
            spendItWiselyStrategy(property);
        }
    }

    private void spendItWiselyStrategy(Property property)
    {
        int decisionRatio = 0;
        decisionRatio += isHighFrequentlyLandedCell(property) ? HIGH_DECISION_RATIO : 0;
        decisionRatio += isMonopolyPossible(property.getPropertyGroup()) ? LOW_DECISION_RATIO : 0;
        decisionRatio += isBlockingAMonopoly(property.getPropertyGroup()) ? LOW_DECISION_RATIO : 0;
        decisionRatio += isSavingMoneyForOtherMonopoly(property.getPropertyGroup()) ? -LOW_DECISION_RATIO : 0;

        if (decisionRatio >= HIGH_DECISION_RATIO)
        {
            buyProperty(property);
        }
    }

    private boolean isSavingMoneyForOtherMonopoly(PropertyGroup propertyGroup)
    {
        return board.getPropertyGroups().stream().filter(p -> isCloseToMonopoly(p) && !p.equals(propertyGroup)).count() > NONE_CELLS;
    }

    private boolean isCloseToMonopoly(PropertyGroup propertyGroup)
    {
        return propertyGroup.getProperties().stream().filter(Property::isPropertyAvailable).count() == LAST_CELL &&
                propertyGroup.getProperties().stream().filter(p -> !p.isPropertyAvailable() && p.getOwner().equals(this)).count() == ALMOST_ALL_CELLS;
    }

    private boolean isBlockingAMonopoly(PropertyGroup propertyGroup)
    {
        return propertyGroup.getProperties().stream()
                .filter(p -> p.getOwner() != null && !p.getOwner().equals(this))
                .map(Property::getOwner)
                .collect(Collectors.toSet()).size() == 1;
    }

    /**
     * The jail is the most frequently landed cell in the Monopoly game.
     * The numbers 5 to 9 are the most frequently dice rolls.
     * So the cells between jailCellIndex + 5 and jailCellIndex + 9 are the most
     * frequently landed cells
     * */
    private boolean isHighFrequentlyLandedCell(Property property)
    {
        int jailCellIndex = board.getJailCellIndex();
        int propertyCellIndex = board.getCellIndex(property);
        int cellsCount = board.getCellsCount();
        return propertyCellIndex >= (jailCellIndex + MIN_HIGH_FREQ_RESULT) % cellsCount &&
                propertyCellIndex <= (jailCellIndex + MAX_HIGH_FREQ_RESULT) % cellsCount;
    }

    private void spendItAllStrategy(Property property)
    {
        buyProperty(property);
    }

    private boolean hasALotOfMoney()
    {
        return getMoneyAmount() / START_MONEY_AMOUNT > LOT_OF_MONEY_RATIO;
    }

    private void buyProperty(Property property)
    {
        engine.addAssetBoughtEvent(this, property.getName());
        property.buyProperty(this);
    }

    private boolean isMonopolyPossible(PropertyGroup pg)
    {
        return pg.getProperties().stream().allMatch(p -> p.isPropertyAvailable() || p.getOwner().equals(this));
    }

    @Override
    public void askToBuyHouse(City city)
    {
        engine.addHouseBoughtEvent(this, city.getName());
        city.buyHouse(this);
    }
}
