package io.deeplay.intership.server;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.response.gameplay.AnswerDtoResponse;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.GoPlayer;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ServerGameTest {
    private final StreamConnector streamConnector = mock(StreamConnector.class);
    private final Lock lock = mock(Lock.class);

    @Test
    public void testConstructor() {
        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);

        assertDoesNotThrow(() -> new ServerGame(gameSession));
    }

    @Test
    public void testJoinPlayer() {
        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        final ServerGame serverGame = new ServerGame(gameSession);
        final String name = "Name" + UUID.randomUUID();
        final Color color = Color.WHITE;
        final GoPlayer player = new ServerGoPlayer(streamConnector, lock, name, color);

        assertDoesNotThrow(() -> serverGame.joinPlayer(player));
    }

    @Test
    public void testIsCompletable() {
        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        final ServerGame serverGame = new ServerGame(gameSession);

        assertFalse(serverGame::isCompletable);
    }

    @Test
    public void testFullGame() {
        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        final ServerGame serverGame = new ServerGame(gameSession);

        final String firstPlayerName = "Name" + UUID.randomUUID();
        final Color blackColor = Color.BLACK;
        final GoPlayer firstPlayer = new ServerGoPlayer(streamConnector, lock, firstPlayerName, blackColor);

        final String secondPlayerName = "Name" + UUID.randomUUID();
        final Color whiteColor = Color.WHITE;
        final GoPlayer secondPlayer = new ServerGoPlayer(streamConnector, lock, secondPlayerName, whiteColor);

        assertFalse(serverGame::isCompletable);
        serverGame.joinPlayer(firstPlayer);
        assertFalse(serverGame::isCompletable);
        serverGame.joinPlayer(secondPlayer);
        assertTrue(serverGame::isCompletable);
    }

    @Test
    public void testIsFinished() {
        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        final ServerGame serverGame = new ServerGame(gameSession);

        assertFalse(serverGame::isFinished);
    }

    @Test
    public void testNotifyAnswer() throws IOException {
        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        final ServerGame serverGame = new ServerGame(gameSession);

        final int size = 9;
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final String infoMessage = ResponseInfoMessage.CAN_TURN.message;
        final Stone[][] field = new Stone[size][size];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Stone(Color.EMPTY, i, j);
            }
        }
        AnswerDtoResponse result = (AnswerDtoResponse) serverGame.notifyAnswer(field);
        assertAll(
                () -> assertEquals(status, result.status),
                () -> assertEquals(infoMessage, result.message),
                () -> assertArrayEquals(field, result.gameField)
        );
    }
}
