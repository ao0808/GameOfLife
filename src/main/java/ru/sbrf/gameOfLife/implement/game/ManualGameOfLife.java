package ru.sbrf.gameOfLife.implement.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbrf.gameOfLife.interfaces.Game;
import ru.sbrf.gameOfLife.interfaces.NewPlayingFieldFactory;
import ru.sbrf.gameOfLife.interfaces.PrintField;
import ru.sbrf.gameOfLife.model.PlayingField;

import java.util.Scanner;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ManualGameOfLife implements Game {
    private PlayingField playingField;
    private PrintField printField;
    private NewPlayingFieldFactory generation;

    private final Logger LOGGER = LoggerFactory.getLogger(ManualGameOfLife.class);

    @Override
    public void start() {
        System.out.println("MANUAL");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.nextLine().equals("Q") && playingField.getLivingCells() > 0) {
            playingField = generation.createNewPlayingField(playingField);
            System.out.print(printField.printString(playingField));
//            LOGGER.info("Living cells: {}", playingField.getLivingCells());
            System.out.println("Living cells: " + playingField.getLivingCells());
        }
        System.out.println("**** Game Over ****");
    }
}
