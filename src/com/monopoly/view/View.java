package com.monopoly.view;

import com.monopoly.GameEvent;
import com.monopoly.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View
{
    private Scanner scanner = new Scanner(System.in);
    private PlayerBuyHouseDecision playerBuyHouseDecision;

    public void setPlayerBuyHouseDecision(PlayerBuyHouseDecision playerBuyHouseDecision) {
        this.playerBuyHouseDecision = playerBuyHouseDecision;
    }
    
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
        for (int i = 1; i <= humanPlayersNumber; i++)
        {
            playerNames.add(getNextName(i));
        }

        return playerNames;
    }

    private String getNextName(int playerNumber)
    {
        scanner = new Scanner(System.in);
        System.out.println("Please enter a name for " + playerNumber + " player:");
        String inputName = scanner.nextLine();  
        
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
    
    public void showCurrentPlayerName(String playerName)
    {
        System.out.println("Now " + playerName + "'s turn");
    }

    public void showEvents(List<GameEvent> events) {
        for(GameEvent event : events)
        {
            showEvent(event);
        }
    }

    private void showEvent(GameEvent event) {
            if(event.getEventType() == GameEvent.EventType.MOVE)
            {
                playerMoveEvent(event);
            }
            if(event.getEventType() == GameEvent.EventType.PROMT_PLAYER_TO_BUY_HOUSE)
            {
                promtPlayerToBuyHouse();
            }
    }

    private void playerMoveEvent(GameEvent event) {
          System.out.println("Player " + event.getPlayerName() + " moved to " + 
                  event.getBoardSquareID() + " cell.");
    }

    private void promtPlayerToBuyHouse() {
        //ask player and get result
        boolean answer = false;
        System.out.println("Do you want to buy house, press 1-Yes 2-No");
         
        playerBuyHouseDecision.onAnswer(answer);
        
    }
}
