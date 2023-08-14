package io.deeplay.intership.game;

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
    public void testAnalyzeMove() {
        final Game game = new Game();
        final int x = 0;
        final int y = 0;

        Stone stone = new Stone(Color.BLACK, x, y);
        assertDoesNotThrow(() -> game.analyzeMove(stone));
    }

    @Test
    public void testAnalyzeMoveToSkip() {
        final Game game = new Game();
        final int x = 0;
        final int y = 0;

        Stone stone = new Stone(Color.EMPTY, x, y);
        assertDoesNotThrow(() -> game.analyzeMove(stone));
    }

    @Test
    public void testSkipTurnSuccess() {
        final Game game = new Game();
        final Color whiteColor = Color.WHITE;
        final Color blackColor = Color.BLACK;

        assertDoesNotThrow(() -> game.skipTurn(whiteColor));
        assertDoesNotThrow(() -> game.skipTurn(blackColor));
    }

    @Test
    public void testMakeMove() {
        final Game game = new Game();
        final Color color = Color.BLACK;
        final int x = 0;
        final int y = 0;
        Stone beforeChanges = new Stone(Color.EMPTY, x, y);
        Stone afterChanges = new Stone(color, x, y);

        assertEquals(beforeChanges, game.startGame().getField()[x][y]);

        Stone stone = new Stone(Color.BLACK, x, y);
        assertDoesNotThrow(() -> game.makeMove(stone));
        assertEquals(afterChanges, game.startGame().getField()[x][y]);
    }

    @Test
    public void testEndGame() {
        final Game game = new Game();
        assertDoesNotThrow(game::endGame);
    }

    @Test
    public void testGameIsOverFalse() {
        final Game game = new Game();
        assertDoesNotThrow(game::gameIsOver);
        assertFalse(game::gameIsOver);
    }

    @Test
    public void testGameIsOverTrue() {
        final Game game = new Game();
        final Color whiteColor = Color.WHITE;
        final Color blackColor = Color.BLACK;

        game.skipTurn(whiteColor);
        game.skipTurn(blackColor);

        assertDoesNotThrow(game::gameIsOver);
        assertTrue(game::gameIsOver);
    }
}
