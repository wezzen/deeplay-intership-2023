package io.deeplay.intership.service;

import io.deeplay.intership.dto.ResponseInfoMessage;
import io.deeplay.intership.dto.ResponseStatus;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.ActionDtoResponse;
import io.deeplay.intership.dto.response.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.FinishGameDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameService {
    private static final ConcurrentMap<String, GameSession> idToGameSession = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Player> players = new ConcurrentHashMap<>();
    private final UserService userService;
    private final Validator dtoValidator;
    private final Logger logger;

    public GameService() {
        this.logger = Logger.getLogger(GameService.class);
        this.userService = new UserService();
        this.dtoValidator = new Validator();
    }

    public CreateGameDtoResponse createGame(final CreateGameDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationCreateGameDto(dtoRequest);
        final User user = userService.findUserByToken(dtoRequest.token())
                .orElseThrow(() -> new ServerException(ErrorCode.NOT_AUTHORIZED));

        final Player player = new Player(user.login(), dtoRequest.color());

        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        gameSession.addCreator(player);
        idToGameSession.put(gameId, gameSession);

        logger.debug("Game was successfully created");
        return new CreateGameDtoResponse(
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message,
                ResponseStatus.SUCCESS.text,
                gameId);
    }

    public InfoDtoResponse joinGame(final JoinGameDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationJoinGameDto(dtoRequest);
        final User user = userService.findUserByToken(dtoRequest.token())
                .orElseThrow(() -> new ServerException(ErrorCode.NOT_AUTHORIZED));
        final GameSession gameSession = findGameSessionById(dtoRequest.gameId())
                .orElseThrow(() -> new ServerException(ErrorCode.GAME_NOT_FOUND));

        final Player player = new Player(user.login(), Color.WHITE.name());
        gameSession.addPlayer(player);

        logger.debug("Player was successfully joined to game " + dtoRequest.gameId());
        return new InfoDtoResponse(
                ResponseInfoMessage.SUCCESS_JOIN_GAME.message,
                ResponseStatus.SUCCESS.text);
    }

    public InfoDtoResponse surrenderGame(final SurrenderDtoRequest dtoRequest) {
        return null;
    }

    public FinishGameDtoResponse finishGame(final FinishGameDtoRequest dtoRequest) {
        return null;
    }

    public ActionDtoResponse turn(final TurnDtoRequest dtoRequest) {
        return null;
    }

    public ActionDtoResponse pass(final PassDtoRequest dtoRequest) {
        return null;
    }

    private Optional<GameSession> findGameSessionById(final String gameId) {
        return Optional.ofNullable(idToGameSession.get(gameId));
    }
}
