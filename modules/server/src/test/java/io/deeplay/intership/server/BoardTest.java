package io.deeplay.intership.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.deeplay.inership.server.Board;
import io.deeplay.inership.server.Color;
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
        assertEquals(i, board.getField()[i][j].getCell().getX());
        assertEquals(j, board.getField()[i][j].getCell().getY());
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
