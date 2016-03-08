package controller;

import java.util.List;

import logic.engine.Engine;
import view.View;

public class Controller
{
    private static final String PATH = "";
    private View view;
    private Engine engine;

    public Controller(View view, Engine engine)
    {
        this.view = view;
        this.engine = engine;
    }

    public void startGame()
    {
        createPlayers();
        engine.initialize(new XmlMonopolyInitReader(PATH));

        while (engine.isStillPlaying())
        {

        }
    }

    private void createPlayers()
    {
        int humanPlayersNumber = view.getHumanPlayersNumber();
        int computerPlayersNumber = view.getComputerPlayersNumber();
        List<String> humanPlayersNames = view.getHumanPlayerNames(humanPlayersNumber);
        engine.createPlayers(humanPlayersNames, computerPlayersNumber);
    }
}
