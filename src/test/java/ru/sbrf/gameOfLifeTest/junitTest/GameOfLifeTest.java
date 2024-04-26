package ru.sbrf.gameOfLifeTest.junitTest;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.sbrf.gameOfLife.implement.gameFactory.GameFactoryAuto;
import ru.sbrf.gameOfLife.implement.gameFactory.GameFactoryManual;
import ru.sbrf.gameOfLife.implement.playingField.FilePlayingFieldFactory;
import ru.sbrf.gameOfLife.interfaces.*;
import ru.sbrf.gameOfLife.model.Cell;
import ru.sbrf.gameOfLife.model.PlayingField;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import static ru.sbrf.gameOfLife.builders.Create.*;
import static ru.sbrf.gameOfLife.enums.CellStatus.DEAD;
import static ru.sbrf.gameOfLife.enums.CellStatus.LIFE;
import static ru.sbrf.gameOfLife.model.PlayingField.builder;
import static ru.sbrf.gameOfLife.implement.general.IO.inputStream;

public class GameOfLifeTest extends Assert {
    @Test
    public void cellIsNotNullTest() {
        Cell[][] generationMatrix = CellMatrix().withHeight(10).withHeight(10).please();
        Cell cell = Cell.builder().status(LIFE).build();
        PlayingField playingField = builder().cellsMatrix(generationMatrix).height(10).weight(10).build();
        assertNotNull(cell);
        assertNotNull(playingField);
    }

    @Test
    public void cellStatusCheck() {
        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        PlayingField playingField = factory.create(inputStream("classpath:startPlayingFieldDefault.txt"));

        assertEquals(LIFE, playingField.getCellsMatrix()[3][5].getStatus());
        assertEquals(DEAD, playingField.getCellsMatrix()[1][1].getStatus());
    }

    @Test
    public void factoryTest() {
        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        PlayingField playingField = factory.create(inputStream("classpath:startPlayingFieldDefault.txt"));

        assertNotNull(playingField.getCellsMatrix());
    }

    @Test
    public void checkPlayingFieldSize() {
        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        PlayingField playingField = factory.create(inputStream("classpath:startPlayingFieldDefault.txt"));

        assertEquals(11, playingField.getHeight());
        assertEquals(11, playingField.getWeight());
    }

    @Test
    public void checkNewField() {
        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        PlayingField playingField = factory.create(inputStream("classpath:startPlayingFieldDefault.txt"));
        PrintField printField = PrintFieldGameOfLife();
        NewPlayingFieldFactory newPlayingFieldFactory = NewPlayingFieldFactory();

        String test_00 = getString(playingField, printField, newPlayingFieldFactory);
        String test_01 = getString(playingField, printField, newPlayingFieldFactory);

        assertEquals(test_00, test_01);
    }

    private String getString(PlayingField playingField, PrintField printField, NewPlayingFieldFactory newPlayingField) {
        return printField.printString(newPlayingField.createNewPlayingField(playingField));
    }

    @Test
    public void checkNewFieldNotEqualsOldField() {
        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        PlayingField playingFieldOld = factory.create(inputStream("classpath:startPlayingFieldDefault.txt"));
        PrintField printField = PrintFieldGameOfLife();
        NewPlayingFieldFactory newPlayingFieldFactory = NewPlayingFieldFactory();

        String test_00 = printField.printString(playingFieldOld);
        PlayingField playingFieldNew = newPlayingFieldFactory.createNewPlayingField(playingFieldOld);
        String test_01 = printField.printString(playingFieldOld);
        String test_02 = printField.printString(playingFieldNew);

        assertEquals(test_00, test_01);
        assertNotEquals(test_00, test_02);
    }

    @Test
    public void manualAndAutoGameTest() {
        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        GameFactory autoGameFactory = new GameFactoryAuto();
        GameFactory manualGameFactory = new GameFactoryManual();
        PlayingField playingField = factory.create(inputStream("classpath:startPlayingFieldDefault.txt"));

        Game manualGame = manualGameFactory.create(playingField);
        Game autoGame = autoGameFactory.create(playingField);

        assertNotNull(manualGame);
        assertNotNull(autoGame);
    }

    @Test
    public void exceptionEmptyFailTest() {
        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        String messageException = "the string is too small to do a matrix";
        Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            PlayingField playingField = factory.create(inputStream("classpath:smallStringTestFail.txt"));
        });
        Assertions.assertEquals(messageException, exception.getMessage());
    }

    @Test
    public void exceptionLineIsTooSmallFailTest() {
        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        String messageException = "the file contains too few rows to form a matrix or fail is empty";
        Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            PlayingField playingField = factory.create(inputStream("classpath:emptyTestFail.txt"));
        });
        Assertions.assertEquals(messageException, exception.getMessage());
    }

    @Test
    public void manualGameOverTest(){
        String expectedOutput = "MANUAL\r\n**** Game Over ****\r\n";
        //Создаем имитацию ввода с консоли
        InputStream sysInBackup = System.in;
        System.setIn(getSequenceInputStream("oneStep"));
        // Создаем поток вывода для перехвата
        ByteArrayOutputStream outContent = getByteArrayOutputStream();

        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        GameFactory manualGameFactory = new GameFactoryManual();
        // Вызываем метод start()
        Game game = manualGameFactory.create(factory.create(inputStream("classpath:startPlayingFieldDefault.txt")));
        game.start();

        assertEquals(expectedOutput, outContent.toString());
        systemInputAndOutputBackup(sysInBackup);
    }

    @SneakyThrows
    @Test
    public void manualFullCycleTest(){
        //Создаем имитацию ввода с консоли
        InputStream sysInBackup = System.in;
        System.setIn(getSequenceInputStream("tenStep"));
        // Создаем поток вывода для перехвата
        ByteArrayOutputStream outContent = getByteArrayOutputStream();

        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        GameFactory manualGameFactory = new GameFactoryManual();
        Game game = manualGameFactory.create(factory.create(inputStream("classpath:startPlayingFieldDefault.txt")));
        game.start();

        String str = outContent.toString().replace("\r", "");
        String expectedOutput = IOUtils.toString(inputStream("classpath:testFullCycle.txt"), StandardCharsets.UTF_8)
                .replace("\r", "");
        assertEquals(expectedOutput, str);

        systemInputAndOutputBackup(sysInBackup);
    }

    @SneakyThrows
    @Test
    public void planerTest() {
        ByteArrayOutputStream outContent = getByteArrayOutputStream();

        PlayingFieldFactory factory = new FilePlayingFieldFactory();
        GameFactory autoGameFactory = new GameFactoryAuto();
        Game game = autoGameFactory.create(factory.create(inputStream("classpath:planerTest.txt")));

        game.start();
        String outputOfGame = outContent.toString().replace("\r", "");
        String expectedOutput = IOUtils.toString(inputStream("classpath:outPlanerTest.txt"), StandardCharsets.UTF_8)
                .replace("\r", "");

        assertEquals(expectedOutput, outputOfGame);
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    @NotNull
    private ByteArrayOutputStream getByteArrayOutputStream() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        return outContent;
    }

    @NotNull
    private SequenceInputStream getSequenceInputStream(String step) {
        Vector<InputStream> inputStreams = new Vector<>();
        if(step.equals("oneStep")){
            oneStep(inputStreams);
        } else {
            manyStep(inputStreams);
        }
        return new SequenceInputStream(inputStreams.elements());
    }

    private void oneStep(Vector<InputStream> inputStreams) {
        ByteArrayInputStream in1 = new ByteArrayInputStream("Q\n".getBytes());
        inputStreams.add(in1);
    }
    private void manyStep(Vector<InputStream> inputStreams) {
        inputStreams.add(new ByteArrayInputStream("1\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("2\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("3\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("4\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("5\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("6\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("7\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("8\n".getBytes()));
        inputStreams.add(new ByteArrayInputStream("Q\n".getBytes()));
    }

    private void systemInputAndOutputBackup(InputStream sysInBackup) {
        // Возвращаем System.in к оригинальному потоку ввода
        System.setIn(sysInBackup);
        // Возвращаем System.out к оригинальному потоку вывода
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}
