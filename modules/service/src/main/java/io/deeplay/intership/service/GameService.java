package io.deeplay.intership.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameService {
    private final ConcurrentMap<String, GameSession> games;

    public GameService() {
        this.games = new ConcurrentHashMap<>();
    }

    public String createGame(final String request) {
        //TODO: give param for this method
        String uuid = UUID.randomUUID().toString();
        GameSession gameSession = new GameSession(uuid);
//        gameSession.addPlayer(player);
//        games.put(gameUUID, gameSession);

        return uuid;
    }

    public String joinGame(final String request) {
        /*GameSession gameSession = games.get(gameUUID);
        if (gameSession != null) {
            gameSession.addPlayer(player);
        }*/
        return null;
    }

    public String surrenderGame(final String request) {
        return null;
    }

    public String endGame(final String request) {
        return null;
    }

    public String turn(final String request) {
        return null;
    }

    public String pass(final String request) {
        return null;
    }
}
