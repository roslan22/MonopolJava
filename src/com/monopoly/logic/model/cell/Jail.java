package com.monopoly.logic.model.cell;

import com.monopoly.logic.model.board.Board;
import com.monopoly.logic.model.player.Player;

import java.util.HashSet;
import java.util.Set;

public class Jail extends Cell
{
    Set<Player> playersInJail = new HashSet<>();
    private Board board;

    @Override
    public void perform(Player player) {}

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public void putInJail(Player player)
    {
        movePlayerToJail(player);
        lockPlayerInJail(player);
    }

    private void lockPlayerInJail(Player player)
    {
        if (!player.hasOutOfJailCard())
        {
            playersInJail.add(player);
        }
        else
        {
            board.addPlayerUsedOutOfJailCard(player);
            player.returnOutOfJailCardToPack();
        }
    }

    private void movePlayerToJail(Player player)
    {
        board.addMovePlayerToJailEvent(player);
        player.setCurrentCellDoNotPerform(this);
    }

    @Override
    public boolean isInJail(Player player)
    {
        return playersInJail.contains(player);
    }

    public void getPlayerOutOfJail(Player player)
    {
        if (playersInJail.contains(player))
        {
            playersInJail.remove(player);
        }
    }

    @Override
    public CellType getType()
    {
        return CellType.JAIL;
    }
}
