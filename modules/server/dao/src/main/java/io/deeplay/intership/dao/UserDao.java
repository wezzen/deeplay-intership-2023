package io.deeplay.intership.dao;

import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user) throws ServerException;

    List<User> getAllUsers() throws ServerException;
}
