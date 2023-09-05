package io.deeplay.intership.dao;

import io.deeplay.intership.exception.ServerException;

public interface GameDao {
    void addActiveUser(String login, String gameId) throws ServerException;

    String getGameIdByPlayerLogin(String login) throws ServerException;
}
