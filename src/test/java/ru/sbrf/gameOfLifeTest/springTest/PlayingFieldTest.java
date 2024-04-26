package ru.sbrf.gameOfLifeTest.springTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sbrf.gameOfLife.ApplicationConfig;
import ru.sbrf.gameOfLife.interfaces.NewPlayingFieldFactory;
import ru.sbrf.gameOfLife.interfaces.PlayingFieldFactory;
import ru.sbrf.gameOfLife.interfaces.PrintField;
import ru.sbrf.gameOfLife.model.PlayingField;

import static org.mockito.Mockito.when;
import static ru.sbrf.gameOfLife.implement.general.IO.inputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@ActiveProfiles("AUTO")
public class PlayingFieldTest extends Assert {

    @Autowired
    private PlayingFieldFactory factory;

    @Autowired
    private NewPlayingFieldFactory newPlayingFieldFactory;

    @Autowired
    private PrintField printField;

    @Autowired
    private PlayingField playingField;

    @Mock
    private PlayingField playingFieldMok;

    @Test
    public void test() {
        when(playingFieldMok.getHeight()).thenReturn(11);
        when(playingFieldMok.getWeight()).thenReturn(11);
        when(playingFieldMok.getLivingCells()).thenReturn(18);

        assertEquals(playingFieldMok.getHeight(), playingField.getHeight());
        assertEquals(playingFieldMok.getWeight(), playingField.getWeight());
        assertEquals(playingFieldMok.getLivingCells(), playingField.getLivingCells());
    }

    @Test
    public void checkNewFieldNotEqualsOldField() {
        PlayingField playingFieldOld = factory.create(inputStream("classpath:startPlayingFieldDefault.txt"));

        String test_00 = printField.printString(playingFieldOld);
        PlayingField playingFieldNew = newPlayingFieldFactory.createNewPlayingField(playingFieldOld);

        String test_01 = printField.printString(playingFieldOld);
        String test_02 = printField.printString(playingFieldNew);
        assertEquals(test_00, test_01);
        assertNotEquals(test_00, test_02);
    }
}