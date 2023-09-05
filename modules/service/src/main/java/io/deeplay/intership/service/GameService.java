package io.deeplay.intership.service;

import io.deeplay.intership.dao.GameDao;
import io.deeplay.intership.dao.UserDao;
import io.deeplay.intership.dao.file.GameDaoImpl;
import io.deeplay.intership.dao.file.UserDaoImpl;
import io.deeplay.intership.dto.request.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.JoinGameDtoRequest;
import io.deeplay.intership.dto.response.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Класс GameService обрабатывает связанные с игрой операции, такие как создание игр, присоединение к играм, выполнение ходов и т.д.
 * и управление игровыми сессиями.
 */
public class GameService {
    private final Logger logger = Logger.getLogger(GameService.class);
    private final ConcurrentMap<String, GameSession> idToGameSession;
    private final ConcurrentMap<Player, String> playerToGame;
    private final UserDao userDao;
    private final GameDao gameDao;


    public GameService(
            ConcurrentMap<String, GameSession> idToGameSession,
            ConcurrentMap<Player, String> playerToGame) {
        this.idToGameSession = idToGameSession;
        this.playerToGame = playerToGame;
        this.userDao = new UserDaoImpl();
        this.gameDao = new GameDaoImpl();
    }

    /**
     * Создает новую игровую сессию на основе предоставленного CreateGameDtoRequest.
     *
     * @param dtoRequest {@link CreateGameDtoRequest}, содержащий сведения о параметрах игры.
     * @return {@link CreateGameDtoResponse}, указывающий на успешное создание игры.
     * @throws ServerException Если возникла проблема при обработке запроса.
     */
    public CreateGameDtoResponse createGame(final CreateGameDtoRequest dtoRequest) throws ServerException {
        final User user = userDao.getUserByToken(dtoRequest.token);

        final Player player = new Player(user.login(), dtoRequest.color);

        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        gameSession.addCreator(player);

        gameDao.addActiveUser(dtoRequest.token, gameId);
        idToGameSession.put(gameId, gameSession);
        playerToGame.put(player, gameId);

        logger.debug("Game was successfully created");
        return new CreateGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message,
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
        final User user = userDao.getUserByToken(dtoRequest.token);
        final GameSession gameSession = findGameSessionById(dtoRequest.gameId);

        final Player player = new Player(user.login(), Color.WHITE.name());
        gameSession.addPlayer(player);
        gameDao.addActiveUser(dtoRequest.token, gameSession.getGameId());
        playerToGame.put(player, dtoRequest.gameId);

        logger.debug("Player was successfully joined to game " + dtoRequest.gameId);
        return new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_JOIN_GAME.message);
    }

    private GameSession findGameSessionById(final String gameId) throws ServerException {
        GameSession gameSession = idToGameSession.get(gameId);
        if (gameSession == null) {
            throw new ServerException(ServerErrorCode.GAME_NOT_FOUND);
        }
        return gameSession;
    }
}
