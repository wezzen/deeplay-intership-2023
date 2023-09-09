package io.deeplay.intership.util.aggregator;

import io.deeplay.intership.dao.UserDao;
import io.deeplay.intership.dao.file.UserDaoImpl;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public record DataCollectionsAggregator(
        ConcurrentMap<String, GameSession> idToGameSession,
        ConcurrentMap<String, User> tokenToUser,
        ConcurrentMap<String, String> playerToGame,
        ConcurrentMap<String, User> users) {
    private static final Logger logger = Logger.getLogger(DataCollectionsAggregator.class);
    private static final UserDao userDao = new UserDaoImpl();

    public DataCollectionsAggregator(
            ConcurrentMap<String, GameSession> idToGameSession,
            ConcurrentMap<String, User> tokenToUser,
            ConcurrentMap<String, String> playerToGame,
            ConcurrentMap<String, User> users) {
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

    public DataCollectionsAggregator() {
        this(new ConcurrentHashMap<>(), new ConcurrentHashMap<>(), new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
    }
}
