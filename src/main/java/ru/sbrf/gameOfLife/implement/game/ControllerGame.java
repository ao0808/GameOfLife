package ru.sbrf.gameOfLife.implement.game;

import ru.sbrf.gameOfLife.interfaces.Game;

public class ControllerGame implements Game {
    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }
    @Override
    public void start(){
        game.start();
    }
}
