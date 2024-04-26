package ru.sbrf.gameOfLifeTest.springTest;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sbrf.gameOfLife.ApplicationConfig;
import ru.sbrf.gameOfLife.implement.game.ManualGameOfLife;
import ru.sbrf.gameOfLife.interfaces.Game;
import ru.sbrf.gameOfLife.interfaces.GameFactory;
import ru.sbrf.gameOfLife.interfaces.NewPlayingFieldFactory;
import ru.sbrf.gameOfLife.interfaces.PlayingFieldFactory;
import ru.sbrf.gameOfLife.model.PlayingField;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static ru.sbrf.gameOfLife.implement.general.IO.inputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@ActiveProfiles("AUTO")
public class OutputAutoGameTest extends Assert {

    @Autowired
    PlayingFieldFactory playingFieldFactory;
    @Autowired
    GameFactory autoGameFactory;
    @Autowired
    private PlayingFieldFactory factory;

    @Test
    public void outputTest() throws IOException {
        ByteArrayOutputStream outContent = getByteArrayOutputStream();
        PlayingField playingFieldSmail = factory.create(inputStream("classpath:smail.txt"));
        Game game = autoGameFactory.create(playingFieldSmail);
        game.start();
        String outputGame = outContent.toString().replace("\r", "");
        String expectedOutput = IOUtils.toString(inputStream("classpath:outputAutoSmail.txt"), StandardCharsets.UTF_8)
                .replace("\r", "");
        assertEquals(expectedOutput, outputGame);

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    private ByteArrayOutputStream getByteArrayOutputStream() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        return outContent;
    }

}
