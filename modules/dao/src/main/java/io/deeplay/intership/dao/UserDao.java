package io.deeplay.intership.dao;

import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;

import java.util.Optional;

public interface UserDao {
    void addUser(User user) throws ServerException;

    Optional<User> getUserByLogin(String login) throws ServerException;

    @Deprecated
    void authorizeUser(User user, String token) throws ServerException;

    @Deprecated
    User getUserByToken(String token) throws ServerException;

    @Deprecated
    void removeAuth(String token) throws ServerException;
}
