package com.monopoly.controller;

import com.monopoly.logic.engine.MonopolyInitReader;
import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.Cell;

import java.util.List;

public class XmlMonopolyInitReader implements MonopolyInitReader
{
    private String path;

    public XmlMonopolyInitReader(String path)
    {
        this.path = path;
    }

    private void read()
    {

    }

    @Override
    public List<Cell> getCells()
    {
        return null;
    }

    @Override
    public CardPack<AlertCard> getAlertCards()
    {
        return null;
    }

    @Override
    public CardPack<SurpriseCard> getSurpriseCards()
    {
        return null;
    }
}
