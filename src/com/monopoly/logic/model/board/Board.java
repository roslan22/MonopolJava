package com.monopoly.logic.model.board;

import com.monopoly.logic.engine.MonopolyEngine;
import com.monopoly.logic.events.Event;
import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.cell.Property;
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
    private CardPack<AlertCard>    alertCardPack;

    private KeyCells keyCells;
            
   public Board(MonopolyEngine engine, List<Cell> cells, CardPack<SurpriseCard> surpriseCardPack, CardPack<AlertCard> alertCardPack,
                KeyCells keyCells)
    {
        this.cells = cells;
        this.engine = engine;
        this.surpriseCardPack = surpriseCardPack;
        this.alertCardPack = alertCardPack;
        this.keyCells = keyCells;
        initBoard();
    }

    private void initBoard()
    {
        setCardCellsBoard();
        keyCells.getJailGate().setJailCell(keyCells.getJailCell());
    }

    private void setCardCellsBoard()
    {
        keyCells.getAlertCells().forEach(alertCell -> alertCell.setBoard(this));
        keyCells.getSurpriseCells().forEach(surpriseCell -> surpriseCell.setBoard(this));
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
        onBoardChange(player);
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
//        engine.addEventToEventManager(CreatePlayerLostEvent(player));
    }

    private void clearPropertiesOwner(Player player)
    {
        cells.stream().filter(cell -> cell instanceof Property).forEach(cell -> {
            Property propertyCell = (Property) cell;
            if (!propertyCell.isPropertyAvailable() && propertyCell.getOwner().equals(player))
            {
                propertyCell.setOwner(null);
            }
        });
    }

    private Event CreatePlayerLostEvent(Player player) {
//        Event event = new EventBuilder().setEventID(engine.getLastEventID())
//                .setEventType(EventType.PLAYER_LOST).createGameEvent();
//        event.setPlayerName(player.getName());
//        return event;
        return null;
    }

    public static class PlayerNotOnBoard extends RuntimeException
    {
    }
    
    private void onBoardChange(Player player)
    {
//        engine.addEventToEventManager(CreateBoardChangeEvent(player));
    }
    
    private Event CreateBoardChangeEvent(Player player) {
//        Event event = new EventBuilder().setEventID(engine.getLastEventID()).setEventType(EventType.MOVE)
//                .createGameEvent();
//        event.setPlayerName(player.getName());
//        return event;
        return null;
    }
}
