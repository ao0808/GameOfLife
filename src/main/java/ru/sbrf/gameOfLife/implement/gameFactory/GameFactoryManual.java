package ru.sbrf.gameOfLife.implement.gameFactory;

import ru.sbrf.gameOfLife.implement.playingField.NewPlayingFieldFactoryGameOfLife;
import ru.sbrf.gameOfLife.implement.playingField.PrintFieldGameOfLife;
import ru.sbrf.gameOfLife.interfaces.Game;
import ru.sbrf.gameOfLife.interfaces.GameFactory;
import ru.sbrf.gameOfLife.model.PlayingField;

import static ru.sbrf.gameOfLife.implement.game.ManualGameOfLife.*;

public class GameFactoryManual implements GameFactory {
    @Override
    public Game create(PlayingField playingField) {
        return builder().printField(new PrintFieldGameOfLife()).
                generation(new NewPlayingFieldFactoryGameOfLife()).playingField(playingField).build();
    }
}
