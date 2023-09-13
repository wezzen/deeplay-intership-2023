package io.deeplay.intership.selfplay.bot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SelfplayTest {
    @Test
    public void testMain() {
        assertDoesNotThrow(() -> new Selfplay());
    }

    @Test
    public void testStartGame() {
        assertDoesNotThrow(() -> new Selfplay().startGame());
    }
}
