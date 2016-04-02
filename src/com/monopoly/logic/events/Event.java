package com.monopoly.logic.events;

public class Event
{

    int       eventID                 = 0;
    int       timeoutCount            = 0;
    EventType eventType               = null;
    String    playerName              = null;
    String    eventMessage            = null;
    int       boardSquareID           = -1;
    int       firstDiceResult         = -1;
    int       secondDiceResult        = -1;
    boolean   playerMove              = false;
    int       nextBoardSquareID       = -1;
    boolean   paymentToOrFromTreasury = false;
    boolean   paymentFromUser         = false;
    String    paymentToPlayerName     = null;
    int       paymentAmount           = 0;

    public Event(int eventID, EventType eventType)
    {
        this.eventID = eventID;
        this.eventType = eventType;

    }

    public Event(int eventID, int timeoutCount, EventType eventType, String playerName, String eventMessage,
                 int boardSquareID, int firstDiceResult, int secondDiceResult, boolean playerMove, int nextBoardSquareID,
                 boolean paymentToOrFromTreasury, boolean paymentFromUser, String paymentToPlayerName, int paymentAmount)
    {
        this.eventID = eventID;
        this.timeoutCount = timeoutCount;
        this.eventType = eventType;
        this.playerName = playerName;
        this.eventMessage = eventMessage;
        this.boardSquareID = boardSquareID;
        this.firstDiceResult = firstDiceResult;
        this.secondDiceResult = secondDiceResult;
        this.playerMove = playerMove;
        this.nextBoardSquareID = nextBoardSquareID;
        this.paymentToOrFromTreasury = paymentToOrFromTreasury;
        this.paymentFromUser = paymentFromUser;
        this.paymentToPlayerName = paymentToPlayerName;
        this.paymentAmount = paymentAmount;
    }

    public int getEventID()
    {
        return eventID;
    }

    public int getTimeoutCount()
    {
        return timeoutCount;
    }

    public void setTimeoutCount(int timeoutCount)
    {
        this.timeoutCount = timeoutCount;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public String getEventMessage()
    {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage)
    {
        this.eventMessage = eventMessage;
    }

    public int getBoardSquareID()
    {
        return boardSquareID;
    }

    public void setBoardSquareID(int boardSquareID)
    {
        this.boardSquareID = boardSquareID;
    }

    public int getFirstDiceResult()
    {
        return firstDiceResult;
    }

    public void setFirstDiceResult(int firstDiceResult)
    {
        this.firstDiceResult = firstDiceResult;
    }

    public int getSecondDiceResult()
    {
        return secondDiceResult;
    }

    public void setSecondDiceResult(int secondDiceResult)
    {
        this.secondDiceResult = secondDiceResult;
    }

    public boolean isPlayerMove()
    {
        return playerMove;
    }

    public void setPlayerMove(boolean playerMove)
    {
        this.playerMove = playerMove;
    }

    public int getNextBoardSquareID()
    {
        return nextBoardSquareID;
    }

    public void setNextBoardSquareID(int nextBoardSquareID)
    {
        this.nextBoardSquareID = nextBoardSquareID;
    }

    public boolean isPaymentToOrFromTreasury()
    {
        return paymentToOrFromTreasury;
    }

    public void setPaymentToOrFromTreasury(boolean paymentToOrFromTreasury)
    {
        this.paymentToOrFromTreasury = paymentToOrFromTreasury;
    }

    public boolean isPaymentFromUser()
    {
        return paymentFromUser;
    }

    public void setPaymentFromUser(boolean paymentFromUser)
    {
        this.paymentFromUser = paymentFromUser;
    }

    public String getPaymentToPlayerName()
    {
        return paymentToPlayerName;
    }

    public void setPaymentToPlayerName(String paymentToPlayerName)
    {
        this.paymentToPlayerName = paymentToPlayerName;
    }

    public int getPaymentAmount()
    {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }

}
