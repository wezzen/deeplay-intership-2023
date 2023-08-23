package io.deeplay.intership.lobby.bots;

import io.deeplay.intership.exception.ServerException;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);
    private static final String PROPERTIES_PATH = "src/main/resources/game.properties";

    public static void main(String[] args) throws ServerException {
        int gamesCount = 10;
        final Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_PATH)) {
            property.load(fis);
            gamesCount = Integer.parseInt(property.getProperty("game.count"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Lobby adapter = new Lobby();
        adapter.startGame();
        for (int i = 0; i < gamesCount; i++) {
            log.info("Game " + i + "was started");
            new Lobby().startGame();
            log.info("Game " + i + "was ended");
        }

    }
}