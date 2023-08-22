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

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Класс GameService обрабатывает связанные с игрой операции, такие как создание игр, присоединение к играм, выполнение ходов и т.д.
 * и управление игровыми сессиями.
 */
public class GameService {
    private static final ConcurrentMap<String, GameSession> ID_TO_GAME_SESSION = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Player> ACTIVE_PLAYERS = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Player, String> PLAYER_TO_GAME = new ConcurrentHashMap<>();
    private final UserService userService;
    private final Validator dtoValidator;
    private final Logger logger;

    public GameService(UserService userService, Validator dtoValidator) {
        this.logger = Logger.getLogger(GameService.class);
        this.userService = userService;
        this.dtoValidator = dtoValidator;
    }

    public GameService() {
        this(new UserService(), new Validator());
    }

    /**
     * Создает новую игровую сессию на основе предоставленного CreateGameDtoRequest.
     *
     * @param dtoRequest {@link CreateGameDtoRequest}, содержащий сведения о параметрах игры.
     * @return {@link CreateGameDtoResponse}, указывающий на успешное создание игры.
     * @throws ServerException Если возникла проблема при обработке запроса.
     */
    public CreateGameDtoResponse createGame(final CreateGameDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationCreateGameDto(dtoRequest);
        final User user = userService.findUserByToken(dtoRequest.token());

        final Player player = new Player(user.login(), dtoRequest.color());

        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        gameSession.addCreator(player);
        ACTIVE_PLAYERS.put(dtoRequest.token(), player);
        ID_TO_GAME_SESSION.put(gameId, gameSession);
        PLAYER_TO_GAME.put(player, gameId);

        logger.debug("Game was successfully created");
        return new CreateGameDtoResponse(
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message,
                ResponseStatus.SUCCESS.text,
                gameId);
    }

    /**
     * Позволяет игроку присоединиться к существующей игровой сессии.
     *
     * @param dtoRequest {@link JoinGameDtoRequest}, содержащий сведения о присоединении к игре.
     * @return {@link InfoDtoResponse}, указывающий на успешное присоединение к игре.
     * @throws ServerException Если есть проблема с сервером.
     */
    public InfoDtoResponse joinGame(final JoinGameDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationJoinGameDto(dtoRequest);
        final User user = userService.findUserByToken(dtoRequest.token());
        final GameSession gameSession = findGameSessionById(dtoRequest.gameId());

        final Player player = new Player(user.login(), Color.WHITE.name());
        gameSession.addPlayer(player);
        ACTIVE_PLAYERS.put(dtoRequest.token(), player);
        PLAYER_TO_GAME.put(player, dtoRequest.gameId());

        logger.debug("Player was successfully joined to game " + dtoRequest.gameId());
        return new InfoDtoResponse(
                ResponseInfoMessage.SUCCESS_JOIN_GAME.message,
                ResponseStatus.SUCCESS.text);
    }

    /**
     * Обрабатывает ход игрока в игре.
     *
     * @param dtoRequest {@link TurnDtoRequest}, содержащий детали хода.
     * @return {@link ActionDtoResponse}, указывающий результат хода.
     * @throws ServerException Если есть проблема с сервером.
     */
    public ActionDtoResponse turn(final TurnDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationTurnDto(dtoRequest);
        userService.findUserByToken(dtoRequest.token());
        final Player player = findPlayerByToken(dtoRequest.token());
        final GameSession gameSession = findGameSessionById(PLAYER_TO_GAME.get(player));

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

    /**
     * Позволяет игроку пройти свой ход в игре.
     *
     * @param dtoRequest {@link PassDtoRequest}, содержащий сведения о проходе.
     * @return {@link ActionDtoResponse}, указывающий на успешное прохождение поворота.
     * @throws ServerException Если есть проблема с сервером.
     */
    public ActionDtoResponse pass(final PassDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationPassDto(dtoRequest);
        userService.findUserByToken(dtoRequest.token());
        final Player player = findPlayerByToken(dtoRequest.token());
        final GameSession gameSession = findGameSessionById(PLAYER_TO_GAME.get(player));
        final Stone[][] gameField = gameSession.pass(player);
        return new ActionDtoResponse(
                ResponseInfoMessage.SUCCESS_PASS.message,
                ResponseStatus.SUCCESS.text,
                gameField);
    }

    public InfoDtoResponse surrenderGame(final SurrenderDtoRequest dtoRequest) {
        return null;
    }

    public FinishGameDtoResponse finishGame(final FinishGameDtoRequest dtoRequest) {
        return null;
    }

    private GameSession findGameSessionById(final String gameId) throws ServerException {
        GameSession gameSession = ID_TO_GAME_SESSION.get(gameId);
        if (gameSession == null) {
            throw new ServerException(ErrorCode.GAME_NOT_FOUND);
        }
        return gameSession;
    }

    private Player findPlayerByToken(final String token) throws ServerException {
        final Player player = ACTIVE_PLAYERS.get(token);
        if (player == null) {
            throw new ServerException(ErrorCode.GAME_NOT_FOUND);
        }
        return player;
    }
}
