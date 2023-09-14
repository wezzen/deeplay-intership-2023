package io.deeplay.intership.server.controllers;

import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.apache.log4j.Logger;

public class GameController extends Controller {
    private final Logger logger = Logger.getLogger(GameController.class);
    private final GameService gameService;

    public GameController(GameService gameService, Validator dtoValidator, int clientId) {
        super(dtoValidator, clientId);
        this.gameService = gameService;
    }

    public GameController(DataCollectionsAggregator collectionsAggregator, int clientId) {
        this(new GameService(collectionsAggregator), new Validator(), clientId);
    }

    public BaseDtoResponse createGame(CreateGameDtoRequest dtoRequest) {
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

    public BaseDtoResponse joinGame(JoinGameDtoRequest dtoRequest) {
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
