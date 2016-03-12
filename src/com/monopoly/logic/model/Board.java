package com.monopoly.logic.model;

import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Board
{
    public static final int FIRST_CELL_INDEX = 0;
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
        int newPlayerPlace = cells.indexOf(player.getCurrentCell()) + stepsToMove;
        if (newPlayerPlace >= cells.size())
            engine.playerFinishedARound(player);
        player.setCurrentCell(cells.get(newPlayerPlace % cells.size()));
    }

    public Cell getFirstCell()
    {
        return cells.get(FIRST_CELL_INDEX);
    }

    public void moveToRoadStart(Player player)
    {
        int playerCurrentPlace = cells.indexOf(player.getCurrentCell());
        if ((playerCurrentPlace == -1))
            throw new PlayerNotOnBoard();
        movePlayer(player, distanceToRoadStart(playerCurrentPlace));
    }

    private int distanceToRoadStart(int playerCurrentPlace)
    {
        return cells.size() - playerCurrentPlace;
    }

    public void removeCardFromSurpricePack(SurpriseCard surpriseCard)
    {
        surpriseCardPack.removeFromPack(surpriseCard);
    }

    public void returnCardToSurprisePack(SurpriseCard surpriseCard)
    {
        surpriseCardPack.returnToPack(surpriseCard);
    }

    public static class PlayerNotOnBoard extends RuntimeException
    {
    }
}
