package com.monopoly.logic.model;

import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board
{
    private List<Cell> cells = new ArrayList<>();
    private Map<Player, Integer> playersPlace;
    
    public Board()
    {
    }

    public Board(List<Cell> cells)
    {
        this.cells = cells;
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

    }
}