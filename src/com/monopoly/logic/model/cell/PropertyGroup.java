package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

import java.util.List;

public class PropertyGroup
{
    private List<Property> properties;

    public PropertyGroup(List<Property> countryCities)
    {
        this.properties = countryCities;
    }

    public boolean hasMonopoly(Player player)
    {
        for (Property p : properties)
        {
            if (p.isPropertyAvailable() || !p.getOwner().equals(player))
                return false;
        }
        return true;
    }
}
