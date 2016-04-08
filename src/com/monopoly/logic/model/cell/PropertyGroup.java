package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

import java.util.List;

public class PropertyGroup
{
    private String name;
    private List<? extends Property> properties;

    public PropertyGroup(String name, List<? extends Property> countryCities)
    {
        this.name = name;
        this.properties = countryCities;
    }

    public String getName()
    {
        return name;
    }

    public List<? extends Property> getProperties()
    {
        return properties;
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
