package ru.sbrf.gameOfLife.implement.game;

import lombok.*;
import ru.sbrf.gameOfLife.interfaces.Game;
import ru.sbrf.gameOfLife.interfaces.NewPlayingFieldFactory;
import ru.sbrf.gameOfLife.interfaces.PrintField;
import ru.sbrf.gameOfLife.model.PlayingField;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AutoGameOfLife implements Game {
    private PlayingField playingField;
    private PrintField printer;
    private NewPlayingFieldFactory generation;

    @Override
    @SneakyThrows
    public void start() {
        System.out.println("AUTO");
        int i = 0;
        while (i < 15 && playingField.getLivingCells() > 0) {
            playingField = generation.createNewPlayingField(playingField);
            System.out.print(printer.printString(playingField));
            i++;
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("**** Game Over ****");
    }
}
