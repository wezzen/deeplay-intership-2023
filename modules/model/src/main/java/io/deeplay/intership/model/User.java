package io.deeplay.intership.model;

public record User(String login, String passwordHash, String token) {
    public User(String login, String passwordHash) {
        this(login, passwordHash, null);
    }
}
