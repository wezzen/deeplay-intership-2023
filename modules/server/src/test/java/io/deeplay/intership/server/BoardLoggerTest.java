package io.deeplay.intership.server;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.deeplay.inership.server.Board;
import io.deeplay.inership.server.BoardLogger;
import io.deeplay.inership.server.Cell;
import io.deeplay.inership.server.Color;
import io.deeplay.inership.server.Group;
import io.deeplay.inership.server.Stone;
import org.junit.jupiter.api.Test;

public class BoardLoggerTest {

  private static final int DEFAULT_BOARD_SIZE = 9;

  @Test
  public void simpleTest() {
    Board board = mock(Board.class);
    Color color = mock(Color.class);
    Cell cell = mock(Cell.class);
    Group group = mock(Group.class);
    Stone[][] stones = new Stone[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
    for (int i = 0; i < stones.length; i++) {
      for (int j = 0; j < stones[i].length; j++) {
        stones[i][j] = new Stone(color, cell, group);
      }
    }
    when(board.getField()).thenReturn(stones);

    BoardLogger bl = new BoardLogger();
    bl.write(board);
  }

  @Test
  public void testReturnCellSymbol() {
    final Cell cell = mock(Cell.class);
    final Group group = mock(Group.class);
    final BoardLogger boardLogger = new BoardLogger();
    final Stone emptyStone = new Stone(Color.EMPTY, cell, group);
    final Stone whiteStone = new Stone(Color.WHITE, cell, group);
    final Stone blackStone = new Stone(Color.BLACK, cell, group);

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
