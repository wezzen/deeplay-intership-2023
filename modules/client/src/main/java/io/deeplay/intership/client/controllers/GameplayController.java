package io.deeplay.intership.client.controllers;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.dto.request.BaseDtoRequest;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoRequest;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoType;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;
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

public class GameplayController extends Controller {
    private final Logger logger = Logger.getLogger(GameplayController.class);

    public GameplayController(StreamConnector streamConnector, UserInterface userInterface, DecisionMaker decisionMaker) {
        super(streamConnector, userInterface, decisionMaker);
    }

    public FinishGameDtoResponse processingGame(final String token, final Color clientColor) throws ClientException, IOException {
        BaseDtoResponse response = new BaseDtoResponse(ResponseStatus.SUCCESS, "");
        while (!isFinish(response)) {
            logger.debug("client listen socket");
            response = streamConnector.getResponse();
            defineAction(response);
        }
        return (FinishGameDtoResponse) response;
    }

    public void defineAction(final BaseDtoResponse response) throws IOException, ClientException {
        logger.debug(response.message);
        if (response instanceof final AnswerDtoResponse dtoResponse) {
            userInterface.showBoard(dtoResponse.gameField);
            streamConnector.sendRequest(getGameAction());
        } else if (response instanceof final UpdateFieldDtoResponse dtoResponse) {
            userInterface.showBoard(dtoResponse.gameField);
        } else if (response instanceof final StartGameDtoResponse dtoResponse) {
            userInterface.showBoard(dtoResponse.gameField);
        } else if (response instanceof FailureDtoResponse) {
            //TODO: через userInterface показать пользователю ошибку, пришедшую с сервера
            //TODO: что делаем в случае ошибки
        } else if (response instanceof final FinishGameDtoResponse dtoResponse) {
            //TODO: вывести результат игры на экран
            userInterface.showGameResult("Черные " + dtoResponse.blackScore + "\n" +
                    "Белые " + dtoResponse.whiteScore);
        } else if (response instanceof BaseDtoResponse) {
            logger.error("Does not catch response");
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
}