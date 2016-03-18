package com.monopoly.logic.engine;

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
    private Notifier<String> currentPlayerNotifier = new Notifier<String>();
    private List<GameEvent> events = new ArrayList<>();
    private int lastEventID = 0;

    
    public int getLastEventID() {
        return lastEventID;
    }
            
    public List<GameEvent> getEvents() {
        return events;
    }
    
    public void addEventToEventList(GameEvent event)
    {
        lastEventID++;
        events.add(event);
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

    public CubesResult throwCubes()
    {
        Random r = new Random(System.nanoTime());
        return new CubesResult(r.nextInt(6) + 1, r.nextInt(6) + 1);
    }

    public void movePlayer(Player player, int result)
    {
        board.movePlayer(player, result);
    }

    public void playerFinishedARound(Player player)
    {
        player.receiveMoney(END_OF_ROUND_MONEY_EARN);
    }

    public void nextPlayer()
    {
        final int nextPlayerIndex = (players.indexOf(currentPlayer) + 1) % players.size();
        currentPlayer = players.get(nextPlayerIndex);
        onPlayerChange();
    }

    public void putPlayersAtFirstCell()
    {
        players.forEach(player -> player.setCurrentCellDoNotPerform(board.getFirstCell()));
    }

    public boolean isPlayerInParking(Player player)
    {
        return player.isParking();
    }

    public boolean isPlayerInJail(Player player)
    {
        return player.isInJail();
    }

    public void exitPlayerFromParking(Player player)
    {
        player.exitFromParking();
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
    
    private void onPlayerChange()
    {
        currentPlayerNotifier.doNotify(currentPlayer.getName());
    } 

    public void doAction(int actionNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<GameEvent> getEvents(int playerID, Integer get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onPlayerBuyHouseAnswer(boolean answer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
