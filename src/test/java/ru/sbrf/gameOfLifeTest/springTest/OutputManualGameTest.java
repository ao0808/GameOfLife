package ru.sbrf.gameOfLifeTest.springTest;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
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
import ru.sbrf.gameOfLife.interfaces.PlayingFieldFactory;
import ru.sbrf.gameOfLife.model.PlayingField;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static ru.sbrf.gameOfLife.implement.general.IO.inputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@ActiveProfiles("MANUAL")
public class OutputManualGameTest {
    @Autowired
    PlayingFieldFactory playingFieldFactory;
    @Autowired
    GameFactory manualGameFactory;
    @Autowired
    private PlayingFieldFactory factory;
    @Test
    public void outputManualTest() throws IOException {
        InputStream sysInBackup = System.in;
        System.setIn(getSequenceInputStream());
        ByteArrayOutputStream outContent = getByteArrayOutputStream();
        PlayingField playingFieldSmail = factory.create(inputStream("classpath:smail.txt"));

        ManualGameOfLife game = (ManualGameOfLife) manualGameFactory.create(factory.create(inputStream("classpath:startPlayingFieldDefault.txt")));
        game.setPlayingField(playingFieldSmail);
        game.start();
        String outOfGame = outContent.toString().replace("\r", "");
        String expectedOutput = IOUtils.toString(inputStream("classpath:testTreeStepSmail.txt"), StandardCharsets.UTF_8)
                .replace("\r", "");
        assertEquals(expectedOutput, outOfGame);

        systemInputAndOutputBackup(sysInBackup);
    }

    @NotNull
    private ByteArrayOutputStream getByteArrayOutputStream() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        return outContent;
    }

    @NotNull
    private SequenceInputStream getSequenceInputStream() {
        Vector<InputStream> inputStreams = new Vector<>();
        threeStep(inputStreams);
        return new SequenceInputStream(inputStreams.elements());
    }

    private void oneStep(Vector<InputStream> inputStreams) {
        ByteArrayInputStream in1 = new ByteArrayInputStream("Q\n".getBytes());
        inputStreams.add(in1);
    }
    private void threeStep(Vector<InputStream> inputStreams) {
        inputStreams.add(new ByteArrayInputStream("1\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("2\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("Q\n".getBytes()));
    }

    private void systemInputAndOutputBackup(InputStream sysInBackup) {
        // Возвращаем System.in к оригинальному потоку ввода
        System.setIn(sysInBackup);
        // Возвращаем System.out к оригинальному потоку вывода
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}
