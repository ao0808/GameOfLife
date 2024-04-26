package ru.sbrf.gameOfLife.implement.playingField;

import ru.sbrf.gameOfLife.enums.CellStatus;
import ru.sbrf.gameOfLife.interfaces.PrintField;
import ru.sbrf.gameOfLife.model.Cell;
import ru.sbrf.gameOfLife.model.PlayingField;

public class PrintFieldGameOfLife implements PrintField {
    @Override
    public String printString(PlayingField playingField){
        StringBuilder builder = new StringBuilder();
        Cell[][] cellsMatrix = playingField.getCellsMatrix();
        for(int i = 0; i < playingField.getHeight(); i++){
            appendBuilder(playingField, builder, cellsMatrix, i);
            builder.append("\n");
        }
        return builder.toString().replace(" \n", "\n");
    }

    private void appendBuilder(PlayingField playingField, StringBuilder builder, Cell[][] cellsMatrix, int i) {
        for(int j = 0; j < playingField.getWeight(); j++){
            if(cellsMatrix[i][j].getStatus().equals(CellStatus.LIFE)){
                builder.append("0 ");
            } else {
                builder.append("* ");
            }
        }
    }
}
