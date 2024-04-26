package ru.sbrf.gameOfLife.implement.playingField;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import ru.sbrf.gameOfLife.enums.CellStatus;
import ru.sbrf.gameOfLife.interfaces.PlayingFieldFactory;
import ru.sbrf.gameOfLife.model.Cell;
import ru.sbrf.gameOfLife.model.PlayingField;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FilePlayingFieldFactory implements PlayingFieldFactory {

    @SneakyThrows
    @Override
    public PlayingField create(InputStream input) {
        String text = IOUtils.toString(input, StandardCharsets.UTF_8);
        int height = searchHeight(text);
        int weight = searchWeight(text);
        Cell[][] cellsMatrix = createCellMatrix(text, weight, height);
        int livingCells = searchLivingCells(weight, height, cellsMatrix);
        return PlayingField.builder().cellsMatrix(cellsMatrix).height(height).weight(weight).livingCells(livingCells).build();
    }

    private int searchLivingCells(int weight, int height, Cell[][] cellsMatrix) {
        int res = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if (cellsMatrix[i][j].getStatus().equals(CellStatus.LIFE)) {
                    res++;
                }
            }
        }
        return res;
    }

    private Cell[][] createCellMatrix(String text, int weight, int height) {
        Cell[][] cellsMatrix = new Cell[height][weight];
        parsFailInputToMatrix(text, height, weight, cellsMatrix);
        return cellsMatrix;
    }

    private void parsFailInputToMatrix(String text, int height, int weight, Cell[][] cellsMatrix) {
        String[] lines = text.split("\n");
        for (int i = 0; i < height; i++) {
            String[] cells = lines[i].split("\\s+");
            for (int j = 0; j < weight; j++) {
                SetCell(cellsMatrix, i, cells, j);
            }
        }
    }

    private void SetCell(Cell[][] cellsMatrix, int i, String[] cells, int j) {
        if(cells[j].equals("0")) cellsMatrix[i][j] = Cell.builder().status(CellStatus.LIFE).build();
        else {
            cellsMatrix[i][j] = Cell.builder().status(CellStatus.DEAD).build();
        }
    }

    @SneakyThrows
    private int searchHeight(String text) {
        int res = text.split("\n").length;;
        if(res < 4)
            throw new Exception("the file contains too few rows to form a matrix or fail is empty");
        return res;
    }

    @SneakyThrows
    private int searchWeight(String text) {
        int i = 0;
        int countSpaces = 0;
        if (text.contains("\n")){
            countSpaces = getCountSpaces(text, i, countSpaces);
        } else {
            throw new Exception("the string is too small to do a matrix");
        }
        if (countSpaces < 4)
            throw new Exception("the string is too small to do a matrix");
        return countSpaces;
    }

    private int getCountSpaces(String text, int i, int countSpaces) {
        while (text.charAt(i) != '\n') {
            if (text.charAt(i) == '0' || text.charAt(i) == '*') {
                countSpaces++;
            }
            i++;
        }
        return countSpaces;
    }
}
