package io.deeplay.intership.sandbox.bot;

import io.deeplay.intership.bot.RandomBot;
import io.deeplay.intership.client.Client;
import io.deeplay.intership.dto.response.ActionDtoResponse;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.terminal.Display;
import org.apache.log4j.Logger;

import java.util.UUID;

public class Sandbox {
    private static final Logger log = Logger.getLogger(Sandbox.class);
    private final Client blackBot;
    private String blackBotToken;
    private final Client whiteBot;
    private String whiteBotToken;
    private int runGame;

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

    public Sandbox() throws ServerException {
        final String host = "localhost";
        final int port = 5000;
        this.runGame = 0;
        blackBot = new Client(new Display(), new RandomBot(Color.BLACK), host, port);
        whiteBot = new Client(new Display(), new RandomBot(Color.BLACK), host, port);
    }

    public void startGame() throws ServerException {

    }

    private RandomBot initBotConnection(Color color) throws ServerException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        return null;
    }

    private String createGame() throws ServerException {
        return null;
    }

    private void joinGame(final String gameId) throws ServerException {

    }

    private ActionDtoResponse turn(final RandomBot bot, final Stone[][] gameField) throws ServerException {
        return null;
    }

}
