package io.deeplay.intership.client;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.dto.request.BaseDtoRequest;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoRequest;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoType;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.dto.response.gameplay.AnswerDtoResponse;
import io.deeplay.intership.dto.response.gameplay.FinishGameDtoResponse;
import io.deeplay.intership.dto.response.gameplay.StartGameDtoResponse;
import io.deeplay.intership.dto.response.gameplay.UpdateFieldDtoResponse;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.ui.UserInterface;
import org.apache.log4j.Logger;

import java.io.IOException;

public class GameplayController {
    private final Logger logger = Logger.getLogger(GameplayController.class);
    private StreamConnector streamConnector;
    private UserInterface userInterface;
    private DecisionMaker decisionMaker;
    private Color clientColor;
    private String token;

    public GameplayController(StreamConnector streamConnector, UserInterface userInterface, DecisionMaker decisionMaker) {
        this.streamConnector = streamConnector;
        this.userInterface = userInterface;
        this.decisionMaker = decisionMaker;
    }

    public FinishGameDtoResponse processingGame(final String token, final Color clientColor) throws ClientException, IOException {
        this.clientColor = clientColor;
        this.token = token;
        BaseDtoResponse response = new BaseDtoResponse(ResponseStatus.SUCCESS, "");
        while (!isFinish(response)) {
            logger.debug("client listen socket");
            response = streamConnector.getResponse();
            defineAction(response);
        }
        return (FinishGameDtoResponse) response;
    }

    public void defineAction(final BaseDtoResponse response) throws IOException, ClientException {
        if (response instanceof final AnswerDtoResponse dtoResponse) {
            userInterface.showBoard(dtoResponse.gameField);
            streamConnector.sendRequest(getGameAction());
            return;
        }
        if (response instanceof final UpdateFieldDtoResponse dtoResponse) {
            userInterface.showBoard(dtoResponse.gameField);
            return;
        }
        if (response instanceof StartGameDtoResponse) {
            userInterface.showBoard(((StartGameDtoResponse) response).gameField);
            return;
        }
        if (response instanceof FailureDtoResponse) {
            //TODO: через userInterface показать пользователю ошибку, пришедшую с сервера
            //TODO: что делаем в случае ошибки
            return;
        }
        if (response instanceof FinishGameDtoResponse) {
            //TODO: вывести результат игры на экран
            userInterface.showGameResult("Черные " + ((FinishGameDtoResponse) response).blackScore + "\n" +
                    "Белые " + ((FinishGameDtoResponse) response).whiteScore);
            return;
        }
        if (response instanceof BaseDtoResponse) {
            logger.error("НЕ ОТЛОВЛЕН response");
            logger.debug(response.status);
            logger.debug(response.message);
            return;
        }
    }

    public BaseDtoRequest getGameAction() throws ClientException {
        userInterface.showMoveRules();
        userInterface.showGameActions();
        final GameAction action;
        try {
            action = decisionMaker.getGameAction();
            return switch (action.type()) {
                case TURN -> turn(action);
                case PASS -> pass();
                default -> throw new ClientException(ClientErrorCode.WRONG_INPUT);
            };
        } catch (ClientException ex) {
            logger.debug(ex.errorMessage);
            throw ex;
        }
    }

    public AnswerDtoRequest turn(final GameAction gameAction) {
        return new AnswerDtoRequest(
                AnswerDtoType.TURN,
                gameAction.row(),
                gameAction.column());
    }

    public AnswerDtoRequest pass() {
        return new AnswerDtoRequest(
                AnswerDtoType.PASS,
                -1,
                -1);
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