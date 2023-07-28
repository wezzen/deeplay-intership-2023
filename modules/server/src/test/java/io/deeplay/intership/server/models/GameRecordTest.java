package io.deeplay.intership.server.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import io.deeplay.inership.server.models.Board;
import io.deeplay.inership.server.models.GameRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class GameRecordTest {

  @Test
  public void testEmptyRecord() {
    GameRecord gr = new GameRecord();
    List<Board> expected = new ArrayList<>();
    assertEquals(expected, gr.getGameHistory());
  }

  @Test
  public void testFilledRecord() {
    GameRecord gr = new GameRecord();
    Board board1 = mock(Board.class);
    Board board2 = mock(Board.class);

    List<Board> list = new ArrayList<>();
    list.add(board1);
    gr.write(board1);
    list.add(board2);
    gr.write(board2);

    List<Board> expectedResult = gr.getGameHistory();
    assertAll(
        () -> assertArrayEquals(expectedResult.toArray(), list.toArray()),
        () -> assertEquals(expectedResult.get(0), list.get(0)),
        () -> assertEquals(expectedResult.get(1), list.get(1))
    );
  }

  @Test
  public void testRandomRecordSize() {
    final int minCount = 1;
    final int maxCount = 200;
    int countOfRecords = new Random().nextInt(maxCount - minCount + 1) + minCount;

    GameRecord gr = new GameRecord();
    List<Board> list = new ArrayList<>();

    for (int i = 0; i < countOfRecords; i++) {
      Board board = mock(Board.class);
      list.add(board);
      gr.write(board);
    }

    List<Board> expectedResult = gr.getGameHistory();
    assertArrayEquals(expectedResult.toArray(), list.toArray());
    for (int i = 0; i < countOfRecords; i++) {
      assertEquals(expectedResult.get(i), list.get(i));
    }
  }

  @Test
  public void test() {

  }
}
