package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.json.converter.JSONConverter;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.service.UserService;
import org.apache.log4j.Logger;

public class GameController {
    private final Logger logger = Logger.getLogger(GameController.class);
    private final UserService userService;
    private final GameService gameService;
    private final JSONConverter jsonConverter;
    private final int clientId;

    public GameController(UserService userService, GameService gameService, JSONConverter jsonConverter, int clientId) {
        this.userService = userService;
        this.gameService = gameService;
        this.jsonConverter = jsonConverter;
        this.clientId = clientId;
    }

    public GameController(int clientId) {
        this(new UserService(), new GameService(), new JSONConverter(), clientId);
    }

    public String createGame(CreateGameDtoRequest dtoRequest) {
        final String message = String.format("Client %d send create game", clientId);
        logger.debug(message);

        try {
            final CreateGameDtoResponse response = gameService.createGame(dtoRequest);
            return jsonConverter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'create game' was failed");
            return getFailureResponse(ex);
        }
    }

    public String joinGame(JoinGameDtoRequest dtoRequest) {
        final String message = String.format("Client %d send join game", clientId);
        logger.debug(message);

        try {
            final InfoDtoResponse response = gameService.joinGame(dtoRequest);
            return jsonConverter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'join game' was failed");
            return getFailureResponse(ex);
        }
    }

    public String surrenderGame(SurrenderDtoRequest dtoRequest) {
        final String message = String.format("Client %d send surrender game", clientId);
        logger.debug(message);

        try {
            final InfoDtoResponse response = gameService.surrenderGame(dtoRequest);
            return jsonConverter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'surrender' was failed");
            return getFailureResponse(ex);
        }
    }

    public String turn(TurnDtoRequest dtoRequest) {
        final String message = String.format("Client %d send make turn", clientId);
        logger.debug(message);

        try {
            final BaseDtoResponse response = gameService.turn(dtoRequest);
            return jsonConverter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'turn' was failed");
            return getFailureResponse(ex);
        }
    }

    public String pass(PassDtoRequest dtoRequest) {
        final String message = String.format("Client %d send pass turn", clientId);
        logger.debug(message);

        try {
            final BaseDtoResponse response = gameService.pass(dtoRequest);
            return jsonConverter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'pass' was failed");
            return getFailureResponse(ex);
        }
    }

    private String getFailureResponse(ServerException ex) {
        final FailureDtoResponse dtoResponse = new FailureDtoResponse(ResponseStatus.FAILURE, ex.message);
        return jsonConverter.getJsonFromObject(dtoResponse);
    }
}
