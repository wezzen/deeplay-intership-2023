package io.deeplay.intership.server;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoRequest;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoType;
import io.deeplay.intership.model.AnswerType;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerGoPlayerTest {
    private final StreamConnector streamConnector = mock(StreamConnector.class);
    private final Lock lock = mock(Lock.class);

    @Test
    public void testConstructor() {
        final String name = "Name" + UUID.randomUUID();
        final Color color = Color.BLACK;

        assertDoesNotThrow(() -> new ServerGoPlayer(streamConnector, lock, name, color));
    }

    @Test
    public void testGetters() {
        final String name = "Name" + UUID.randomUUID();
        final Color color = Color.BLACK;
        final ServerGoPlayer serverGoPlayer = new ServerGoPlayer(streamConnector, lock, name, color);

        assertAll(
                () -> assertEquals(name, serverGoPlayer.getName()),
                () -> assertEquals(color, serverGoPlayer.getColor())
        );
    }

    @Test
    public void testStartGame() {
        final String name = "Name" + UUID.randomUUID();
        final Color color = Color.BLACK;
        final ServerGoPlayer serverGoPlayer = new ServerGoPlayer(streamConnector, lock, name, color);

        assertDoesNotThrow(serverGoPlayer::startGame);
    }

    @Test
    public void testEndGame() {
        final String name = "Name" + UUID.randomUUID();
        final Color color = Color.BLACK;
        final ServerGoPlayer serverGoPlayer = new ServerGoPlayer(streamConnector, lock, name, color);

        assertDoesNotThrow(serverGoPlayer::endGame);
    }

    @Test
    public void testGetGameAction() throws IOException {
        final String name = "Name" + UUID.randomUUID();
        final Color color = Color.BLACK;
        final ServerGoPlayer serverGoPlayer = new ServerGoPlayer(streamConnector, lock, name, color);
        final int row = 0;
        final int column = 0;
        final AnswerDtoRequest dtoRequest = new AnswerDtoRequest(AnswerDtoType.TURN, row, column);

        when(streamConnector.getRequest()).thenReturn(dtoRequest);
        final var res = serverGoPlayer.getGameAction();
        assertAll(
                () -> assertEquals(AnswerType.TURN, res.answerType()),
                () -> assertEquals(row, res.row()),
                () -> assertEquals(column, res.column())
        );
    }
}
