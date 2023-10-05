package io.deeplay.intership.game;

import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckGameOverTest {
    private static final int STONE_COUNT = 160;

    @Test
    public void testMaxCanMakeMove() {
        CheckGameOver checkGameOver = new CheckGameOver();
        Color color = Color.BLACK;

        for (int i = 0; i < STONE_COUNT; i++) {
            assertTrue(checkGameOver.canMakeMove(color));
        }
    }

    @Test
    public void testCanMakeMove_ForDifferentColors() {
        CheckGameOver checkGameOver = new CheckGameOver();
        assertAll(
                () -> assertTrue(checkGameOver.canMakeMove(Color.BLACK)),
                () -> assertTrue(checkGameOver.canMakeMove(Color.WHITE)),
                () -> assertThrows(IllegalArgumentException.class, () -> checkGameOver.canMakeMove(Color.EMPTY))
        );
    }

    @Test
    public void testCanMakeMove_ForTwoStones() {
        CheckGameOver checkGameOver = new CheckGameOver();
        for (int i = 0; i < STONE_COUNT; i++) {
            assertTrue(checkGameOver.canMakeMove(Color.BLACK));
            assertTrue(checkGameOver.canMakeMove(Color.WHITE));
        }
    }

    @Test
    public void testOverMaxMakeMove() {
        CheckGameOver checkGameOver = new CheckGameOver();
        Color color = Color.BLACK;

        for (int i = 0; i < STONE_COUNT; i++) {
            assertTrue(checkGameOver.canMakeMove(color));
        }
        assertFalse(checkGameOver.canMakeMove(color));
    }

    @Test
    public void testCanSkipTurn() {
        CheckGameOver checkGameOver = new CheckGameOver();
        assertTrue(checkGameOver.canSkipTurn());
        assertFalse(checkGameOver.canSkipTurn());
    }

    @Test
    public void testResetSkipCount() {
        CheckGameOver checkGameOver = new CheckGameOver();
        assertTrue(checkGameOver.canSkipTurn());
        checkGameOver.resetSkipCount();
        assertTrue(checkGameOver.canSkipTurn());
    }
}
