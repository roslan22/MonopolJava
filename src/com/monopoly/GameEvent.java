package com.monopoly;

public class GameEvent {
    public static enum EventType {GAME_START, GAME_OVER, GAME_WINNER, PLAYER_RESIGNED, 
    PLAYER_LOST, DICE_ROLL, MOVE, PASSED_START_SQUARE, LANDED_ON_START_SQUARE, GO_TO_JAIL,
    PROMT_PLAYER_TO_BUY_ASSET, PROMT_PLAYER_TO_BUY_HOUSE, ASSET_BOUGHT_MESSAGE, HOUSE_BOUGHT_MESSAGE, 
    SUPRISE_CARD, WARRANT_CARD, GET_OUT_OF_JAIL_CARD, PAYMENT, PLAYER_USED_OUT_OF_JAIL_CARD};

    int eventID = 0;
    int timeoutCount = 0;
    EventType eventType = null;
    String playerName = null;
    String eventMessage = null;
    int boardSquareID = -1;
    int firstDiceResult = -1;
    int secondDiceResult = -1;
    boolean playerMove = false;
    int nextBoardSquareID = -1;
    boolean paymentToOrFromTreasury = false;
    boolean paymentFromUser = false;
    String paymentToPlayerName = null;
    int paymentAmount = 0;
    
    public GameEvent(int eventID, EventType eventType)
    {
        this.eventID = eventID;
        this.eventType = eventType;
        
    }
    
    public int getEventID() {
        return eventID;
    }

    public int getTimeoutCount() {
        return timeoutCount;
    }

    public void setTimeoutCount(int timeoutCount) {
        this.timeoutCount = timeoutCount;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public int getBoardSquareID() {
        return boardSquareID;
    }

    public void setBoardSquareID(int boardSquareID) {
        this.boardSquareID = boardSquareID;
    }

    public int getFirstDiceResult() {
        return firstDiceResult;
    }

    public void setFirstDiceResult(int firstDiceResult) {
        this.firstDiceResult = firstDiceResult;
    }

    public int getSecondDiceResult() {
        return secondDiceResult;
    }

    public void setSecondDiceResult(int secondDiceResult) {
        this.secondDiceResult = secondDiceResult;
    }

    public boolean isPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(boolean playerMove) {
        this.playerMove = playerMove;
    }

    public int getNextBoardSquareID() {
        return nextBoardSquareID;
    }

    public void setNextBoardSquareID(int nextBoardSquareID) {
        this.nextBoardSquareID = nextBoardSquareID;
    }

    public boolean isPaymentToOrFromTreasury() {
        return paymentToOrFromTreasury;
    }

    public void setPaymentToOrFromTreasury(boolean paymentToOrFromTreasury) {
        this.paymentToOrFromTreasury = paymentToOrFromTreasury;
    }

    public boolean isPaymentFromUser() {
        return paymentFromUser;
    }

    public void setPaymentFromUser(boolean paymentFromUser) {
        this.paymentFromUser = paymentFromUser;
    }

    public String getPaymentToPlayerName() {
        return paymentToPlayerName;
    }

    public void setPaymentToPlayerName(String paymentToPlayerName) {
        this.paymentToPlayerName = paymentToPlayerName;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
   
}
