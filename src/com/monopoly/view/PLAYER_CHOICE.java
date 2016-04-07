package com.monopoly.view;

public enum PLAYER_CHOICE 
{
    
    YES(1), NO(2), RESIGN(3);
    
    private int choice;
    //Constructor which will initialize the enum
    PLAYER_CHOICE(int choice)
    {
      this.choice = choice;
    }
    
    //method to return the direction set by the user which initializing the enum
    public int GetChoice()
    {
      return choice;
    }
};
