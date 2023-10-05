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

/**
 * Обеспечивает взаимодействие клиента с игровой сессией, обработку игровых действий
 * и отображение информации о ходе игры.
 */
public class GameplayController extends Controller {
    private final Logger logger = Logger.getLogger(GameplayController.class);

    public GameplayController(
            final StreamConnector streamConnector,
            final UserInterface userInterface,
            final DecisionMaker decisionMaker) {
        super(streamConnector, userInterface, decisionMaker);
    }

    /**
     * Обработка игровой сессии, включая передачу ходов, получение ответов от сервера и отображение информации.
     *
     * @param token       Токен клиента, необходимый для аутентификации.
     * @param clientColor Цвет клиента {@link Color} в игре (Black или White).
     * @return Результат игры, включая счет и победителя.
     * @throws ClientException Если возникла ошибка взаимодействия с сервером или неверные данные.
     * @throws IOException     Если возникла ошибка при отправке запросов и получении ответов.
     */
    public FinishGameDtoResponse processingGame(final String token, final Color clientColor) throws ClientException, IOException {
        BaseDtoResponse response = new BaseDtoResponse(ResponseStatus.SUCCESS, "");
        while (!isFinish(response)) {
            logger.debug("client listen socket");
            response = streamConnector.getResponse();
            defineAction(response);
        }
        return (FinishGameDtoResponse) response;
    }

    /**
     * Определение действия, которое необходимо выполнить в зависимости от ответа сервера.
     *
     * @param response Ответ от сервера.
     * @throws ClientException Если возникла ошибка взаимодействия с сервером или неверные данные.
     * @throws IOException     Если возникла ошибка при отправке запросов и получении ответов.
     */
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

    /**
     * Получение игрового действия от пользователя с использованием {@link DecisionMaker}.
     *
     * @return Запрос на игровое действие, который будет отправлен на сервер.
     * @throws ClientException Если возникла ошибка при отправке запросов и получении ответов.
     */
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

    /**
     * Создание запроса на выполнение хода в игре.
     *
     * @param gameAction Действие {@link GameAction}, которое клиент хочет собирается выполнить.
     * @return Запрос на выполнение хода.
     */
    public AnswerDtoRequest turn(final GameAction gameAction) {
        return new AnswerDtoRequest(
                AnswerDtoType.TURN,
                gameAction.row(),
                gameAction.column());
    }

    /**
     * Создание запроса на пропуск хода в игре.
     *
     * @return Запрос на передачу хода (пропуск хода).
     */
    public AnswerDtoRequest pass() {
        return new AnswerDtoRequest(
                AnswerDtoType.PASS,
                -1,
                -1);
    }

    /**
     * Проверка, завершена ли игровая сессия на основе ответа от сервера.
     *
     * @param dtoResponse Ответ от сервера.
     * @return {@code true}, если игровая сессия завершена; в противном случае - {@code false}.
     */
    public boolean isFinish(final BaseDtoResponse dtoResponse) {
        return dtoResponse instanceof FinishGameDtoResponse;
    }
}