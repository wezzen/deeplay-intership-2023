package io.deeplay.intership.sandbox.bot;

import io.deeplay.intership.exception.ServerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {
    @Test
    public void testMain() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
