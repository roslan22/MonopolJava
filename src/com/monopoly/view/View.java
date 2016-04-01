package com.monopoly.view;

import com.monopoly.GameEvent;
import com.monopoly.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View
{
    public static final int YES = 1;
    public static final int NO = 2;
    
    private Scanner scanner = new Scanner(System.in);
    private PlayerBuyHouseDecision playerBuyHouseDecision;
    private PlayerBuyAssetDecision playerBuyAssetDecision;
    
    public void setPlayerBuyHouseDecision(PlayerBuyHouseDecision playerBuyHouseDecision) 
    {
        this.playerBuyHouseDecision = playerBuyHouseDecision;
    }
    
    public void setPlayerBuyAssetDecision(PlayerBuyAssetDecision playerBuyAssetDecision) 
    {
        this.playerBuyAssetDecision = playerBuyAssetDecision;
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

    private void showEvent(GameEvent event) 
    {
       GameEvent.EventType eventType = event.getEventType();
       
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
            case PROMT_PLAYER_TO_BUY_ASSET: promtPlayerToBuyAsset(event);
                    break;
            case PROMT_PLAYER_TO_BUY_HOUSE: promtPlayerToBuyHouse(event);
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
            case SUPRISE_CARD: showSupriseCardMsg(event);
                    break;
            case WARRANT_CARD: showWarrantCardMsg(event);
                    break;
            default:  unknownEvent();
        }
                
    }

    private void showPlayerMove(GameEvent event) 
    {
          System.out.println("Player " + event.getPlayerName() + " moved to " + 
                  event.getBoardSquareID() + " cell.");
    }

    private void promtPlayerToBuyHouse(GameEvent event) 
    {
        boolean isUserWillingToBuy = false;
        System.out.println("Do you want to buy " + event.getEventMessage() +  "press 1-Yes 2-No:");
        isUserWillingToBuy = isUserWillingToBuy();
        
        playerBuyHouseDecision.onAnswer(event.getEventID(), isUserWillingToBuy);
    }
    
    private void promtPlayerToBuyAsset(GameEvent event) 
    {
        boolean isUserWillingToBuy = false;
        System.out.println("Do you want to buy " + event.getEventMessage() +  "press 1-Yes 2-No:");
        isUserWillingToBuy = isUserWillingToBuy();
        
        playerBuyAssetDecision.onAnswer(event.getEventID(), isUserWillingToBuy);    
    }
        
    private boolean isUserWillingToBuy() 
    {
        boolean isUserWillingToBuy = false;
        int decision = getNumberFromUser();
        while(decision != YES || decision != NO)
        {
            System.out.println("Wrong input, try again:");
            decision = getNumberFromUser();
        }
        
        if(decision == YES)
        {
            isUserWillingToBuy = true;
        }
        return isUserWillingToBuy;
    }

    private void showGameStartedMsg() 
    {
        System.out.println("Game Started");
    }

    private void showGameOverMsg() 
    {
        System.out.println("Game Over!");
    }

    private void showDiceRollResult(GameEvent event) 
    {
         System.out.println("Dice roll result is:" + event.getFirstDiceResult() 
                 + " " + event.getSecondDiceResult());
    }

    private void showGameWinner(GameEvent event) 
    {
        System.out.println("Game winner is: " + event.getPlayerName());
    }



    private void showAssetBoughtMsg(GameEvent event) 
    {
        System.out.println(event.getEventMessage() + " by " + event.getPlayerName());
    }

    private void showHouseBoughtMsg(GameEvent event) 
    {
        System.out.println(event.getEventMessage() + " by " + event.getPlayerName());
    }

    private void showLandedOnStartSquareMsg(GameEvent event) 
    {
        System.out.println(event.getPlayerName() + " landed on start square");
    }

    private void showPassedStartSquareMsg(GameEvent event) 
    {
        System.out.println("Player " + event.getPlayerName() + " passed start square");
    }

    private void showPaymentMsg(GameEvent event) 
    {
        System.out.println(event.getEventMessage());
    }

    private void showPlayerLostMsg(GameEvent event) 
    {
        System.out.println("Player " + event.getPlayerName() + " passed start square");
    }

    private void showPlayerResignMsg(GameEvent event) 
    {
        System.out.println("Player " + event.getPlayerName() + " resigned");
    }

    private void showUsedOutOfJailCardMsg(GameEvent event) 
    {
        System.out.println("Player " + event.getPlayerName() + " used - Out of "
                + "jail card");
    }

    private void showSupriseCardMsg(GameEvent event) 
    {
        System.out.println("Player " + event.getPlayerName() + "got suprise card: " +
                event.getEventMessage());    
    }

    private void showWarrantCardMsg(GameEvent event) 
    {
        System.out.println(event.getEventMessage());      
    }
    
    private void showOutOfJailCard(GameEvent event) {
        System.out.println("Out of jail card was used by " + event.getPlayerName());      
    }

    private void showGoToJailMsg(GameEvent event) {
        System.out.println("Player " + event.getPlayerName() + " goes to jail");      
    }
    
    private void unknownEvent() {
        //implement
    }

}