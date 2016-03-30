package com.monopoly.logic.engine;

import com.monopoly.EventsManager;
import com.monopoly.GameEvent;
import com.monopoly.Notifier;
import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.CubesResult;
import com.monopoly.logic.model.player.ComputerPlayer;
import com.monopoly.logic.model.player.HumanPlayer;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Engine
{
    public static final int FIRST_PLAYER_INDEX = 0;
    public static final int END_OF_ROUND_MONEY_EARN = 200;
    public static final int MINIMUM_GAME_PLAYERS = 2;

    private List<Player> players = new ArrayList<>();
    private Board  board;
    private Player currentPlayer;
    private CubesResult currentCubeResult = null;
    private EventsManager eventsManager = new EventsManager();
    
    public int getLastEventID() {
        return eventsManager.getLastEventID();
    }
            
    public List<GameEvent> getEvents() {
        return eventsManager.getEvents();
    }
    
    public List<Player> getAllPlayers()
    {
        return players;
    }

    public Player getCurrentPlayer()
    {
        if (currentPlayer == null)
        {
            throw new IllegalStateException("There is no current player. Have you called createPlayers(...) ?");
        }
        return currentPlayer;
    }

    public void createPlayers(List<String> humanPlayerNames, int computerPlayersCount)
    {
        humanPlayerNames = handleDuplicateNames(humanPlayerNames);
        humanPlayerNames.forEach(name -> players.add(new HumanPlayer(name)));
        createComputerPlayers(computerPlayersCount);
        randomizePlayersOrder();
        currentPlayer = players.get(FIRST_PLAYER_INDEX);
    }

    private List<String> handleDuplicateNames(List<String> names)
    {
        List<String> humanPlayerNamesNoDup = new ArrayList<>();
        for (int i = 0; i < names.size(); i++)
        {
            humanPlayerNamesNoDup.add(getNameWithSuffix(names, i));
        }
        return humanPlayerNamesNoDup;
    }

    private String getNameWithSuffix(List<String> names, int nameIndex)
    {
        int occurrencesCounter = 0;
        for (int i = 0; i < nameIndex; i++)
        {
            if (names.get(nameIndex).equals(names.get(i)))
            {
                occurrencesCounter++;
            }
        }
        String nameSuffix = occurrencesCounter > 0 ? String.valueOf(nameIndex) : "";
        return names.get(nameIndex) + nameSuffix;
    }

    private void randomizePlayersOrder()
    {
        Collections.shuffle(players, new Random(System.nanoTime()));
    }

    private void createComputerPlayers(int computerPlayersCount)
    {
        for (int i = 0; i < computerPlayersCount; i++)
        {
            players.add(new ComputerPlayer());
        }
    }

    public void initializeBoard(MonopolyInitReader monopolyInitReader)
    {
        monopolyInitReader.read();
        board = new Board(this,
                          monopolyInitReader.getCells(),
                          monopolyInitReader.getSurpriseCards(),
                          monopolyInitReader.getAlertCards(),
                          monopolyInitReader.getKeyCells());
    }

    public boolean isStillPlaying()
    {
        return players.size() < MINIMUM_GAME_PLAYERS;
    }

    public void throwCubes()
    {
        Random r = new Random(System.nanoTime());
        currentCubeResult =  new CubesResult(r.nextInt(6) + 1, r.nextInt(6) + 1);
        GameEvent throwCubesEvent = new GameEvent(eventsManager.getNewEventId(), GameEvent.EventType.DICE_ROLL);
        throwCubesEvent.setFirstDiceResult(currentCubeResult.getFirstCubeResult());
        throwCubesEvent.setSecondDiceResult(currentCubeResult.getSecondCuberResult());
        eventsManager.addEvent(throwCubesEvent);
    }
    
    public void startGame()
    {
        continueGame();
    }
    
    public void addEventToEventManager(GameEvent event)
    {
        eventsManager.addEvent(event);
    }
    
    public void continueGame()
    {
        while(currentPlayer.getPlayerType() != Player.PlayerType.HUMAN)
        {
           throwCubes();
           movePlayer();
           if(!isGameOver())
           {
            nextPlayer();
           }
        }
        moveHumanPlayer();
    }
    
    public void movePlayer() 
    {   /*  ??????? before paking? *****
        if (isPlayerInJail(currentPlayer))  //No need for current
        {
            if (cr.isDouble())
                takePlayerOutOfJail(currentPlayer);
            else
                return;
        }
        */
        
        if (!isCurrentPlayerInParking())
             board.movePlayer(currentPlayer, currentCubeResult.getResult());
        else
            exitCurrentPlayerFromParking();
    }

    public void playerFinishedARound(Player player)
    {
        player.receiveMoney(END_OF_ROUND_MONEY_EARN);
    }

    private void nextPlayer()
    {
        final int nextPlayerIndex = (players.indexOf(currentPlayer) + 1) % players.size();
        currentPlayer = players.get(nextPlayerIndex);
        //TODO: check if to add event that player changed
    }

    public void putPlayersAtFirstCell()
    {
        players.forEach(player -> player.setCurrentCellDoNotPerform(board.getFirstCell()));
    }

    public boolean isCurrentPlayerInParking()
    {
        return currentPlayer.isParking();
    }

    public boolean isPlayerInJail(Player player)
    {
        return player.isInJail();
    }

    public void exitCurrentPlayerFromParking()
    {
        currentPlayer.exitFromParking();
    }

    public void takePlayerOutOfJail(Player player)
    {
        player.getOutOfJail();
    }

    public void payToEveryoneElse(Player payingPlayer, int amount)
    {
        players.forEach(p -> payingPlayer.payToOtherPlayer(p, amount));

        if (payingPlayer.getMoney() <= 0)
        {
            playerLost(payingPlayer);
        }
    }

    public void transferOtherPlayersMoneyTo(Player player, int amount)
    {
        for (Player p : players)
        {
            p.payToOtherPlayer(player, amount);
        }
        Stream<Player> loosingPlayers = players.stream().filter(p -> p.getMoney() <= 0);
        loosingPlayers.forEach(this::playerLost);
    }

    private void playerLost(Player player)
    {
        players.remove(player);
        board.playerLost(player);
    }

    public Board getBoard() {
        return board;
    }

    public List<GameEvent> getEvents(int playerID, Integer get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void moveHumanPlayer() 
    {
        throwCubes();
        movePlayer();
    }
    
    public void buy(int playerID, int eventID, boolean buy)
    {
        //only on human player
        
        //IMPLEMENT LOGIC
        
        nextPlayer();
        continueGame();
    }
    
    public boolean resign(int playerID)
    {
        Boolean isPlayerFound = false;
        int counter = 0;
        Player player;
        
        while (!isPlayerFound && counter < players.size())
        {
            player = players.get(counter);
            if(player.getPlayerID() == playerID)
            {
                isPlayerFound = true;
                GameEvent resignEvent = new GameEvent(getLastEventID(), 
                        GameEvent.EventType.PLAYER_RESIGNED);
                resignEvent.setPlayerName(player.getName());
                eventsManager.addEvent(resignEvent);
            }
        }
        
        return isPlayerFound;
    }

    private boolean isGameOver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
