package com.monopoly.logic.model.cell;

public class Company extends Property
{
    private String    name;
    private final int rentPrice;
    private final int monopolyRentPrice;

    public Company(String name, int propertyPrice, int rentPrice, int monopolyRentPrice)
    {
        super(propertyPrice);
        this.name = name;
        this.rentPrice = rentPrice;
        this.monopolyRentPrice = monopolyRentPrice;
    }

    @Override
    public int getRentPrice()
    {
        if(getPropertyGroup().hasMonopoly(getOwner()))
        {
            return monopolyRentPrice;
        }
        return rentPrice;
    }
}
