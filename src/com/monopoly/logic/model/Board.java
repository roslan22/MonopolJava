package com.monopoly.logic.model;

import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.AlertCell;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.cell.Jail;
import com.monopoly.logic.model.cell.SurpriseCell;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Board
{
    public static final int FIRST_CELL_INDEX        = 0;

    private Engine engine;
    private List<Cell> cells = new ArrayList<>();
    private CardPack<SurpriseCard> surpriseCardPack;
    private CardPack<AlertCard>    alertCardPack;

    private List<AlertCell> alertCells = new ArrayList<>();
    private List<SurpriseCell> surpriseCells = new ArrayList<>();
    private Jail jailCell = new Jail();

    public Board(Engine engine, List<Cell> cells, CardPack<SurpriseCard> surpriseCardPack, CardPack<AlertCard> alertCardPack)
    {
        this.cells = cells;
        this.engine = engine;
        this.surpriseCardPack = surpriseCardPack;
        this.alertCardPack = alertCardPack;
        mapKeyCells();
    }

    private void mapKeyCells()
    {
        cells.forEach(this::mapCell);
    }

    private void mapCell(Cell cell)
    {
        if (cell instanceof Jail)
            jailCell = (Jail) cell;
        if (cell instanceof SurpriseCell)
            surpriseCells.add((SurpriseCell) cell);
        if (cell instanceof AlertCell)
            alertCells.add((AlertCell) cell);
    }

    public List<Cell> getCells()
    {
        return cells;
    }

    public void setCells(List<Cell> cells)
    {
        this.cells = cells;
    }

    public void movePlayer(Player player, int stepsToMove)
    {
        int newPlayerPlace = getPlayerCurrentPlace(player) + stepsToMove;
        if (newPlayerPlace >= cells.size())
        {
            engine.playerFinishedARound(player);
        }
        player.setCurrentCell(cells.get(newPlayerPlace % cells.size()));
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
        jailCell.putInJail(player);
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
        return distanceToClosestCell(alertCells, playerCurrentPlace);
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
        int newPlayerPlace = getPlayerCurrentPlace(player) + steps;
        player.setCurrentCell(cells.get(newPlayerPlace % cells.size()));
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
        return distanceToClosestCell(surpriseCells, playerCurrentPlace);
    }

    public void payToEveryoneElse(Player player, int moneyToPay)
    {

    }

    public void transferOtherPlayersMoneyTo(Player player, int moneyEarned)
    {

    }

    public static class PlayerNotOnBoard extends RuntimeException
    {
    }
}
