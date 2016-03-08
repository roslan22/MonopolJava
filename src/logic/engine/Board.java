package logic.engine;

import java.util.ArrayList;
import java.util.List;

import logic.model.cell.Cell;

public class Board
{
    private List<Cell> cells = new ArrayList<>();

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
}
