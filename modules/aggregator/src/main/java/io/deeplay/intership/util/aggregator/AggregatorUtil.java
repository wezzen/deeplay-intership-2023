package io.deeplay.intership.util.aggregator;

import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.User;

import java.util.Optional;

/**
 * Класс {@code AggregatorUtil} предоставляет функционал для работы с коллекциями данных представленный
 * {@link DataCollectionsAggregator}. Он позволяет добавлять и получать информацию об играх, пользователях и их
 * токенах.
 */
public class AggregatorUtil {
    private final DataCollectionsAggregator collectionsAggregator;

    public AggregatorUtil(final DataCollectionsAggregator collectionsAggregator) {
        this.collectionsAggregator = collectionsAggregator;
    }

    /**
     * Добавляет нового пользователя в коллекцию.
     *
     * @param user Объект {@link User}, который нужно добавить.
     */
    public void addNewUser(final User user) {
        collectionsAggregator.users().put(user.login(), user);
    }

    /**
     * Добавляет связь между токеном и пользователем в коллекцию авторизованных пользователей.
     *
     * @param token Токен пользователя.
     * @param user  Объект {@link User}, связанный с токеном.
     */
    public void addUsersToken(final String token, final User user) {
        collectionsAggregator.tokenToUser().put(token, user);
    }

    /**
     * Добавляет связь между пользователем и игрой в коллекцию.
     *
     * @param token  Токен пользователя.
     * @param gameId Идентификатор игры.
     */
    public void addUserToGame(final String token, final String gameId) {
        collectionsAggregator.playerToGame().put(token, gameId);
    }

    /**
     * Добавляет игровую сессию в коллекцию.
     *
     * @param gameId      Идентификатор игры.
     * @param gameSession Объект {@link GameSession}, представляющий игровую сессию.
     */
    public void addGameSession(final String gameId, final GameSession gameSession) {
        collectionsAggregator.idToGameSession().put(gameId, gameSession);
    }

    /**
     * Получает пользователя по его логину.
     *
     * @param login Логин пользователя.
     * @return Объект {@link User}, если пользователь найден; в противном случае {@code Optional.empty()}.
     * @throws ServerException Если пользователь не найден, генерируется исключение {@code ServerException}.
     */
    public Optional<User> getUserByLogin(final String login) throws ServerException {
        return Optional.ofNullable(collectionsAggregator.users().get(login));
    }

    /**
     * Получает пользователя по его токену.
     *
     * @param token Токен пользователя.
     * @return Объект {@link User}, если пользователь найден.
     * @throws ServerException Если пользователь не найден, генерируется исключение {@code ServerException}.
     */
    public User getUserByToken(final String token) throws ServerException {
        User user = collectionsAggregator.tokenToUser().get(token);
        if (user == null) {
            throw new ServerException(ServerErrorCode.NOT_AUTHORIZED);
        }
        return user;
    }

    /**
     * Получает игровую сессию по ее идентификатору.
     *
     * @param gameId Идентификатор игры.
     * @return Объект {@link GameSession}, представляющий игровую сессию.
     * @throws ServerException Если игровая сессия не найдена, генерируется исключение {@code ServerException}.
     */
    public GameSession getGameSessionById(final String gameId) throws ServerException {
        GameSession gameSession = collectionsAggregator.idToGameSession().get(gameId);
        if (gameSession == null) {
            throw new ServerException(ServerErrorCode.GAME_NOT_FOUND);
        }
        return gameSession;
    }

    /**
     * Получает игровую сессию по токену пользователя.
     *
     * @param token Токен пользователя.
     * @return Объект {@link GameSession}, представляющий игровую сессию.
     * @throws ServerException Если игровая сессия не найдена, генерируется исключение {@code ServerException}.
     */
    public GameSession getGameByUserToken(final String token) throws ServerException {
        final String gameId = collectionsAggregator.playerToGame().get(token);
        if (gameId == null) {
            throw new ServerException(ServerErrorCode.GAME_NOT_FOUND);
        }
        return getGameSessionById(gameId);
    }

    /**
     * Удаляет связь между токеном и пользователем из коллекции с авторизованными пользователями.
     *
     * @param token Токен пользователя.
     */
    public void removeUsersToken(final String token) {
        collectionsAggregator.tokenToUser().remove(token);
    }
}
