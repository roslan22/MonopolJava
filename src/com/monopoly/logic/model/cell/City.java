package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

public class City extends Property
{
    private static final int MAX_HOUSES_AVAILABLE = 3;
    public static final int RENT_PRICES_AMOUNT = MAX_HOUSES_AVAILABLE + 1;

    private String cityName;
    private int houseCounter = 0;
    private int housePrice;
    private int[] rentPrices;
    private Country country;

    public City(String cityName, int propertyPrice, int housePrice, int[] rentPrices)
    {
        super(propertyPrice);
        this.cityName = cityName;
        this.housePrice = housePrice;
        setRentPrices(rentPrices);
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }

    private void setRentPrices(int[] rentPrices)
    {
        if (rentPrices.length != RENT_PRICES_AMOUNT)
            throw new IllegalRentPricesAmount();
        this.rentPrices = rentPrices;
    }

    public String getCityName()
    {
        return cityName;
    }

    @Override
    public void perform(Player player) 
    {
        if (canBuyHouse(player)) 
        {
            buyHouse(player);
        }
        else
        {
            super.perform(player);
        }
    }

    private boolean canBuyHouse(Player player) 
    {
        return getOwner().equals(player) && country.hasMonopoly(player);
    }

    private void buyHouse(Player player) {
        if (player.getMoney() >= housePrice && houseCounter < MAX_HOUSES_AVAILABLE)
        {
            if (offerToBuyHouse(player))
            {
                
                houseCounter++;
            }
        }
    }

    @Override
    public int getRentPrice() 
    {
        return rentPrices[houseCounter];
    }

    public static class IllegalRentPricesAmount extends RuntimeException
    {
    }
}
