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
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameService {
    private static final ConcurrentMap<String, GameSession> ID_TO_GAME_SESSION = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Player> ACTIVE_PLAYERS = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Player, String> PLAYER_TO_GAME = new ConcurrentHashMap<>();
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
        final User user = userService.findUserByToken(dtoRequest.token());

        final Player player = new Player(user.login(), dtoRequest.color());

        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        gameSession.addCreator(player);
        ID_TO_GAME_SESSION.put(gameId, gameSession);

        logger.debug("Game was successfully created");
        return new CreateGameDtoResponse(
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message,
                ResponseStatus.SUCCESS.text,
                gameId);
    }

    public InfoDtoResponse joinGame(final JoinGameDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationJoinGameDto(dtoRequest);
        final User user = userService.findUserByToken(dtoRequest.token());
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

    public ActionDtoResponse turn(final TurnDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationTurnDto(dtoRequest);
        userService.findUserByToken(dtoRequest.token());
        final Player player = findPlayerByToken(dtoRequest.token());
        final GameSession gameSession = ID_TO_GAME_SESSION.get(PLAYER_TO_GAME.get(player));

        final Stone stone = new Stone(
                Color.valueOf(dtoRequest.color()),
                dtoRequest.row(),
                dtoRequest.column());
        Stone[][] gameField = gameSession.turn(player, stone);

        logger.debug("Player was successfully make turn");
        return new ActionDtoResponse(
                ResponseInfoMessage.SUCCESS_TURN.message,
                ResponseStatus.SUCCESS.text,
                gameField);
    }

    public ActionDtoResponse pass(final PassDtoRequest dtoRequest) {
        return null;
    }

    public FinishGameDtoResponse finishGame(final FinishGameDtoRequest dtoRequest) {
        return null;
    }

    private Optional<GameSession> findGameSessionById(final String gameId) {
        return Optional.ofNullable(ID_TO_GAME_SESSION.get(gameId));
    }

    private Player findPlayerByToken(final String token) throws ServerException {
        final Player player = ACTIVE_PLAYERS.get(token);
        if (player == null) {
            throw new ServerException(ErrorCode.CANNOT_FIND_GAME);
        }
        return player;
    }
}
