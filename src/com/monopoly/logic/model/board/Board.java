package com.monopoly.logic.model.board;

import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.cell.Property;
import com.monopoly.logic.model.cell.PropertyGroup;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Board
{
    public static final int FIRST_CELL_INDEX = 0;

    private MonopolyEngine engine;
    private List<Cell> cells = new ArrayList<>();
    private CardPack<SurpriseCard> surpriseCardPack;

    private KeyCells keyCells;

    public Board(MonopolyEngine engine, List<Cell> cells, CardPack<SurpriseCard> surpriseCardPack, KeyCells keyCells)
    {
        this.cells = cells;
        this.engine = engine;
        this.surpriseCardPack = surpriseCardPack;
        this.keyCells = keyCells;
        initBoard();
    }

    public KeyCells getKeyCells()
    {
        return keyCells;
    }

    private void initBoard()
    {
        setCardCellsBoard();
        keyCells.getJailCell().setBoard(this);
        keyCells.getJailGate().setJailCell(keyCells.getJailCell());
    }

    private void setCardCellsBoard()
    {
        keyCells.getAlertCells().forEach(alertCell -> alertCell.setBoard(this));
        keyCells.getSurpriseCells().forEach(surpriseCell -> surpriseCell.setBoard(this));
    }

    public void movePlayer(Player player, int stepsToMove)
    {
        int currentPlayerPlace = getPlayerCurrentPlace(player);
        int newPlayerPlace = currentPlayerPlace + stepsToMove;
        if (newPlayerPlace > cells.size())
        {
            engine.playerFinishedARound(player);
        }
        if (newPlayerPlace == cells.size())
        {
            engine.addLandedOnStartSquareEvent(player);
        }

        int destinationCellIndex = newPlayerPlace % cells.size();
        addMovePlayerEvent(player, currentPlayerPlace, destinationCellIndex);
        player.setCurrentCell(cells.get(destinationCellIndex));
    }

    public void addMovePlayerEvent(Player player, int currentPlayerPlace, int destinationCellIndex)
    {
        String destinationName = cells.get(destinationCellIndex).getClass().getSimpleName();
        engine.addMovePlayerEvent(player, currentPlayerPlace, destinationCellIndex, destinationName);
    }

    public Cell getFirstCell()
    {
        return cells.get(FIRST_CELL_INDEX);
    }

    public void moveToRoadStart(Player player)
    {
        int playerCurrentPlace = getPlayerCurrentPlace(player);
        movePlayer(player, distanceToRoadStart(playerCurrentPlace));
    }

    private int getPlayerCurrentPlace(Player player)
    {
        int playerCurrentPlace = cells.indexOf(player.getCurrentCell());
        if ((playerCurrentPlace == -1))
        {
            throw new PlayerNotOnBoard();
        }
        return playerCurrentPlace;
    }

    public void moveToJail(Player player)
    {
        keyCells.getJailCell().putInJail(player);
    }

    private int distanceToRoadStart(int playerCurrentPlace)
    {
        return cells.size() - playerCurrentPlace;
    }

    public void removeCardFromSurprisePack(SurpriseCard surpriseCard)
    {
        surpriseCardPack.removeFromPack(surpriseCard);
    }

    public void returnCardToSurprisePack(SurpriseCard surpriseCard)
    {
        surpriseCardPack.returnToPack(surpriseCard);
    }

    private int distanceToNextAlertCard(int playerCurrentPlace)
    {
        return distanceToClosestCell(keyCells.getAlertCells(), playerCurrentPlace);
    }

    private <T extends Cell> int distanceToClosestCell(List<T> cells, int comparedIndex)
    {
        List<Integer> distances = cells.stream().map(cell -> distanceToCell(cell, comparedIndex))
                .collect(Collectors.toList());
        return Collections.min(distances);
    }

    private int distanceToCell(Cell cell, int comparedIndex)
    {
        int firstCellIndex = cells.indexOf(cell);
        if (comparedIndex >= firstCellIndex)
        {
            return cells.size() - comparedIndex + firstCellIndex;
        }
        else
        {
            return firstCellIndex - comparedIndex;
        }
    }

    private void movePlayerSkipRoadStart(Player player, int steps)
    {
        int playerCurrentPlace = getPlayerCurrentPlace(player);
        int newPlayerPlace = (playerCurrentPlace + steps) % cells.size();
        addMovePlayerEvent(player, playerCurrentPlace, newPlayerPlace);
        player.setCurrentCell(cells.get(newPlayerPlace));
    }

    public void moveToNextAlertCard(Player player)
    {
        int playerCurrentPlace = getPlayerCurrentPlace(player);
        movePlayerSkipRoadStart(player, distanceToNextAlertCard(playerCurrentPlace));
    }

    public void moveToNextSurpriseCard(Player player)
    {
        int playerCurrentPlace = getPlayerCurrentPlace(player);
        movePlayer(player, distanceToNextSurprise(playerCurrentPlace));
    }

    private int distanceToNextSurprise(int playerCurrentPlace)
    {
        return distanceToClosestCell(keyCells.getSurpriseCells(), playerCurrentPlace);
    }

    public void payToEveryoneElse(Player player, int moneyToPay)
    {
        engine.payToEveryoneElse(player, moneyToPay);
    }

    public void transferOtherPlayersMoneyTo(Player player, int moneyEarned)
    {
        engine.transferOtherPlayersMoneyTo(player, moneyEarned);
    }

    public void playerLost(Player player)
    {
        keyCells.getJailCell().getPlayerOutOfJail(player);
        keyCells.getParkingCell().exitFromParking(player);
        clearPropertiesOwner(player);
    }

    public List<Property> getProperties()
    {
        List<Property> properties = new ArrayList<>();
        keyCells.getPropertyGroups().forEach(pg -> properties.addAll(pg.getProperties()));
        return properties;
    }

    private void clearPropertiesOwner(Player player)
    {
        getProperties().stream().forEach(p -> clearPlayerPropertiesOwnership(player, p));
    }

    private void clearPlayerPropertiesOwnership(Player player, Property p)
    {
        if (!p.isPropertyAvailable() && p.getOwner().equals(player))
        {
            p.setOwner(null);
        }
    }

    public void addSurpriseCardEvent(Player player, String cardText)
    {
        engine.addSurpriseCardEvent(player, cardText);
    }

    public void addAlertCardEvent(Player player, String cardText)
    {
        engine.addAlertCardEvent(player, cardText);
    }

    public void addOutOfJailEvent(Player player)
    {
        engine.addOutOfJailEvent(player);
    }

    public void addMovePlayerToJailEvent(Player player)
    {
        engine.addGoToJailEvent(player);
        addMovePlayerEvent(player, getPlayerCurrentPlace(player), cells.indexOf(keyCells.getJailCell()));
    }

    public void addPlayerUsedOutOfJailCard(Player player)
    {
        engine.addPlayerUsedOutOfJailCard(player);
    }

    public int getParkingCellIndex()
    {
        return cells.indexOf(keyCells.getParkingCell());
    }

    public int getJailCellIndex()
    {
        return cells.indexOf(keyCells.getJailCell());
    }

    public List<PropertyGroup> getPropertyGroups()
    {
        return keyCells.getPropertyGroups();
    }

    public int getCellIndex(Cell cell)
    {
        return cells.indexOf(cell);
    }

    public int getCellsCount()
    {
        return cells.size();
    }

    public static class PlayerNotOnBoard extends RuntimeException
    {
    }
}
