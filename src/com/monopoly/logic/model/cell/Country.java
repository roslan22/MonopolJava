package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

import java.util.List;

public class Country
{
    private List<City> countryCities;

    public Country(List<City> countryCities)
    {
        this.countryCities = countryCities;
    }

    public boolean hasMonopoly(Player player)
    {
        for (City city : countryCities)
        {
            if (!city.getOwner().equals(player))
                return false;
        }
        return true;
    }
}
