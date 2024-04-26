package ru.sbrf.gameOfLife.interfaces;

import ru.sbrf.gameOfLife.model.PlayingField;

public interface GameFactory {
   Game create(PlayingField playingField);
}
