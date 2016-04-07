package com.monopoly.view;

import com.monopoly.view.interfaces.PlayerResign;
import com.monopoly.view.interfaces.PlayerBuyHouseDecision;
import com.monopoly.view.interfaces.PlayerBuyAssetDecision;
import com.monopoly.logic.events.Event;
import com.monopoly.logic.events.EventType;
import com.monopoly.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class View
{
    private Scanner scanner = new Scanner(System.in);
    private PlayerBuyHouseDecision playerBuyHouseDecision;
    private PlayerBuyAssetDecision playerBuyAssetDecision;
    private PlayerResign playerResign;

    public void setPlayerResign(PlayerResign playerResign) {
        this.playerResign = playerResign;
    }
    
    public void setPlayerBuyHouseDecision(PlayerBuyHouseDecision playerBuyHouseDecision) 
    {
        this.playerBuyHouseDecision = playerBuyHouseDecision;
    }
    
    public void setPlayerBuyAssetDecision(PlayerBuyAssetDecision playerBuyAssetDecision) 
    {
        this.playerBuyAssetDecision = playerBuyAssetDecision;
    }
    
    public int getHumanPlayersNumber(int maximumAllowed)
    {
        System.out.print("Please enter a number of Human players: ");
        return getNumberFromUser(maximumAllowed);
    }

    public int getComputerPlayersNumber(int maximumAllowed)
    {
        System.out.print("Please enter a number of Computer players: ");
        return getNumberFromUser(maximumAllowed);
    }

    public List<String> getHumanPlayerNames(int humanPlayersNumber)
    {
        System.out.println("Please enter the names of the human players: ");

        List<String> playerNames = new ArrayList<>();        {

        for (int i = 1; i <= humanPlayersNumber; i++)
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
    
    private int getNumberFromUser(int maximumAllowed)
    {
        Integer inputNum = Utils.tryParseInt(scanner.next());
        
        while(inputNum == null || inputNum > maximumAllowed)
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

    public void showEvents(Event[] events) {
        for(Event event : events)
        {
            showEvent(event);
        }
    }

    private void showEvent(Event event)
    {
       EventType eventType = event.getEventType();
       
        switch (eventType) 
        {
            case GAME_START: showGameStartedMsg();
                     break;
            case GAME_OVER:  showGameOverMsg();
                     break;
            case MOVE: showPlayerMove(event);
                     break;
            case DICE_ROLL: showDiceRollResult(event);
                    break;
            case GAME_WINNER: showGameWinner(event);
                    break;
            case PROMPT_PLAYER_TO_BUY_ASSET: promtPlayerToBuyAsset(event);
                    break;
            case PROMPT_PLAYER_TO_BUY_HOUSE: promtPlayerToBuyHouse(event);
                    break;
            case GET_OUT_OF_JAIL_CARD: showOutOfJailCard(event);  
                    break;
            case GO_TO_JAIL: showGoToJailMsg(event);
                    break;
            case ASSET_BOUGHT_MESSAGE: showAssetBoughtMsg(event);
                    break;
            case HOUSE_BOUGHT_MESSAGE: showHouseBoughtMsg(event);
                    break;
            case LANDED_ON_START_SQUARE: showLandedOnStartSquareMsg(event);
                    break;
            case PASSED_START_SQUARE: showPassedStartSquareMsg(event);
                    break;
            case PAYMENT: showPaymentMsg(event);
                    break;
            case PLAYER_LOST: showPlayerLostMsg(event);
                    break;
            case PLAYER_RESIGNED: showPlayerResignMsg(event);
                    break;
            case PLAYER_USED_OUT_OF_JAIL_CARD: showUsedOutOfJailCardMsg(event);
                    break;
            case SURPRISE_CARD: showSupriseCardMsg(event);
                    break;
            case WARRANT_CARD: showWarrantCardMsg(event);
                    break;
            default:  unknownEvent();
        }
                
    }

    private void showPlayerMove(Event event)
    {
          System.out.println("Player " + event.getPlayerName() + " moved to " + 
                  event.getBoardSquareID() + " cell.");
    }

    private void promtPlayerToBuyHouse(Event event)
    {
        PlayerChoice playersChoice;
        System.out.println(event.getEventMessage() +  "press 1-Yes 2-No 3-Resign:");
        playersChoice = isUserWillingToBuy();
        
        if(playersChoice == PlayerChoice.YES)
        {
            playerBuyHouseDecision.onAnswer(event.getEventID(), true);
        } 
        else if (playersChoice == PlayerChoice.NO)
        {
            playerBuyAssetDecision.onAnswer(event.getEventID(), false);    
        }
        else
        {
            playerResign.resing();
        }
    }
    
    private void promtPlayerToBuyAsset(Event event)
    {
        PlayerChoice playersChoice;
        System.out.println(event.getEventMessage() +  "press 1-Yes 2-No 3-Resign:");
        playersChoice = isUserWillingToBuy();
        
        if(playersChoice == PlayerChoice.YES)
        {
            playerBuyAssetDecision.onAnswer(event.getEventID(), true);    
        }
        else if (playersChoice == PlayerChoice.NO)
        {
            playerBuyAssetDecision.onAnswer(event.getEventID(), false);    
        }
        else
        {
            playerResign.resing();
        }
    }
        
    private PlayerChoice isUserWillingToBuy() 
    {
        int maximumAllowed = 3;
        int decision = getNumberFromUser(maximumAllowed);
        while(isWrongChoiceInserted(decision))
        {
            System.out.println("Wrong input, try again:");
            decision = getNumberFromUser(maximumAllowed);
        }
        
        if(PlayerChoice.YES.GetChoice() == decision)
        {
            return PlayerChoice.YES;
        }
        else if(PlayerChoice.NO.GetChoice() == decision)
        {
            return PlayerChoice.NO;
        }
        else
        {
            return PlayerChoice.RESIGN;
        }
    }

    private static boolean isWrongChoiceInserted(int decision) {
        return (PlayerChoice.YES.GetChoice() != decision) &&
               (PlayerChoice.NO.GetChoice() != decision) &&
               (PlayerChoice.RESIGN.GetChoice() != decision);
    }

    private void showGameStartedMsg() 
    {
        System.out.println("Game Started");
    }

    private void showGameOverMsg() 
    {
        System.out.println("Game Over!");
    }

    private void showDiceRollResult(Event event)
    {
         System.out.println(event.getEventMessage());
    }

    private void showGameWinner(Event event)
    {
        System.out.println("Game winner is: " + event.getPlayerName());
    }



    private void showAssetBoughtMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    private void showHouseBoughtMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    private void showLandedOnStartSquareMsg(Event event)
    {
        System.out.println(event.getPlayerName() + " landed on start square");
    }

    private void showPassedStartSquareMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    private void showPaymentMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    private void showPlayerLostMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    private void showPlayerResignMsg(Event event)
    {
        System.out.println("Player " + event.getPlayerName() + " resigned");
    }

    private void showUsedOutOfJailCardMsg(Event event)
    {
        System.out.println("Player " + event.getPlayerName() + " used - Out of "
                + "jail card");
    }

    private void showSupriseCardMsg(Event event)
    {
        System.out.println("Player " + event.getPlayerName() + "got suprise card: " +
                event.getEventMessage());    
    }

    private void showWarrantCardMsg(Event event)
    {
        System.out.println(event.getEventMessage());      
    }
    
    private void showOutOfJailCard(Event event) {
        System.out.println("Out of jail card was used by " + event.getPlayerName());      
    }

    private void showGoToJailMsg(Event event) {
        System.out.println("Player " + event.getPlayerName() + " goes to jail");      
    }
    
    private void unknownEvent() {
        //implement
    }

}