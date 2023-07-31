package io.deeplay.intership.server;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.deeplay.inership.server.Cell;
import io.deeplay.inership.server.Color;
import io.deeplay.inership.server.Group;
import io.deeplay.inership.server.Stone;
import org.junit.jupiter.api.Test;

public class StoneTest {

  @Test
  public void testStone() {
    Cell cell = mock(Cell.class);
    Color color = mock(Color.class);
    Group group = mock(Group.class);
    Stone stone = new Stone(color, cell, group);

    assertAll(
        () -> assertNotNull(stone.getColor()),
        () -> assertEquals(color, stone.getColor()),

        () -> assertNotNull(stone.getCell()),
        () -> assertEquals(cell, stone.getCell()),

        () -> assertNotNull(stone.getGroup()),
        () -> assertEquals(group, stone.getGroup())
    );


  }
}
