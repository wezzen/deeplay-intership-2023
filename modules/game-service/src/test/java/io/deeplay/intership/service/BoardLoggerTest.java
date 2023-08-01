package io.deeplay.intership.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BoardLoggerTest {

  private static final int DEFAULT_BOARD_SIZE = 9;

  @Test
  public void simpleTest() {
    Board board = Mockito.mock(Board.class);
    Color color = Mockito.mock(Color.class);
    Group group = Mockito.mock(Group.class);
    Stone[][] stones = new Stone[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
    for (int i = 0; i < stones.length; i++) {
      for (int j = 0; j < stones[i].length; j++) {
        stones[i][j] = new Stone(color, i, j, group);
      }
    }
    when(board.getField()).thenReturn(stones);

    BoardLogger bl = new BoardLogger();
    bl.write(board);
  }

  @Test
  public void testReturnCellSymbol() {
    final int x = 1;
    final int y = 1;
    final Group group = Mockito.mock(Group.class);
    final BoardLogger boardLogger = new BoardLogger();
    final Stone emptyStone = new Stone(Color.EMPTY, x, y, group);
    final Stone whiteStone = new Stone(Color.WHITE, x, y, group);
    final Stone blackStone = new Stone(Color.BLACK, x, y, group);

    final char expectedEmpty = '0';
    final char expectedWhite = '+';
    final char expectedBlack = '-';
    assertAll(
        () -> assertEquals(expectedEmpty, boardLogger.getCellSymbol(emptyStone)),
        () -> assertEquals(expectedWhite, boardLogger.getCellSymbol(whiteStone)),
        () -> assertEquals(expectedBlack, boardLogger.getCellSymbol(blackStone))
    );

  }
}
