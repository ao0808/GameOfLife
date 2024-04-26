package ru.sbrf.gameOfLife.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayingField {
    private int livingCells;
    private Cell[][] cellsMatrix;
    private int weight;
    private int height;
}
