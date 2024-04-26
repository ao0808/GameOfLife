package ru.sbrf.gameOfLife;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.sbrf.gameOfLife.implement.game.ControllerGame;
import ru.sbrf.gameOfLife.implement.game.ManualGameOfLife;
import ru.sbrf.gameOfLife.interfaces.Game;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManualGameOfLife.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ApplicationConfig.class)
                .profiles("AUTO")
                .run(args);
        Game game = context.getBean(ControllerGame.class);
        game.start();
    }
}
