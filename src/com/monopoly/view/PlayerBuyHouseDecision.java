package com.monopoly.view;

@FunctionalInterface
public interface PlayerBuyHouseDecision 
{
    void onAnswer(int eventID, boolean answer);
}
