package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.PassDtoRequest;
import io.deeplay.intership.dto.request.SurrenderDtoRequest;
import io.deeplay.intership.dto.request.TurnDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.service.GameplayService;
import io.deeplay.intership.service.UserService;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentMap;

public class GameplayController extends Controller {
    private final Logger logger = Logger.getLogger(GameController.class);
    private final UserService userService;
    private final GameService gameService;
    private final GameplayService gameplayService;

    public GameplayController(UserService userService, GameService gameService, GameplayService gameplayService, Validator dtoValidator, int clientId) {
        super(dtoValidator, clientId);
        this.userService = userService;
        this.gameService = gameService;
        this.gameplayService = gameplayService;
    }

    public GameplayController(
            ConcurrentMap<String, GameSession> idToGameSession,
            ConcurrentMap<Player, String> playerToGame,
            int clientId) {
        this(
                new UserService(),
                new GameService(idToGameSession, playerToGame),
                new GameplayService(idToGameSession, playerToGame),
                new Validator(),
                clientId);
    }

    public BaseDtoResponse turn(TurnDtoRequest dtoRequest) {
        try {
            final String message = String.format("Client %d send make turn", clientId);
            logger.debug(message);

            dtoValidator.validationTurnDto(dtoRequest);
            return gameplayService.turn(dtoRequest);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'turn' was failed");
            return getFailureResponse(ex);
        }
    }

    public BaseDtoResponse pass(PassDtoRequest dtoRequest) {
        try {
            final String message = String.format("Client %d send pass turn", clientId);
            logger.debug(message);

            dtoValidator.validationPassDto(dtoRequest);
            return gameplayService.pass(dtoRequest);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'pass' was failed");
            return getFailureResponse(ex);
        }
    }

    public BaseDtoResponse surrenderGame(SurrenderDtoRequest dtoRequest) {
        try {
            final String message = String.format("Client %d send surrender game", clientId);
            logger.debug(message);

            return gameplayService.surrenderGame(dtoRequest);
        } catch (ServerException ex) {
            logger.debug("Clients operation 'surrender' was failed");
            return getFailureResponse(ex);
        }
    }
}
