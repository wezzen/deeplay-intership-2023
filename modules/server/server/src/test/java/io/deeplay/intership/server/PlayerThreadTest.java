package io.deeplay.intership.server;

import io.deeplay.intership.game.GameSession;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class PlayerThreadTest {
    private final Lock lock = mock(Lock.class);

    @Test
    public void testConstructor() {
        final String gameId = UUID.randomUUID().toString();
        final ServerGame serverGame = new ServerGame(new GameSession(gameId));
        assertDoesNotThrow(() -> new PlayerThread(serverGame, lock));
    }

    @Test
    public void testRun() {
        final String gameId = UUID.randomUUID().toString();
        final ServerGame serverGame = new ServerGame(new GameSession(gameId));
        final PlayerThread playerThread = new PlayerThread(serverGame, lock);
        assertDoesNotThrow(playerThread::start);
    }
}
