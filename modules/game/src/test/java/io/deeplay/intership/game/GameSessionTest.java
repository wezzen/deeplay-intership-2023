package io.deeplay.intership.game;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameSessionTest {
    @Test
    public void testConstructor() {
        final String gameId = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> new GameSession(gameId));
    }
}
