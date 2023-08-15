package io.deeplay.intership.service;


import io.deeplay.intership.model.User;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserService {
    private final ConcurrentMap<String, User> users;

    public UserService() {
        this.users = new ConcurrentHashMap<>();
    }

    public boolean isAuthorized(final String token) {
        return users.containsKey(token);
    }

    public String authorization(final String request) {

        String token = UUID.randomUUID().toString();
//        users.put(token, user);
        return token;
    }

    public String register(final String request) {
        return null;
    }

    public String logout(final String request) {
        return null;
    }
}
