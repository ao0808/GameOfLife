package ru.sbrf.gameOfLife.model;

import lombok.Builder;
import lombok.Data;
import ru.sbrf.gameOfLife.enums.CellStatus;


@Data
@Builder
public class Cell {
    private CellStatus status;
}
