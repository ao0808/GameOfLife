package ru.sbrf.gameOfLifeTest.springTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sbrf.gameOfLife.ApplicationConfig;
import ru.sbrf.gameOfLife.implement.game.AutoGameOfLife;
import ru.sbrf.gameOfLife.interfaces.GameFactory;
import ru.sbrf.gameOfLife.interfaces.PlayingFieldFactory;
import ru.sbrf.gameOfLife.model.PlayingField;

import static ru.sbrf.gameOfLife.implement.general.IO.inputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@ActiveProfiles("AUTO")
public class ErrorGameTest extends Assert {
        @Autowired
        private PlayingField playingField;
        @Autowired
        private PlayingFieldFactory factory;
        @Autowired
        GameFactory autoGameFactory;

        @Test
        public void errorAutoGameTest(){
                String messageException = "Index 10 out of bounds for length 5";
                PlayingField playingFieldSmail = factory.create(inputStream("classpath:smail.txt"));
                AutoGameOfLife autoGame = (AutoGameOfLife) autoGameFactory.create(playingField);
                autoGame.getPlayingField().setCellsMatrix(playingFieldSmail.getCellsMatrix());
                Throwable exception = Assertions.assertThrows(Exception.class, autoGame::start);
                Assertions.assertEquals(messageException, exception.getMessage());
        }

        @Test
        public void errorAutoGameWitheFakeHeightTest(){
                String messageException = "the height ore weight is too small to do a matrix";
                AutoGameOfLife autoGame = (AutoGameOfLife) autoGameFactory.create(playingField);
                autoGame.getPlayingField().setHeight(0);
                Throwable exception = Assertions.assertThrows(Exception.class, autoGame::start);
                assertEquals(messageException, exception.getMessage());
        }
}
