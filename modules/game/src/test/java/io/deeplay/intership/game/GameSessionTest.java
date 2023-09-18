package io.deeplay.intership.game;

import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.model.Stone;
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
    public void testSetColor() throws ServerException {
        final GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        final Player joiner = new Player("hachapuri", Color.WHITE.name());
        gameSession.addPlayer(new Player("abobus", Color.BLACK.name()));
        assertDoesNotThrow(() -> gameSession.setColor(joiner));
    }


    @Test
    public void testSetColorFailure() throws ServerException {
        final GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        final Player joiner = new Player("hachapuri", Color.WHITE.name());
        gameSession.addPlayer(new Player("abobus", Color.BLACK.name()));
        gameSession.addPlayer(joiner);

        assertThrows(ServerException.class, () -> gameSession.setColor(joiner));
    }

    @Test
    public void testGetField() {
        final GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        var result = gameSession.getGameField();
        assertNotNull(result);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                assertNotNull(result[i][j]);
            }
        }
    }

    @Test
    public void testStartGame() {
        final GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        assertDoesNotThrow(gameSession::startGame);
    }

    @Test
    public void testGetNextPlayer() {
        final GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        assertDoesNotThrow(gameSession::getNextPlayer);
    }

    @Test
    public void testTurn() throws ServerException {
        final GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        final String playerName1 = "Player1";
        final String playerName2 = "Player2";
        final String color1 = Color.BLACK.name();
        final String color2 = Color.WHITE.name();
        final Player player1 = new Player(playerName1, color1);
        final Player player2 = new Player(playerName2, color2);

        gameSession.addPlayer(player1);
        gameSession.addPlayer(player2);
        gameSession.startGame();

        assertDoesNotThrow(() -> gameSession.turn(playerName1, new Stone(Color.BLACK, 0, 0)));
    }


    @Test
    public void testPass() throws ServerException {
        final GameSession gameSession = new GameSession(UUID.randomUUID().toString());
        final String playerName1 = "Player1";
        final String playerName2 = "Player2";
        final String color1 = Color.BLACK.name();
        final String color2 = Color.WHITE.name();
        final Player player1 = new Player(playerName1, color1);
        final Player player2 = new Player(playerName2, color2);

        gameSession.addPlayer(player1);
        gameSession.addPlayer(player2);
        gameSession.startGame();

        assertDoesNotThrow(() -> gameSession.pass(playerName1));
    }
}
