package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

public class City extends Property
{
    private static final int MAX_HOUSES_AVAILABLE = 3;
    public static final  int RENT_PRICES_AMOUNT   = MAX_HOUSES_AVAILABLE + 1;

    private int houseCounter = 0;
    private int   housePrice;
    private int[] rentPrices;

    public City(String cityName, int propertyPrice, int housePrice, int[] rentPrices)
    {
        super(propertyPrice, cityName);
        this.housePrice = housePrice;
        setRentPrices(rentPrices);
    }

    private void setRentPrices(int[] rentPrices)
    {
        if (rentPrices.length != RENT_PRICES_AMOUNT)
        {
            throw new IllegalRentPricesAmount();
        }
        this.rentPrices = rentPrices;
    }

    public int getHousePrice()
    {
        return housePrice;
    }

    public int[] getRentPrices()
    {
        return rentPrices;
    }

    @Override
    public int getRentPrice()
    {
        return rentPrices[houseCounter];
    }

    @Override
    public void perform(Player player)
    {
        if (canBuyHouse(player))
        {
            player.askToBuyHouse(this);
        }
        else
        {
            super.perform(player);
        }
    }

    private boolean canBuyHouse(Player player)
    {
        return getPropertyGroup().hasMonopoly(player) &&
                houseCounter < MAX_HOUSES_AVAILABLE &&
                player.getMoneyAmount() > housePrice;
    }

    public void buyHouse(Player player)
    {
        if (canBuyHouse(player))
        {
            player.payToBank(housePrice);
            houseCounter++;
        }
    }

    public static class IllegalRentPricesAmount extends RuntimeException
    {
    }
}
