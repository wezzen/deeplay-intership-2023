package io.deeplay.intership.sandbox.bot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SandboxTest {
    @Test
    public void testMain() {
        assertDoesNotThrow(() -> new Sandbox());
    }

    @Test
    public void testStartGame() {
        assertDoesNotThrow(() -> new Sandbox().startGame());
    }
}
