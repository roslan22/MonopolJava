package com.monopoly.logic.model.cell;

public enum CellType
{
    JAIL,
    JAIL_GATE,
    PARKING,
    SURPRISE_CELL,
    ALERT_CELL,
    DEFAULT;

    public static class UnimplementedCellType extends RuntimeException
    {
    }
}
