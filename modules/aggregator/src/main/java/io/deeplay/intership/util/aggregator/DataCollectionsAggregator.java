package io.deeplay.intership.util.aggregator;

import io.deeplay.intership.dao.UserDao;
import io.deeplay.intership.dao.file.UserDaoImpl;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Класс {@code DataCollectionsAggregator} представляет собой агрегатор коллекций данных,
 * используемых в системе. Этот класс содержит четыре потокобезопасных коллекции данных:
 * - {@code idToGameSession} для хранения игровых сессий с их идентификаторами.
 * - {@code tokenToUser} для хранения связей между токенами и пользователями.
 * - {@code playerToGame} для хранения связей между пользователями и играми.
 * - {@code users} для хранения пользователей по их логинам.
 * Класс также выполняет инициализацию коллекции {@code users} данными из базы данных, используя объект {@link UserDao}.
 */
public record DataCollectionsAggregator(
        ConcurrentMap<String, GameSession> idToGameSession,
        ConcurrentMap<String, User> tokenToUser,
        ConcurrentMap<String, String> playerToGame,
        ConcurrentMap<String, User> users) {
    private static final Logger logger = Logger.getLogger(DataCollectionsAggregator.class);
    private static final UserDao userDao = new UserDaoImpl();

    /**
     * Конструктор класса {@code DataCollectionsAggregator} для инициализации коллекций данных.
     *
     * @param idToGameSession Коллекция для игровых сессий.
     * @param tokenToUser     Коллекция для хранения авторизованных пользователей.
     * @param playerToGame    Коллекция для связей пользователей и игр.
     * @param users           Коллекция для хранения пользователей.
     */
    public DataCollectionsAggregator(
            final ConcurrentMap<String, GameSession> idToGameSession,
            final ConcurrentMap<String, User> tokenToUser,
            final ConcurrentMap<String, String> playerToGame,
            final ConcurrentMap<String, User> users) {
        this.idToGameSession = idToGameSession;
        this.tokenToUser = tokenToUser;
        this.playerToGame = playerToGame;
        this.users = users;
        try {
            for (var user : userDao.getAllUsers()) {
                users.put(user.login(), user);
            }
        } catch (ServerException e) {
            logger.debug("File with users data not found");
        }
    }

    /**
     * Конструктор класса {@code DataCollectionsAggregator} без параметров для создания пустых коллекций данных.
     */
    public DataCollectionsAggregator() {
        this(new ConcurrentHashMap<>(), new ConcurrentHashMap<>(), new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
    }
}
