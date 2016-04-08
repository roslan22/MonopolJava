package com.monopoly.logic.model.board;

import com.monopoly.logic.model.cell.AlertCell;
import com.monopoly.logic.model.cell.Jail;
import com.monopoly.logic.model.cell.JailGate;
import com.monopoly.logic.model.cell.Parking;
import com.monopoly.logic.model.cell.PropertyGroup;
import com.monopoly.logic.model.cell.SurpriseCell;

import java.util.ArrayList;
import java.util.List;

public class KeyCells
{
    private List<AlertCell>    alertCells    = new ArrayList<>();
    private List<SurpriseCell> surpriseCells = new ArrayList<>();
    private List<PropertyGroup> propertyGroups = new ArrayList<>();
    private Jail     jailCell;
    private JailGate jailGate;
    private Parking  parkingCell;

    public KeyCells(List<AlertCell> alertCells, List<SurpriseCell> surpriseCells, Jail jailCell, JailGate jailGate,
                    Parking parkingCell, List<PropertyGroup> propertyGroups)
    {
        this.alertCells = alertCells;
        this.surpriseCells = surpriseCells;
        this.jailCell = jailCell;
        this.jailGate = jailGate;
        this.parkingCell = parkingCell;
        this.propertyGroups = propertyGroups;
    }

    public List<AlertCell> getAlertCells()
    {
        return alertCells;
    }

    public void setAlertCells(List<AlertCell> alertCells)
    {
        this.alertCells = alertCells;
    }

    public List<SurpriseCell> getSurpriseCells()
    {
        return surpriseCells;
    }

    public void setSurpriseCells(List<SurpriseCell> surpriseCells)
    {
        this.surpriseCells = surpriseCells;
    }

    public Jail getJailCell()
    {
        return jailCell;
    }

    public void setJailCell(Jail jailCell)
    {
        this.jailCell = jailCell;
    }

    public JailGate getJailGate()
    {
        return jailGate;
    }

    public void setJailGate(JailGate jailGate)
    {
        this.jailGate = jailGate;
    }

    public Parking getParkingCell()
    {
        return parkingCell;
    }

    public void setParkingCell(Parking parkingCell)
    {
        this.parkingCell = parkingCell;
    }

    public List<PropertyGroup> getPropertyGroups()
    {
        return propertyGroups;
    }

    public void setPropertyGroups(List<PropertyGroup> propertyGroups)
    {
        this.propertyGroups = propertyGroups;
    }
}
