package ru.sbrf.gameOfLife.builders.cellMatrixBuilder;
import ru.sbrf.gameOfLife.model.Cell;

public class CellMatrixBuilder {
    private int weight;
    private int height;

    public CellMatrixBuilder withHeight(int height){
        this.height = height;
        return this;
    }

    public CellMatrixBuilder withWeight(int weight){
        this.weight = weight;
        return this;
    }
    public Cell[][] please(){
        return new Cell[height][weight];
    }
}
