package controller;

import java.util.ArrayList;
import java.util.List;

import logic.engine.MonopolyInitReader;
import logic.model.cell.Cell;

public class XmlMonopolyInitReader extends MonopolyInitReader
{
    private String path;

    public XmlMonopolyInitReader(String path)
    {
        this.path = path;
    }

    @Override
    public List<Cell> read()
    {
        return new ArrayList<>();
    }
}
