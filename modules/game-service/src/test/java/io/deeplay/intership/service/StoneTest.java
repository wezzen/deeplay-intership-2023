package io.deeplay.intership.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StoneTest {

  @Test
  public void testStone() {
    Cell cell = Mockito.mock(Cell.class);
    Color color = Mockito.mock(Color.class);
    Group group = Mockito.mock(Group.class);
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
