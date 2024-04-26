package ru.sbrf.gameOfLife.interfaces;

import ru.sbrf.gameOfLife.model.PlayingField;

public interface NewPlayingFieldFactory {
    PlayingField createNewPlayingField(PlayingField playingField);
}
