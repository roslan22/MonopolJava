package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.player.Player;

public abstract class Property extends Cell
{
    private Player owner;
    private int    price;
    private PropertyGroup propertyGroup;

    public Property(int price)
    {
        this.price = price;
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
        if (isPropertyAvailable())
        {
            buyProperty(player);
        }
        else
        {
            player.payToOtherPlayer(getOwner(), getRentPrice());
        }
    }

    private void buyProperty(Player player)
    {
        if (player.getMoney() >= getPrice() && player.isWillingToBuyProperty())
        {
            player.payToBank(price);
            setOwner(player);
        }
    }
}
