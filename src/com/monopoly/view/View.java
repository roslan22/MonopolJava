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
        return scanner.nextInt();
    }

    public int getComputerPlayersNumber()
    {
        System.out.print("Please enter a number of Computer players: ");
        return scanner.nextInt();
    }

    public List<String> getHumanPlayerNames(int humanPlayersNumber)
    {
        System.out.println("Please enter the names of the human players: ");

        List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < humanPlayersNumber; i++)
        {
            playerNames.add(getNextName(i));
        }

        return playerNames;
    }

    private String getNextName(int playerNumber)
    {
        System.out.println("Player number " + playerNumber + " is:");
        String inputName = scanner.nextLine();  //TODO: check if it's ok
        //clear the scanner buffer from the Enter
        scanner.nextLine();
        return inputName;
    }

    public void BoardChange(String change)
    {
        //TODO: implement
    }
}
