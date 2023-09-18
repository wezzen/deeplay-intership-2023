package io.deeplay.intership.client;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.dto.request.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.JoinGameDtoRequest;
import io.deeplay.intership.dto.request.PassDtoRequest;
import io.deeplay.intership.dto.request.TurnDtoRequest;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
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

    public void joinToGame(final String token) throws ClientException {
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
                userInterface.showJoin();
                isNoCreated = false;
            }
            if (response instanceof InfoDtoResponse) {
                userInterface.showJoin();
                isNoCreated = false;
            }
        }
    }

    public FinishGameDtoResponse processingGame() throws ClientException {
        BaseDtoResponse response = new BaseDtoResponse(ResponseStatus.SUCCESS, "");
        Board board = new Board();
        Stone[][] field = board.getField();
        validation = new Validation(board);

        while (!isFinish(response)) {
            userInterface.showBoard(field);
            response = defineGameAction();
            if (response instanceof ActionDtoResponse) {
                field = ((ActionDtoResponse) response).gameField;
            }
            if (response instanceof FailureDtoResponse) {
                logger.debug(response.status + " " + response.message);
                //TODO: через userInterface показать пользователю ошибку, пришедшую с сервера
            }
            if (response instanceof FinishGameDtoResponse) {
                userInterface.showGameResult("Черные " + ((FinishGameDtoResponse) response).blackScore + "\n" +
                        "Белые " + ((FinishGameDtoResponse) response).whiteScore);
            }
        }
        return (FinishGameDtoResponse) response;
    }

    public BaseDtoResponse createGame(final GameConfig gameConfig) throws ClientException {
        try {
            streamConnector.sendRequest(new CreateGameDtoRequest(
                    gameConfig.withBot(),
                    gameConfig.color().name(),
                    gameConfig.size(),
                    token));
            return streamConnector.getResponse();
        } catch (IOException ex) {
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }

    public BaseDtoResponse joinGameById(final GameConfig gameConfig) throws ClientException {
        try {
            streamConnector.sendRequest(new JoinGameDtoRequest(
                    gameConfig.gameId(),
                    token,
                    gameConfig.color().name()));
            return streamConnector.getResponse();
        } catch (IOException ex) {
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }

    public BaseDtoResponse defineGameAction() throws ClientException {
        GameAction action;
        try {
            userInterface.showMoveRules();
            action = decisionMaker.getGameAction();
            switch (action.type()) {
                case TURN -> turn(clientColor, action);
                case PASS -> pass(token);
                default -> throw new ClientException(ClientErrorCode.WRONG_INPUT);
            }
            return streamConnector.getResponse();
        } catch (ClientException ex) {
            logger.debug(ex.errorMessage);
            throw ex;
        } catch (IOException ex) {
            logger.debug(ex.getMessage());
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }

    public void turn(final Color color, final GameAction gameAction) throws ClientException {
        try {
            streamConnector.sendRequest(new TurnDtoRequest(
                    color.name(),
                    gameAction.row(),
                    gameAction.column(),
                    token));
        } catch (IOException ex) {
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }

    public void pass(final String token) throws ClientException {
        try {
            streamConnector.sendRequest(new PassDtoRequest(token));
        } catch (IOException ex) {
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }

    public boolean isFinish(final BaseDtoResponse dtoResponse) {
        return dtoResponse instanceof FinishGameDtoResponse;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setColor(final Color color) {
        this.clientColor = color;
    }
}