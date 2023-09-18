package io.deeplay.intership.client;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.game.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.ui.UserInterface;
import io.deeplay.intership.validation.Validation;
import org.apache.log4j.Logger;

import java.io.IOException;

public class GameController {
    private final Logger logger = Logger.getLogger(GameController.class);
    private StreamConnector streamConnector;
    private UserInterface userInterface;
    private DecisionMaker decisionMaker;
    private Validation validation;
    private String token;
    private Color clientColor;

    public GameController(StreamConnector streamConnector, UserInterface userInterface, DecisionMaker decisionMaker) {
        this.streamConnector = streamConnector;
        this.userInterface = userInterface;
        this.decisionMaker = decisionMaker;
    }

    public Color joinToGame(final String token) throws ClientException, IOException {
        this.token = token;
        BaseDtoResponse response;
        boolean isNoCreated = true;

        while (isNoCreated) {
            userInterface.showRoomActions();
            GameConfig gameConfig = decisionMaker.getGameConfig();
            clientColor = gameConfig.color();

            response = switch (gameConfig.type()) {
                case CREATE_GAME -> createGame(gameConfig);
                case JOIN_GAME -> joinGameById(gameConfig);
                default -> throw new ClientException(ClientErrorCode.WRONG_INPUT);
            };

            if (response instanceof CreateGameDtoResponse) {

                userInterface.showCreating(((CreateGameDtoResponse) response).gameId);
                //userInterface.showJoin();
                isNoCreated = false;
            }
            if (response instanceof InfoDtoResponse) {
                userInterface.showJoin();
                isNoCreated = false;
            }
        }
        return clientColor;
    }

    public BaseDtoResponse createGame(final GameConfig gameConfig) throws IOException {
        streamConnector.sendRequest(new CreateGameDtoRequest(
                gameConfig.withBot(),
                gameConfig.color().name(),
                gameConfig.size(),
                token));
        return streamConnector.getResponse();
    }

    public BaseDtoResponse joinGameById(final GameConfig gameConfig) throws IOException {
        streamConnector.sendRequest(new JoinGameDtoRequest(
                gameConfig.gameId(),
                token,
                gameConfig.color().name()));
        return streamConnector.getResponse();
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setColor(final Color color) {
        this.clientColor = color;
    }
}