package io.deeplay.intership.game;

import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Player;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionTest {
    @Test
    public void testConstructor() {
        final String gameId = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> new GameSession(gameId));
    }

    @Test
    public void testRepeatedPlayerException(){
        GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        gameSession.addCreator(new Player("abobus", "Black"));
        assertThrows(new ServerException(ErrorCode.REPEATED_PLAYER).getClass(), () -> gameSession.addPlayer(new Player("abobus", "White")));
    }

    @Test
    public void testSetColor() throws ServerException {
        GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        Player joiner = new Player("hachapuri", "White");
        gameSession.addCreator(new Player("abobus", "Black"));
        gameSession.addPlayer(joiner);
        assertDoesNotThrow(() -> gameSession.setColor(joiner));
    }
}
