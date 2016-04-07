package com.monopoly.view.interfaces;

@FunctionalInterface
public interface PlayerBuyHouseDecision 
{
    void onAnswer(int eventID, boolean answer);
}
