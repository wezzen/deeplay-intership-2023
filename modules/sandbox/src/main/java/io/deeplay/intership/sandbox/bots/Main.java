package io.deeplay.intership.sandbox.bots;

import io.deeplay.intership.exception.ServerException;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws ServerException {
        int gamesCount = 100;
        for (int i = 0; i < gamesCount; i++) {
            String startMessage = String.format("Game %d was started", i);
            String endMessage = String.format("Game %d was ended", i);

            log.info(startMessage);
            new Sandbox().startGame();
            log.info(endMessage);
        }
    }
}