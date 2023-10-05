package io.deeplay.intership.game;

import io.deeplay.intership.exception.game.GameException;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testStartGame() {
        final Game game = new Game();
        final Stone[][] expected = new Board().getField();

        assertDoesNotThrow(game::startGame);
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], game.startGame().getField()[i][j]);
            }
        }
    }

    @Test
    public void testSkipTurnSuccess() {
        final Game game = new Game();
        final Color whiteColor = Color.WHITE;
        final Color blackColor = Color.BLACK;

        assertDoesNotThrow(() -> game.skipTurn(whiteColor));
        assertThrows(GameException.class, () -> game.skipTurn(blackColor));
    }

    @Test
    public void testMakeMoveBlack() {
        final Game game = new Game();
        final Color color = Color.BLACK;
        final int x = 0;
        final int y = 0;
        Stone beforeChanges = new Stone(Color.EMPTY, x, y);
        Stone afterChanges = new Stone(color, x, y);

        assertEquals(beforeChanges, game.startGame().getField()[x][y]);

        Stone stone = new Stone(color, x, y);
        assertDoesNotThrow(() -> game.makeMove(stone));
        assertEquals(afterChanges, game.startGame().getField()[x][y]);
    }

    @Test
    public void testMakeMoveWhite() {
        final Game game = new Game();
        final Color color = Color.WHITE;
        final int x = 0;
        final int y = 0;
        Stone beforeChanges = new Stone(Color.EMPTY, x, y);
        Stone afterChanges = new Stone(color, x, y);

        assertEquals(beforeChanges, game.startGame().getField()[x][y]);

        Stone stone = new Stone(color, x, y);
        assertDoesNotThrow(() -> game.makeMove(stone));
        assertEquals(afterChanges, game.startGame().getField()[x][y]);
    }

    @Test
    public void testEndGame() {
        final Game game = new Game();
        assertDoesNotThrow(game::getGameScore);
    }

    @Test
    public void testGameIsOverFalse() {
        final Game game = new Game();
        assertDoesNotThrow(game::gameIsOver);
        assertFalse(game::gameIsOver);
    }

    @Test
    public void testGameIsOverTrue() throws GameException {
        final Game game = new Game();
        final Color whiteColor = Color.WHITE;
        final Color blackColor = Color.BLACK;

        game.skipTurn(whiteColor);
        try {
            game.skipTurn(blackColor);
        } catch (GameException ex) {

        }

        assertDoesNotThrow(game::gameIsOver);
        assertTrue(game::gameIsOver);
    }
}
