package ru.sbrf.gameOfLife.implement.playingField;

import lombok.SneakyThrows;
import ru.sbrf.gameOfLife.interfaces.NewPlayingFieldFactory;
import ru.sbrf.gameOfLife.model.Cell;
import ru.sbrf.gameOfLife.model.PlayingField;

import static ru.sbrf.gameOfLife.builders.Create.CellMatrix;
import static ru.sbrf.gameOfLife.enums.CellStatus.DEAD;
import static ru.sbrf.gameOfLife.enums.CellStatus.LIFE;
import static ru.sbrf.gameOfLife.model.PlayingField.*;

public class NewPlayingFieldFactoryGameOfLife implements NewPlayingFieldFactory {

    @SneakyThrows
    @Override
    public PlayingField createNewPlayingField(PlayingField playingFieldOld) {
        int height = playingFieldOld.getHeight();
        int weight = playingFieldOld.getWeight();
        if (height < 4 || weight < 4){
            throw new Exception("the height ore weight is too small to do a matrix");
        }
        Cell[][] newMatrix = createNewMatrix(playingFieldOld, height, weight);
        int livingCells = getLivingCells(newMatrix, height, weight);
        return builder().cellsMatrix(newMatrix).height(height).weight(weight).
                livingCells(livingCells).build();
    }

    private Cell[][] createNewMatrix(PlayingField playingFieldOld, int height, int weight) {
        Cell[][] newGameField = CellMatrix().withHeight(height).withWeight(weight).please();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                int aliveNeighbors = searchAliveNeighbors(playingFieldOld, height, weight, i, j);
                setNewStatusCell(playingFieldOld, newGameField, i, j, aliveNeighbors);
            }
        }
        return newGameField;
    }

    private void setNewStatusCell(PlayingField playingFieldOld, Cell[][] newGameField, int i, int j, int aliveNeighbors) {
        if (playingFieldOld.getCellsMatrix()[i][j].getStatus().equals(LIFE) && (aliveNeighbors == 2 || aliveNeighbors == 3)) {
            newGameField[i][j] = Cell.builder().status(LIFE).build(); // Ячейка остается живой
        } else if (!playingFieldOld.getCellsMatrix()[i][j].getStatus().equals(LIFE) && aliveNeighbors == 3) {
            newGameField[i][j] = Cell.builder().status(LIFE).build(); // Ячейка становится живой
        } else {
            newGameField[i][j] = Cell.builder().status(DEAD).build(); // Ячейка умирает
        }
    }

    private int searchAliveNeighbors(PlayingField playingFieldOld, int height, int weight, int i, int j) {
        int aliveNeighbors = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                aliveNeighbors = getAliveNeighbors(playingFieldOld, height, weight, i, j, aliveNeighbors, x, y);
            }
        }
        return aliveNeighbors;
    }

    private int getAliveNeighbors(PlayingField playingFieldOld, int height, int weight, int i, int j, int aliveNeighbors, int x, int y) {
        if (x != 0 || y != 0) {
            int ni = (i + x + height) % height;
            int nj = (j + y + weight) % weight;
            if(playingFieldOld.getCellsMatrix()[ni][nj].getStatus().equals(LIFE)){
                aliveNeighbors++;
            }
        }
        return aliveNeighbors;
    }


    public int getLivingCells(Cell[][] newMatrix, int height, int weight) {
        int res = 0;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < weight; j++)
                if (newMatrix[i][j].getStatus().equals(LIFE))
                    res++;
        return res;
    }
}
