package logic.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import logic.model.player.ComputerPlayer;
import logic.model.player.HumanPlayer;
import logic.model.player.Player;

public class Engine
{
    private List<Player> players = new ArrayList<>();
    private Board board = new Board();
    private Optional<Player> currentPlayer;

    public Optional<Player> getPlayingPlayer()
    {
        return currentPlayer;
    }

    public void createPlayers(List<String> humanPlayerNames, int computerPlayersCount)
    {
        humanPlayerNames.forEach(name -> players.add(new HumanPlayer(name)));
        createComputerPlayers(computerPlayersCount);
    }

    private void createComputerPlayers(int computerPlayersCount)
    {
        for (int i = 0; i < computerPlayersCount; i++)
        {
            players.add(new ComputerPlayer());
        }
    }

    public void initialize(MonopolyInitReader monopolyInitReader)
    {
    }

    public boolean isStillPlaying()
    {
        return false;
    }
}
