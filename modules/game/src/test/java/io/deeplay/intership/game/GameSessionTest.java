package io.deeplay.intership.game;

import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameSessionTest {
    @Test
    public void testConstructor() {
        final String gameId = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> new GameSession(gameId));
    }

    @Test
    public void testSetColor() throws ServerException {
        GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        Player joiner = new Player("hachapuri", Color.WHITE.name());
        gameSession.addPlayer(new Player("abobus", Color.BLACK.name()));
        assertDoesNotThrow(() -> gameSession.setColor(joiner));
    }


    @Test
    public void testSetColorFailure() throws ServerException {
        GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        Player joiner = new Player("hachapuri", Color.WHITE.name());
        gameSession.addPlayer(new Player("abobus", Color.BLACK.name()));
        gameSession.addPlayer(joiner);

        assertThrows(ServerException.class, () -> gameSession.setColor(joiner));
    }
}
