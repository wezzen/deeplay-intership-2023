package io.deeplay.intership.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

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
}
