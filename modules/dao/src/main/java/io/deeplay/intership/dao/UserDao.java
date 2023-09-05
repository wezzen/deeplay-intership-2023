package io.deeplay.intership.dao;

import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;

import java.util.Optional;

public interface UserDao {
    void addUser(User user) throws ServerException;

    Optional<User> getUserByLogin(String login) throws ServerException;

    void authorizeUser(User user, String token) throws ServerException;

    User getUserByToken(String token) throws ServerException;

    void removeAuth(String token) throws ServerException;
}
