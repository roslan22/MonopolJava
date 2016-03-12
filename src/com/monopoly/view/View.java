package com.monopoly.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View
{
    private Scanner scanner = new Scanner(System.in);

    public int getHumanPlayersNumber()
    {
        System.out.print("Please enter a number of Human players: ");
	int humanPlayers  = scanner.nextInt();
        
        return humanPlayers;
    }

    public int getComputerPlayersNumber()
    {
        System.out.print("Please enter a number of Computer players: ");
	int humanPlayers  = scanner.nextInt();
        
        return humanPlayers;
    }

    public List<String> getHumanPlayerNames(int humanPlayersNumber)
    {
        List<String> playerNames = new ArrayList<String>();
        String inputName;
        
        System.out.println("Please enter names of human players: ");

        for(int i = 0; i < humanPlayersNumber; i++)
        {
            System.out.println("Player number " + i + " is:");
            inputName = scanner.nextLine();  //TODO: check if it's ok
            playerNames.add(inputName);
            //clear the scanner buffer from the Enter
            scanner.nextLine();
        }
        
        return playerNames;
    }
    
    public void BoardChange(String change)
    {
        //TODO: implement
    }
}
