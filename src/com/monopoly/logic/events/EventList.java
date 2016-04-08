package com.monopoly.logic.events;

import com.monopoly.logic.model.CubesResult;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class EventList
{
    private int         eventIdSequence = -1;
    private List<Event> events          = new ArrayList<>();

    public List<Event> getEventsClone()
    {
        return new ArrayList<>(events);
    }

    public void addThrowCubesEvent(Player player, CubesResult cubesResult)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.DICE_ROLL).setPlayerName(player.getName())
                .setEventMessage(player.getName() + " cube throw result:" + cubesResult.toString())
                .setFirstDiceResult(cubesResult.getFirstCubeResult()).setSecondDiceResult(cubesResult.getSecondCuberResult())
                .createGameEvent();
        events.add(e);
    }

    private int getAndIncrementNextEventID()
    {
        eventIdSequence++;
        return eventIdSequence;
    }

    public int size()
    {
        return events.size();
    }

    public void addGameStartEvent()
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.GAME_START).setEventMessage("Game has started")
                .createGameEvent();
        events.add(e);
    }

    public void addMovePlayerEvent(Player player, int from, int to, String destinationName)
    {
        String eventMessage = player.getName();
        eventMessage += from == to ? " stayed at " + destinationName : " moved from " + from + " to " + to + ", " + destinationName;
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.MOVE).setEventMessage(eventMessage)
                .setPlayerName(player.getName()).setPlayerMove(from != to).setBoardSquareID(from).setNextBoardSquareID(to)
                .createGameEvent();
        events.add(e);
    }

    public void addPlayerPassedStartEvent(Player player, int paymentAmount)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.PASSED_START_SQUARE)
                .setPlayerName(player.getName())
                .setEventMessage(player.getName() + " has passed the start cell and got money")
                .setPaymentToOrFromTreasury(true).setPaymentToPlayerName(player.getName()).setPaymentAmount(paymentAmount)
                .createGameEvent();
        events.add(e);
    }

    public void addPlayerLostEvent(Player player)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.PLAYER_LOST)
                .setEventMessage(player.getName() + " lost").setPlayerName(player.getName()).createGameEvent();
        events.add(e);
    }

    public void addGameWinnerEvent(Player player)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.GAME_WINNER).setPlayerName(player.getName())
                .setEventMessage(player.getName() + " has won the game").createGameEvent();
        events.add(e);
    }

    public void addGameOver()
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.GAME_OVER).setEventMessage("Game has finished")
                .createGameEvent();
        events.add(e);
    }

    public void addPlayerResignEvent(Player player)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.PLAYER_RESIGNED)
                .setEventMessage(player.getName() + " has resigned").setPlayerName(player.getName()).createGameEvent();
        events.add(e);
    }

    public void addGoToJailEvent(Player player)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.GO_TO_JAIL).setPlayerName(player.getName()).
                setEventMessage(player.getName() + " went to jail").createGameEvent();
        events.add(e);
    }

    public void addSurpriseCardEvent(Player player, String cardText)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.SURPRISE_CARD).setPlayerName(player.getName())
                .setEventMessage(player.getName() + " got surprise card: " + cardText).createGameEvent();
        events.add(e);
    }

    public void addAlertCardEvent(Player player, String cardText)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.WARRANT_CARD).setPlayerName(player.getName())
                .setEventMessage(player.getName() + " got warning card: " + cardText).createGameEvent();
        events.add(e);
    }

    public void addOutOfJailEvent(Player player)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.GET_OUT_OF_JAIL_CARD)
                .setPlayerName(player.getName()).
                        setEventMessage(player.getName() + " got out of jail card").createGameEvent();
        events.add(e);
    }

    public void addPlayerUsedOutOfJailCard(Player player)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.PLAYER_USED_OUT_OF_JAIL_CARD)
                .setPlayerName(player.getName()).setEventMessage(player.getName() + " used out of jail card")
                .createGameEvent();
        events.add(e);
    }

    public void addLandedOnStartSquareEvent(Player player)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.LANDED_ON_START_SQUARE)
                .setPlayerName(player.getName()).setEventMessage(player.getName() + " has landed on start square")
                .createGameEvent();
        events.add(e);
    }

    public void addPromptPlayerToBuyAssetEvent(Player player, String propertyGroupName, String propertyName, int price)
    {
        String eventMessage = player
                .getName() + " would you like to buy " + propertyName + " in " + propertyGroupName + " for ₪" + price + "? You now own ₪" + player
                .getMoneyAmount();
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.PROMPT_PLAYER_TO_BUY_ASSET)
                .setEventMessage(eventMessage).setPlayerName(player.getName()).createGameEvent();
        events.add(e);
    }

    public void addPromptPlayerToBuyHouseEvent(Player player, String cityName, int housePrice)
    {
        String eventMessage = player.getName() + " would you like to buy house in " + cityName + " for ₪" + housePrice +
                "? You now own ₪" + player.getMoneyAmount();
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.PROMPT_PLAYER_TO_BUY_HOUSE)
                .setEventMessage(eventMessage).createGameEvent();
        events.add(e);
    }

    public void addHouseBoughtEvent(Player player, String cityName)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.HOUSE_BOUGHT_MESSAGE)
                .setPlayerName(player.getName()).setEventMessage(player.getName() + " bought house in" + cityName)
                .createGameEvent();
        events.add(e);
    }

    public void addAssertBoughtEvent(Player player, String assetName)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.ASSET_BOUGHT_MESSAGE)
                .setPlayerName(player.getName()).setEventMessage(player.getName() + " bought " + assetName)
                .createGameEvent();
        events.add(e);
    }

    public void addPayToBankEvent(Player player, int amount)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.PAYMENT).setPlayerName(player.getName())
                .setEventMessage(player.getName() + " payed ₪" + amount + " to bank. Now owns ₪" + player.getMoneyAmount())
                .setPaymentToOrFromTreasury(true).setPaymentAmount(amount).setPaymentFromUser(true).createGameEvent();
        events.add(e);
    }

    public void addPaymentFromBankEvent(Player player, int amount)
    {
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.PAYMENT).setPlayerName(player.getName())
                .setEventMessage("Bank payed ₪" + amount + " to " + player.getName() + ". Now owns ₪" + player
                        .getMoneyAmount()).setPaymentToOrFromTreasury(true).setPaymentAmount(amount)
                .setPaymentFromUser(false).createGameEvent();
        events.add(e);
    }

    public void addPayToOtherPlayerEvent(Player payingPlayer, Player payedPlayer, int amount)
    {
        String eventMessage = payingPlayer.getName() + " payed ₪" + amount + " to " + payedPlayer.getName() +
                ". " + payingPlayer.getName() + ": ₪" + payingPlayer.getMoneyAmount() +
                ". " + payedPlayer.getName() + ": ₪" + payedPlayer.getMoneyAmount();
        Event e = new EventBuilder(getAndIncrementNextEventID(), EventType.PAYMENT).setPlayerName(payingPlayer.getName())
                .setEventMessage(eventMessage)
                .setPaymentToOrFromTreasury(false).setPaymentAmount(amount).setPaymentFromUser(true)
                .setPaymentToPlayerName(payedPlayer.getName()).createGameEvent();
        events.add(e);
    }
}
