package io.deeplay.intership.util.aggregator;

import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public record DataCollectionsAggregator(
        ConcurrentMap<String, GameSession> idToGameSession,
        ConcurrentMap<String, User> tokenToUser,
        ConcurrentMap<String, String> playerToGame) {

    public DataCollectionsAggregator() {
        this(new ConcurrentHashMap<>(), new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
    }
}
