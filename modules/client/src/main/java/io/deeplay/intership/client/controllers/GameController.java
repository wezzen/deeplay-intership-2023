package io.deeplay.intership.client.controllers;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.game.CreateGameDtoResponse;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.ui.UserInterface;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Обеспечивает взаимодействие клиента с сервером в процессе создания и присоединения к игровым сессиям.
 */
public class GameController extends Controller {
    private final Logger logger = Logger.getLogger(GameController.class);
    private String token;
    private Color clientColor;

    public GameController(
            final StreamConnector streamConnector,
            final UserInterface userInterface,
            final DecisionMaker decisionMaker) {
        super(streamConnector, userInterface, decisionMaker);
    }

    /**
     * Метод для присоединения клиента к игровой сессии с использованием токена.
     *
     * @param token Токен клиента, необходимый для аутентификации.
     * @return Цвет клиента в игре (Black или White).
     * @throws ClientException Если возникла ошибка взаимодействия с сервером или неверные данные.
     * @throws IOException     Если возникла ошибка при отправке запроса.
     */
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
                userInterface.showJoin();
                logger.debug(response.message);
                isNoCreated = false;
            }
            if (response instanceof InfoDtoResponse) {
                userInterface.showJoin();
                logger.debug(response.message);
                isNoCreated = false;
            }
        }
        return clientColor;
    }

    /**
     * Метод для отправки запроса на создание игровой сессии.
     *
     * @param gameConfig Конфигурация игры {@link GameConfig}, включая параметры, такие как наличие бота, цвет игрока и размер поля.
     * @return Ответ от сервера после попытки создания игровой сессии.
     * @throws IOException Если возникла ошибка при отправке запроса.
     */
    public BaseDtoResponse createGame(final GameConfig gameConfig) throws IOException {
        streamConnector.sendRequest(new CreateGameDtoRequest(
                gameConfig.withBot(),
                gameConfig.color().name(),
                gameConfig.size(),
                token));
        return streamConnector.getResponse();
    }

    /**
     * Метод для отправки запроса на присоединение к существующей игровой сессии по идентификатору.
     *
     * @param gameConfig Конфигурация игры {@link GameConfig}, включая идентификатор сессии, цвет игрока и другие параметры.
     * @return Ответ от сервера после попытки присоединения к игровой сессии.
     * @throws IOException Если возникла ошибка при отправке запроса.
     */
    public BaseDtoResponse joinGameById(final GameConfig gameConfig) throws IOException {
        streamConnector.sendRequest(new JoinGameDtoRequest(
                gameConfig.gameId(),
                token,
                gameConfig.color().name()));
        return streamConnector.getResponse();
    }

    /**
     * Метод для установки токена клиента.
     *
     * @param token Токен клиента, необходимый для аутентификации.
     */
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * Метод для установки цвета клиента в игре (Black или White).
     *
     * @param color Цвет клиента в игре.
     */
    public void setColor(final Color color) {
        this.clientColor = color;
    }
}