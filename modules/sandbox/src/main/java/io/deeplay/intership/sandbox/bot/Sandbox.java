package io.deeplay.intership.sandbox.bot;

import io.deeplay.intership.bot.random.RandomBot;
import io.deeplay.intership.client.AuthorizationController;
import io.deeplay.intership.client.GameController;
import io.deeplay.intership.client.StreamConnector;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.response.ActionDtoResponse;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.CreateGameDtoResponse;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.ui.terminal.Display;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Sandbox {
    private static final Logger log = Logger.getLogger(Sandbox.class);
    private static final String host = "localhost";
    private static final int port = 5000;
    private final Display display = new Display();
    private final RandomBot blackBot;
    private String blackToken;
    private final AuthorizationController blackAuthController;
    private final GameController blackGameController;
    private final RandomBot whiteBot;
    private String whiteToken;
    private final AuthorizationController whiteAuthController;
    private final GameController whiteGameController;

    public static void main(String[] args) throws IOException, ClientException {
        int gamesCount = 1;
        for (int i = 0; i < gamesCount; i++) {
            String startMessage = String.format("Game %d was started", i);
            String endMessage = String.format("Game %d was ended", i);

            log.info(startMessage);
            new Sandbox().startGame();
            log.info(endMessage);
        }
    }

    public Sandbox() throws IOException {
        blackBot = new RandomBot(Color.BLACK);
        whiteBot = new RandomBot(Color.WHITE);
        final Socket blackSocket = new Socket(InetAddress.getByName(host), port);
        final Socket whiteSocket = new Socket(InetAddress.getByName(host), port);

        StreamConnector blackConnector = new StreamConnector(
                new DataOutputStream(blackSocket.getOutputStream()),
                new DataInputStream(blackSocket.getInputStream()));

        StreamConnector whiteConnector = new StreamConnector(
                new DataOutputStream(whiteSocket.getOutputStream()),
                new DataInputStream(whiteSocket.getInputStream()));

        this.blackAuthController = new AuthorizationController(blackConnector, display, blackBot);
        this.blackGameController = new GameController(blackConnector, display, blackBot);
        this.whiteAuthController = new AuthorizationController(whiteConnector, display, whiteBot);
        this.whiteGameController = new GameController(whiteConnector, display, whiteBot);
    }

    public void startGame() throws ClientException {
        initBotConnection();

        String gameId = createGame();
        joinGame(gameId);

        blackGameController.defineGameAction();
        whiteGameController.defineGameAction();

        BaseDtoResponse response;
        while (true) {
            response = blackGameController.defineGameAction();
            if (response instanceof ActionDtoResponse) {
                blackBot.setGameField(((ActionDtoResponse) response).gameField);
                whiteBot.setGameField(((ActionDtoResponse) response).gameField);
                display.showBoard(((ActionDtoResponse) response).gameField);
            }
            response = whiteGameController.defineGameAction();
            if (response instanceof ActionDtoResponse) {
                blackBot.setGameField(((ActionDtoResponse) response).gameField);
                whiteBot.setGameField(((ActionDtoResponse) response).gameField);
                display.showBoard(((ActionDtoResponse) response).gameField);
            }
        }
    }

    private void initBotConnection() throws ClientException {
        blackAuthController.authorizeClient();
        blackToken = blackAuthController.authorizeClient();

        whiteAuthController.authorizeClient();
        whiteToken = whiteAuthController.authorizeClient();

        blackGameController.setToken(blackToken);
        whiteGameController.setToken(whiteToken);
        blackGameController.setColor(Color.BLACK);
        whiteGameController.setColor(Color.WHITE);
    }

    private String createGame() throws ClientException {
        BaseDtoResponse response = blackGameController.createGame(blackBot.getGameConfig());
        if (response instanceof CreateGameDtoResponse) {
            return ((CreateGameDtoResponse) response).gameId;
        }
        throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
    }

    private void joinGame(final String gameId) throws ClientException {
        GameConfig gameConfig = new GameConfig(
                RequestType.JOIN_GAME,
                whiteBot.getGameConfig().withBot(),
                whiteBot.getColor(),
                whiteBot.getGameConfig().size(),
                gameId);
        whiteGameController.joinGameById(gameConfig);
    }

    private <T extends BaseDtoResponse> void checkResponse(final T dtoResponse, final RandomBot bot) {
        if (dtoResponse instanceof ActionDtoResponse) {
            bot.setGameField(((ActionDtoResponse) dtoResponse).gameField);
        }
    }
}
