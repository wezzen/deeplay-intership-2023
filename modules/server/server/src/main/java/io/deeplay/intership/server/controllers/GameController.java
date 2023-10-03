package io.deeplay.intership.server.controllers;

import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.apache.log4j.Logger;

/**
 * Контроллер игры, расширяющий базовый класс Controller.
 * Отвечает за обработку запросов, связанных с созданием и присоединением к играм.
 */
public class GameController extends Controller {
    private final Logger logger = Logger.getLogger(GameController.class);
    private final GameService gameService;

    public GameController(final GameService gameService, final Validator dtoValidator, final int clientId) {
        super(dtoValidator, clientId);
        this.gameService = gameService;
    }

    public GameController(final DataCollectionsAggregator collectionsAggregator, final int clientId) {
        this(new GameService(collectionsAggregator), new Validator(), clientId);
    }

    /**
     * Метод для создания игры на основе запроса {@link CreateGameDtoRequest}.
     *
     * @param dtoRequest Запрос на создание игры.
     * @return Ответ на запрос, содержащий информацию о созданной игре.
     */
    public BaseDtoResponse createGame(final CreateGameDtoRequest dtoRequest) {
        try {
            final String message = String.format("Client %d send create game", clientId);
            logger.debug(message);

            dtoValidator.validationCreateGameDto(dtoRequest);
            return gameService.createGame(dtoRequest);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'create game' was failed");
            return getFailureResponse(ex);
        }
    }

    /**
     * Метод для присоединения к существующей игре на основе запроса {@link JoinGameDtoRequest}.
     *
     * @param dtoRequest Запрос на присоединение к игре.
     * @return Ответ на запрос, содержащий информацию о присоединении к игре.
     */
    public BaseDtoResponse joinGame(final JoinGameDtoRequest dtoRequest) {
        try {
            final String message = String.format("Client %d send join game", clientId);
            logger.debug(message);

            dtoValidator.validationJoinGameDto(dtoRequest);
            return gameService.joinGame(dtoRequest);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'join game' was failed");
            return getFailureResponse(ex);
        }
    }
}
