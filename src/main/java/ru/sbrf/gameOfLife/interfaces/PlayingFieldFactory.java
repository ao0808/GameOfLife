package ru.sbrf.gameOfLife.interfaces;

import ru.sbrf.gameOfLife.model.PlayingField;

import java.io.InputStream;

public interface PlayingFieldFactory {
    PlayingField create(InputStream input);
}
