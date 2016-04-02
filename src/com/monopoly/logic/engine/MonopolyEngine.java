package com.monopoly.logic.engine;

import com.monopoly.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import com.monopoly.logic.engine.monopolyInitReader.MonopolyInitReader;
import com.monopoly.logic.events.Event;
import com.monopoly.logic.events.EventList;
import com.monopoly.logic.model.CubesResult;
import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.ComputerPlayer;
import com.monopoly.logic.model.player.HumanPlayer;
import com.monopoly.logic.model.player.Player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class MonopolyEngine implements Engine
{
    public static final int FIRST_PLAYER_INDEX      = 0;
    public static final int END_OF_ROUND_MONEY_EARN = 200;
    public static final int MINIMUM_GAME_PLAYERS    = 2;

    private List<Player> players = new ArrayList<>();
    private Board  board;
    private Player currentPlayer;
    private CubesResult   currentCubeResult = null;

    private EventList events = new EventList();

    @Override
    public Event[] getEvents(int playerID, int eventID)
    {
        if (eventID < 0 || eventID >= events.size())
        {
            throw new InvalidParameterException("eventID should be between 0 and " + events.size());
        }
        int fromIndex = eventID == 0 ? 0 : eventID + 1;
        return events.getEventsClone().subList(fromIndex, events.size()).toArray(new Event[events.size() - fromIndex]);
    }

    @Override
    public void resign(int playerID)
    {
        Boolean isPlayerFound = false;
        int counter = 0;
        Player player;

        while (!isPlayerFound && counter < players.size())
        {
            player = players.get(counter);
            if (player.getPlayerID() == playerID)
            {
                isPlayerFound = true;
//                Event resignEvent = new EventBuilder( ).setEventID(getLastEventID())
//                        .setEventType(EventType.PLAYER_RESIGNED).createGameEvent();
//                resignEvent.setPlayerName(player.getName());
//                eventsManager.addEvent(resignEvent);
            }
        }
    }

    @Override
    public void buy(int playerID, int eventID, boolean buy)
    {
        //only on human player

        //IMPLEMENT LOGIC

        nextPlayer();
        continueGame();
    }

    @Override
    public void createGame(String gameName, int computerPlayers, int humanPlayers)
    {

    }

    @Override
    public int joinGame(String gameName, String playerName)
    {
        return 0;
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

    public void initializeBoard(MonopolyInitReader monopolyInitReader) throws CouldNotReadMonopolyInitReader
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
        currentCubeResult = new CubesResult(r.nextInt(6) + 1, r.nextInt(6) + 1);
        events.addThrowCubesEvent(currentPlayer, currentCubeResult);
    }

    public void startGame()
    {
        continueGame();
    }

    public void continueGame()
    {
        while (currentPlayer.getPlayerType() != Player.PlayerType.HUMAN)
        {
            throwCubes();
            movePlayer();
            if (!isGameOver())
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
        {
            board.movePlayer(currentPlayer, currentCubeResult.getResult());
        }
        else
        {
            exitCurrentPlayerFromParking();
        }
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

    public Board getBoard()
    {
        return board;
    }

    private void moveHumanPlayer()
    {
        throwCubes();
        movePlayer();
    }

    private boolean isGameOver()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
