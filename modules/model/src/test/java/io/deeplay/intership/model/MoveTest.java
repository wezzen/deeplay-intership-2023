package io.deeplay.intership.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {
    @Test
    public void testConstructor() {
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final int row = 0;
        final int column = 0;
        final Move move = new Move(
                token,
                color,
                row,
                column
        );
        assertAll(
                () -> assertDoesNotThrow(() -> new Move(token, color, row, column)),
                () -> assertEquals(token, move.token()),
                () -> assertEquals(color, move.color()),
                () -> assertEquals(row, move.row()),
                () -> assertEquals(column, move.column())
        );

    }
}
