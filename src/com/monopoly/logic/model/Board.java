package com.monopoly.logic.model;

import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.cell.Jail;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Board
{
    public static final int FIRST_CELL_INDEX        = 0;
    public static final int FIRST_ALERT_CELL_INDEX  = 9;
    public static final int SECOND_ALERT_CELL_INDEX = 23;
    public static final int SURPRISE_CELL_INDEX     = 4;
    public static final int JAIL_CELL_INDEX         = 10; //TODO: check if jail index is constant

    private Engine engine;
    private List<Cell> cells = new ArrayList<>();
    private CardPack<SurpriseCard> surpriseCardPack;
    private CardPack<AlertCard>    alertCardPack;

    public Board(Engine engine, List<Cell> cells, CardPack<SurpriseCard> surpriseCardPack, CardPack<AlertCard> alertCardPack)
    {
        this.cells = cells;
        this.engine = engine;
        this.surpriseCardPack = surpriseCardPack;
        this.alertCardPack = alertCardPack;
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
        ((Jail) cells.get(JAIL_CELL_INDEX)).putInJail(player);
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
        if (playerCurrentPlace == FIRST_CELL_INDEX)
        {
            return SECOND_ALERT_CELL_INDEX - FIRST_CELL_INDEX;
        }
        else
        {
            return (cells.size() - SECOND_ALERT_CELL_INDEX) + FIRST_CELL_INDEX;
        }
    }

    private void movePlayerSkipRoadStart(Player player, int steps)
    {
        int newPlayerPlace = cells.indexOf(player.getCurrentCell()) + steps;
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
        return cells.size(); //TODO: check if there is only one surprise cell in Game
    }

    public static class PlayerNotOnBoard extends RuntimeException
    {
    }
}
