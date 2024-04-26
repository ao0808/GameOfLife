package ru.sbrf.gameOfLife.builders;

import ru.sbrf.gameOfLife.builders.cellMatrixBuilder.CellMatrixBuilder;
import ru.sbrf.gameOfLife.builders.newPlayngFieldFactory.NewPlayingFieldFactoryBuilder;
import ru.sbrf.gameOfLife.builders.printFieldGameOfLifeBuilder.PrintFieldGameOfLifeBuilder;
import ru.sbrf.gameOfLife.implement.playingField.PrintFieldGameOfLife;
import ru.sbrf.gameOfLife.interfaces.NewPlayingFieldFactory;

public class Create {
    public static PrintFieldGameOfLife PrintFieldGameOfLife(){
        return PrintFieldGameOfLifeBuilder.please();
    }

    public static CellMatrixBuilder CellMatrix(){
        return new CellMatrixBuilder();
    }

    public static NewPlayingFieldFactory NewPlayingFieldFactory(){
        return NewPlayingFieldFactoryBuilder.please();
    }
}

