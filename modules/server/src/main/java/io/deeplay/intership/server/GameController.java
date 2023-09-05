package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.JoinGameDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.service.UserService;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentMap;

public class GameController extends Controller {
    private final Logger logger = Logger.getLogger(GameController.class);
    private final UserService userService;
    private final GameService gameService;

    public GameController(UserService userService, GameService gameService, Validator dtoValidator, int clientId) {
        super(dtoValidator, clientId);
        this.userService = userService;
        this.gameService = gameService;
    }

    public GameController(
            ConcurrentMap<String, GameSession> idToGameSession,
            ConcurrentMap<Player, String> playerToGame,
            int clientId) {
        this(
                new UserService(),
                new GameService(idToGameSession, playerToGame),
                new Validator(),
                clientId);
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
