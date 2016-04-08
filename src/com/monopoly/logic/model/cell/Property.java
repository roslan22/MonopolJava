package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

public abstract class Property extends Cell
{
    protected String        name;
    private   Player        owner;
    private   int           price;
    private   PropertyGroup propertyGroup;

    public Property(int price, String name)
    {
        this.price = price;
        this.name = name;
    }

    public void setPropertyGroup(PropertyGroup propertyGroup)
    {
        this.propertyGroup = propertyGroup;
    }

    public PropertyGroup getPropertyGroup()
    {
        return propertyGroup;
    }

    public Player getOwner()
    {
        return owner;
    }

    public void setOwner(Player owner)
    {
        this.owner = owner;
    }

    public int getPrice()
    {
        return price;
    }

    public abstract int getRentPrice();

    public boolean isPropertyAvailable()
    {
        return owner == null;
    }

    @Override
    public void perform(Player player)
    {
        if (isAbleToBuyProperty(player))
        {
            player.askToBuyProperty(this);
        }
        else if (!isPropertyAvailable())
        {
            player.payToOtherPlayer(getOwner(), getRentPrice());
        }
    }

    private boolean isAbleToBuyProperty(Player player)
    {
        return isPropertyAvailable() && player.getMoneyAmount() > getPrice();
    }

    public void buyProperty(Player player)
    {
        if (isAbleToBuyProperty(player))
        {
            player.payToBank(price);
            setOwner(player);
        }
    }

    public String getName()
    {
        return name;
    }

    @Override
    public CellType getType()
    {
        return CellType.PROPERTY;
    }

    @Override
    public String getCellName()
    {
        return getName();
    }
}
