package io.deeplay.intership.util.aggregator;

import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.User;

import java.util.Optional;

public class AggregatorUtil {
    private final DataCollectionsAggregator collectionsAggregator;

    public AggregatorUtil(DataCollectionsAggregator collectionsAggregator) {
        this.collectionsAggregator = collectionsAggregator;
    }

    public void addNewUser(final User user) {
        collectionsAggregator.users().put(user.login(), user);
    }

    public void addUsersToken(final String token, final User user) {
        collectionsAggregator.tokenToUser().put(token, user);
    }

    public void addUserToGame(final String token, final String gameId) {
        collectionsAggregator.playerToGame().put(token, gameId);
    }

    public void addGameSession(final String gameId, final GameSession gameSession) {
        collectionsAggregator.idToGameSession().put(gameId, gameSession);
    }

    public Optional<User> getUserByLogin(final String login) throws ServerException {
        return Optional.ofNullable(collectionsAggregator.users().get(login));
    }

    public User getUserByToken(final String token) throws ServerException {
        User user = collectionsAggregator.tokenToUser().get(token);
        if (user == null) {
            throw new ServerException(ErrorCode.NOT_AUTHORIZED);
        }
        return user;
    }


    public GameSession getGameSessionById(final String gameId) throws ServerException {
        GameSession gameSession = collectionsAggregator.idToGameSession().get(gameId);
        if (gameSession == null) {
            throw new ServerException(ErrorCode.GAME_NOT_FOUND);
        }
        return gameSession;
    }

    public GameSession getGameByUserToken(final String token) throws ServerException {
        final String gameId = collectionsAggregator.playerToGame().get(token);
        if (gameId == null) {
            throw new ServerException(ErrorCode.GAME_NOT_FOUND);
        }
        return getGameSessionById(gameId);
    }

    public void removeUsersToken(final String token) {
        collectionsAggregator.tokenToUser().remove(token);
    }
}
