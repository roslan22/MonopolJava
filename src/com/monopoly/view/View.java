package com.monopoly.view;

import com.monopoly.logic.events.Event;
import com.monopoly.logic.events.EventType;
import com.monopoly.utils.Utils;
import com.monopoly.view.interfaces.PlayerBuyAssetDecision;
import com.monopoly.view.interfaces.PlayerBuyHouseDecision;
import com.monopoly.view.interfaces.PlayerResign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;


public class View
{
    public static final int MAXIMUM_ANSWERS_ALLOWED_FOR_YES_NO_RESIGN = 3;
    private Scanner scanner = new Scanner(System.in);
    private PlayerBuyHouseDecision playerBuyHouseDecision;
    private PlayerBuyAssetDecision playerBuyAssetDecision;
    private PlayerResign           playerResign;

    public void setPlayerResign(PlayerResign playerResign)
    {
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
        if (maximumAllowed > 0)
        {
            System.out.print("Please enter a number of Computer players: ");
            return getNumberFromUser(maximumAllowed);
        }
        return 0;
    }

    public List<String> getDistinctHumanPlayerNames(int humanPlayersNumber)
    {
        System.out.println("Please enter the names of the human players: ");
        List<String> names = new ArrayList<>();
        IntStream.range(0, humanPlayersNumber).forEach(i -> names.add(getNextName(i + 1, names)));
        return names;
    }

    private String getNextName(int playerNumber, List<String> prevNames)
    {
        String name = getPlayerName(playerNumber);
        while (prevNames.contains(name))
        {
            System.out.println("name already exist");
            name = getPlayerName(playerNumber);
        }
        return name;
    }

    private String getPlayerName(int playerNumber)
    {
        String name;
        System.out.println("Please enter a name for " + playerNumber + " player:");
        scanner = new Scanner(System.in);
        name = scanner.nextLine();
        return name;
    }

    private int getNumberFromUser(int maximumAllowed)
    {
        Integer inputNum = Utils.tryParseInt(scanner.next());

        while (inputNum == null || inputNum > maximumAllowed)
        {
            System.out.println("Bad input format, please try again:");
            inputNum = Utils.tryParseInt(scanner.next());
        }

        return inputNum;
    }

    public void showEvents(Event[] events)
    {
        Arrays.stream(events).forEach(this::showEvent);
    }

    private void showEvent(Event event)
    {
        EventType eventType = event.getEventType();

        switch (eventType)
        {
            case GAME_START:
                showGameStartedMsg();
                break;
            case GAME_OVER:
                showGameOverMsg();
                break;
            case MOVE:
                showPlayerMove(event);
                break;
            case DICE_ROLL:
                showDiceRollResult(event);
                break;
            case GAME_WINNER:
                showGameWinner(event);
                break;
            case PROMPT_PLAYER_TO_BUY_ASSET:
                promptPlayerToBuy(event);
                break;
            case PROMPT_PLAYER_TO_BUY_HOUSE:
                promptPlayerToBuy(event);
                break;
            case GET_OUT_OF_JAIL_CARD:
                showOutOfJailCard(event);
                break;
            case GO_TO_JAIL:
                showGoToJailMsg(event);
                break;
            case ASSET_BOUGHT_MESSAGE:
                showAssetBoughtMsg(event);
                break;
            case HOUSE_BOUGHT_MESSAGE:
                showHouseBoughtMsg(event);
                break;
            case LANDED_ON_START_SQUARE:
                showLandedOnStartSquareMsg(event);
                break;
            case PASSED_START_SQUARE:
                showPassedStartSquareMsg(event);
                break;
            case PAYMENT:
                showPaymentMsg(event);
                break;
            case PLAYER_LOST:
                showPlayerLostMsg(event);
                break;
            case PLAYER_RESIGNED:
                showPlayerResignMsg(event);
                break;
            case PLAYER_USED_OUT_OF_JAIL_CARD:
                showUsedOutOfJailCardMsg(event);
                break;
            case SURPRISE_CARD:
                showSurpriseCardMsg(event);
                break;
            case WARRANT_CARD:
                showWarrantCardMsg(event);
                break;
            default:
                unknownEvent();
        }
    }

    private void showPlayerMove(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    private void promptPlayerToBuy(Event event)
    {
        System.out.println(event.getEventMessage() + "\npress 1-Yes 2-No 3-Resign:");
        handlePlayerChoiceForPrompt(event, isUserWillingToBuy());
    }

    private void handlePlayerChoiceForPrompt(Event event, PlayerChoice playersChoice)
    {
        switch (playersChoice)
        {
            case YES:
                playerBuyHouseDecision.onAnswer(event.getEventID(), true);
                break;
            case NO:
                playerBuyAssetDecision.onAnswer(event.getEventID(), false);
                break;
            case RESIGN:
                playerResign.resign();
                break;
        }
    }

    private PlayerChoice isUserWillingToBuy()
    {
        return PlayerChoice.getValueForChoice(getLegalDecision());
    }

    private int getLegalDecision()
    {
        int decision = getNumberFromUser(MAXIMUM_ANSWERS_ALLOWED_FOR_YES_NO_RESIGN);
        while (!PlayerChoice.isChoiceExists(decision))
        {
            System.out.println("Wrong input, try again:");
            decision = getNumberFromUser(MAXIMUM_ANSWERS_ALLOWED_FOR_YES_NO_RESIGN);
        }
        return decision;
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
        System.out.println(event.getEventMessage());
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
        System.out.println(event.getEventMessage());
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
        System.out.println("Player " + event.getPlayerName() + " used - Out of jail card");
    }

    private void showSurpriseCardMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    private void showWarrantCardMsg(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    private void showOutOfJailCard(Event event)
    {
        System.out.println(event.getEventMessage());
    }

    private void showGoToJailMsg(Event event)
    {
        System.out.println("Player " + event.getPlayerName() + " goes to jail");
    }

    private void unknownEvent()
    {
    }

}