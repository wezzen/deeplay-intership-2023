package io.deeplay.intership.service;

import io.deeplay.intership.game.Color;
import io.deeplay.intership.game.Group;
import io.deeplay.intership.game.Stone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class StoneTest {

    @Test
    public void testStone() {
        int x = 1;
        int y = 1;
        Color color = mock(Color.class);
        Group group = mock(Group.class);
        Stone stone = new Stone(color, x, y, group);

        assertAll(
                () -> assertNotNull(stone.getColor()),
                () -> assertEquals(color, stone.getColor()),

                () -> assertEquals(x, stone.getRowNumber()),
                () -> assertEquals(y, stone.getColumnNumber()),

                () -> assertNotNull(stone.getGroup()),
                () -> assertEquals(group, stone.getGroup())
        );
    }

    @Test
    public void testStoneConstructor() {
        int x = 1;
        int y = 1;
        Color color = mock(Color.class);
        Stone stone = new Stone(color, x, y);

        assertAll(
                () -> assertNotNull(stone.getColor()),
                () -> assertEquals(color, stone.getColor()),

                () -> assertEquals(x, stone.getRowNumber()),
                () -> assertEquals(y, stone.getColumnNumber()),

                () -> assertNull(stone.getGroup())
        );
    }

    @Test
    public void testEquals() {
        Stone stone1 = new Stone(Color.EMPTY, 1, 1);
        assertNotEquals(stone1, null);
    }
}
