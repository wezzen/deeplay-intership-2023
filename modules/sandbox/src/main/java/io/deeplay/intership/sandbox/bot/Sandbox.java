package io.deeplay.intership.sandbox.bot;

import io.deeplay.intership.bot.random.RandomBot;
import io.deeplay.intership.decision.maker.GoPlayer;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.ui.terminal.Display;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Sandbox {
    private static final Logger log = Logger.getLogger(Sandbox.class);
    private final Display display = new Display();

    private final GoPlayer blackBot;
    private final GoPlayer whiteBot;

    public static void main(String[] args) throws IOException, ClientException {
        int gamesCount = 100;
        for (int i = 0; i < gamesCount; i++) {
            String startMessage = String.format("Game %d was started", i);
            String endMessage = String.format("Game %d was ended", i);

            log.info(startMessage);
            new Sandbox().startGame();
            log.info(endMessage);
        }
    }

    public Sandbox() throws IOException {
        blackBot = new RandomBot("Bot black", Color.BLACK);
        whiteBot = new RandomBot("Bot white", Color.WHITE);
    }

    public void startGame() throws ClientException {

    }

    private String createGame() throws ClientException {
        return null;
    }
}
