package ru.sbrf.gameOfLife;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import ru.sbrf.gameOfLife.builders.Create;
import ru.sbrf.gameOfLife.implement.game.AutoGameOfLife;
import ru.sbrf.gameOfLife.implement.game.ControllerGame;
import ru.sbrf.gameOfLife.implement.game.ManualGameOfLife;
import ru.sbrf.gameOfLife.implement.gameFactory.GameFactoryAuto;
import ru.sbrf.gameOfLife.implement.gameFactory.GameFactoryManual;
import ru.sbrf.gameOfLife.implement.playingField.FilePlayingFieldFactory;
import ru.sbrf.gameOfLife.interfaces.*;
import ru.sbrf.gameOfLife.model.PlayingField;

@Configuration
public class ApplicationConfig {

    @SneakyThrows
    @Bean
    @Scope("prototype")
    public PlayingField playingField(
            @Value("classpath:/startPlayingFieldDefault.txt")
            Resource input, PlayingFieldFactory factory
    ) {
        return factory.create(input.getInputStream());
    }

    @Bean
    public PlayingFieldFactory playingFieldFactory(){
        return new FilePlayingFieldFactory();
    }
    @Bean
    public PrintField printField(){
        return Create.PrintFieldGameOfLife();
    }

    @Bean
    public NewPlayingFieldFactory newPlayingFieldFactory(){
        return Create.NewPlayingFieldFactory();
    }

    @Bean
    @Profile("MANUAL")
    public Game manualGameOfLife(PlayingField playingField, PrintField printField, NewPlayingFieldFactory generation){
        return new ManualGameOfLife(playingField, printField, generation);
    }

    @Bean
    @Profile("AUTO")
    public Game autoGameOfLife(PlayingField playingField, PrintField printField, NewPlayingFieldFactory generation){
        return new AutoGameOfLife(playingField, printField, generation);
    }

    @Bean
    public ControllerGame controllerGame(Game game){
        ControllerGame controller = new ControllerGame();
        controller.setGame(game);
        return controller;
    }

    @Bean
    public GameFactory autoGameFactory(){
        return new GameFactoryAuto();
    }

    @Bean
    public GameFactory manualGameFactory(){
        return new GameFactoryManual();
    }
}
