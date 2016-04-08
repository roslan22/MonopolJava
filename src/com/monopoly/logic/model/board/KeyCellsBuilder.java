package com.monopoly.logic.model.board;

import com.monopoly.logic.model.cell.AlertCell;
import com.monopoly.logic.model.cell.Jail;
import com.monopoly.logic.model.cell.JailGate;
import com.monopoly.logic.model.cell.Parking;
import com.monopoly.logic.model.cell.PropertyGroup;
import com.monopoly.logic.model.cell.SurpriseCell;

import java.util.List;

public class KeyCellsBuilder
{
    private List<AlertCell>     alertCells;
    private List<SurpriseCell>  surpriseCells;
    private List<PropertyGroup> propertyGroups;
    private Jail                jailCell;
    private JailGate            jailGate;
    private Parking             parkingCell;

    public KeyCellsBuilder setAlertCells(List<AlertCell> alertCells)
    {
        this.alertCells = alertCells;
        return this;
    }

    public KeyCellsBuilder setSurpriseCells(List<SurpriseCell> surpriseCells)
    {
        this.surpriseCells = surpriseCells;
        return this;
    }

    public KeyCellsBuilder setJailCell(Jail jailCell)
    {
        this.jailCell = jailCell;
        return this;
    }

    public KeyCellsBuilder setJailGate(JailGate jailGate)
    {
        this.jailGate = jailGate;
        return this;
    }

    public KeyCellsBuilder setParkingCell(Parking parkingCell)
    {
        this.parkingCell = parkingCell;
        return this;
    }

    public KeyCellsBuilder setPropertyGroups(List<PropertyGroup> propertyGroups)
    {
        this.propertyGroups = propertyGroups;
        return this;
    }

    public KeyCells createKeyCells()
    {
        return new KeyCells(alertCells, surpriseCells, jailCell, jailGate, parkingCell, propertyGroups);
    }
}