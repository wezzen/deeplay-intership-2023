package io.deeplay.intership.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BoardTest {

  @Test
  public void testBoardInitField() {
    Board board = new Board();
    board.getField();

    assertNotNull(board.getField());
    for (int i = 0; i < board.getField().length; i++) {
      for (int j = 0; j < board.getField()[i].length; j++) {
        assertEquals(Color.EMPTY, board.getField()[i][j].getColor());
        assertEquals(i, board.getField()[i][j].getCell().x());
        assertEquals(j, board.getField()[i][j].getCell().y());
      }
    }
  }

  @Test
  public void testBoardInitGroups() {
    Board board = new Board();
    board.getField();

    assertNotNull(board.getGroups());
    assertNotNull(board.getField());
    assertEquals(0, board.getGroups().size());
  }
}
