
package com.monopoly.logic.engine;


import com.monopoly.logic.model.board.KeyCells;
import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.Cell;

import java.util.List;

public interface MonopolyInitReader
{
    List<Cell> getCells();

    CardPack<AlertCard> getAlertCards();

    CardPack<SurpriseCard> getSurpriseCards();

    KeyCells getKeyCells();

    void read();
}
