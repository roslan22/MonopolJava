package com.monopoly.view;

import java.util.Arrays;

public enum PlayerChoice
{
    
    YES(1), NO(2), RESIGN(3);

    public static boolean isChoiceExists(int choice)
    {
        return Arrays.stream(PlayerChoice.values()).anyMatch(c -> c.getChoice() == choice);
    }

    public static PlayerChoice getValueForChoice(int choice)
    {
        return Arrays.stream(PlayerChoice.values()).filter(c -> c.getChoice() == choice).findAny().get();
    }

    private int choice;

    PlayerChoice(int choice)
    {
      this.choice = choice;
    }

    public int getChoice()
    {
      return choice;
    }
}
