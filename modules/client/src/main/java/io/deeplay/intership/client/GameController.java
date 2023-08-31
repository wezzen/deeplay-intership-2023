package io.deeplay.intership.client;

import io.deeplay.intership.UserInterface;
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
import io.deeplay.intership.json.converter.JSONConverter;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import org.apache.log4j.Logger;

public class GameController {
    private final Logger logger = Logger.getLogger(GameController.class);
    private final JSONConverter jsonConverter = new JSONConverter();
    private StreamConnector streamConnector;
    private UserInterface userInterface;
    private DecisionMaker decisionMaker;
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
                case JOIN_GAME -> joinGameById(gameConfig, clientColor);
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

    private BaseDtoResponse createGame(final GameConfig gameConfig) throws ClientException {
        String requestString = jsonConverter.getJsonFromObject(new CreateGameDtoRequest(
                gameConfig.withBot(),
                gameConfig.color().name(),
                gameConfig.size(),
                token));
        streamConnector.sendRequest(requestString);
        return streamConnector.getResponse();
    }

    private BaseDtoResponse joinGameById(final GameConfig gameConfig, final Color color) throws ClientException {
        String requestString = jsonConverter.getJsonFromObject(new JoinGameDtoRequest(
                gameConfig.gameId(),
                token,
                gameConfig.color().name()));
        streamConnector.sendRequest(requestString);
        return streamConnector.getResponse();
    }

    public void defineGameAction() throws ClientException {
        BaseDtoResponse response = new BaseDtoResponse("", "");
        Stone[][] field = new Board().getField();
        GameAction action;

        while (!isFinish(response)) {
            try {
                userInterface.showBoard(field);
                userInterface.showMoveRules();
                action = decisionMaker.getGameAction();
                switch (action.type()) {
                    case TURN -> turn(clientColor, action);
                    case PASS -> pass(token);
                }
                response = streamConnector.getResponse();
                response = processingResponse(response, field);
            } catch (ClientException ex) {
                logger.debug(ex.errorMessage);
                throw ex;
            }
        }
    }

    public void turn(final Color color, final GameAction gameAction) throws ClientException {
        String request = jsonConverter.getJsonFromObject(new TurnDtoRequest(
                color.name(),
                gameAction.row() - 1,
                gameAction.column() - 1,
                token));
        streamConnector.sendRequest(request);
    }

    public void pass(final String token) throws ClientException {
        String request = jsonConverter.getJsonFromObject(new PassDtoRequest(token));
        streamConnector.sendRequest(request);
    }

    private <T extends BaseDtoResponse> T processingResponse(final T dtoResponse, Stone[][] field) {
        if (dtoResponse instanceof ActionDtoResponse) {
            field = ((ActionDtoResponse) dtoResponse).gameField;
        }
        if (dtoResponse instanceof FailureDtoResponse) {
            logger.debug(dtoResponse.status + " " + dtoResponse.message);
            //TODO: через userInterface показать пользователю ошибку, пришедшую с сервера
        }
        if (dtoResponse instanceof FinishGameDtoResponse) {
            userInterface.showGameResult(dtoResponse.message +
                    ((FinishGameDtoResponse) dtoResponse).blackScore +
                    ((FinishGameDtoResponse) dtoResponse).whiteScore);
        }
        return dtoResponse;
    }

    private <T extends BaseDtoResponse> boolean isFinish(final T dtoResponse) {
        return dtoResponse instanceof FinishGameDtoResponse;
    }
}
