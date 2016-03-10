package com.monopoly.logic.model;

import com.monopoly.logic.engine.Engine;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Board
{
    private Engine engine;
    private List<Cell> cells = new ArrayList<>();
    private CardPack<SurpriseCard> surpriseCardPack;
    private CardPack<AlertCard> alertCardPack;
    
    public Board(Engine engine, List<Cell> cells)
    {
        this.cells = cells;
        this.engine = engine;
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
        int currentPlayerPlace = 0;
        if (currentPlayerPlace + stepsToMove >= cells.size())
        {
            engine.playerFinishedARound(player);
        }
        cells.get((currentPlayerPlace + stepsToMove) % cells.size()).perform(player);
    }
}
