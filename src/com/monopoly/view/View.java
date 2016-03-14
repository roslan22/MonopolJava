package com.monopoly.view;

import com.monopoly.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View
{
    private Scanner scanner = new Scanner(System.in);

    public int getHumanPlayersNumber()
    {
        System.out.print("Please enter a number of Human players: ");
        return getNumberFromUser();
    }

    public int getComputerPlayersNumber()
    {
        System.out.print("Please enter a number of Computer players: ");
        return getNumberFromUser();
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

    public void boardChange(String change)
    {
        System.out.println(change);
        //TODO: implement
    }
    
    private int getNumberFromUser()
    {
        Integer inputNum = Utils.tryParseInt(scanner.next());
        
        while(inputNum == null)
        {
            System.out.println("Bad input format, please try again:");
            inputNum = Utils.tryParseInt(scanner.next());
        }
        
        return inputNum;
    }
}
