package ru.sbrf.gameOfLife.implement.general;

import lombok.SneakyThrows;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;

public class IO {
    @SneakyThrows
    public static InputStream inputStream(String location) {
        ResourceLoader loader = new DefaultResourceLoader();
        return loader.getResource(location).getInputStream();
    }
}
