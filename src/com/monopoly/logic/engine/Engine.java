package com.monopoly.logic.engine;

import com.monopoly.logic.model.Board;
import com.monopoly.logic.model.CubesResult;
import com.monopoly.logic.model.player.ComputerPlayer;
import com.monopoly.logic.model.player.HumanPlayer;
import com.monopoly.logic.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Engine
{
    public static final int FIRST_PLAYER_INDEX = 0;
    public static final int END_OF_ROUND_MONEY_EARN = 200;

    private List<Player> players = new ArrayList<>();
    private Board board;
    private Player currentPlayer;

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
        humanPlayerNames.forEach(name -> players.add(new HumanPlayer(name)));
        createComputerPlayers(computerPlayersCount);
        randomizePlayersOrder();
        currentPlayer = players.get(FIRST_PLAYER_INDEX);
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

    public void initializeBoard(MonopolyInitSettings monopolyInitSettings)
    {
        board = new Board(this, monopolyInitSettings.getCells());
    }

    public boolean isStillPlaying()
    {
        return players.size() < 2;
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
}
