package com.monopoly.view.interfaces;

@FunctionalInterface
public interface PlayerBuyAssetDecision 
{
    void onAnswer(int eventID, boolean answer);
}
