package com.monopoly.view;

@FunctionalInterface
public interface PlayerBuyAssetDecision 
{
    void onAnswer(int eventID, boolean answer);
}
