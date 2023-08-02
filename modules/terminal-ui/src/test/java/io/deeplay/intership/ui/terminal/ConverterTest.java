package io.deeplay.intership.ui.terminal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.deeplay.intership.service.Color;
import io.deeplay.intership.service.Stone;
import org.junit.jupiter.api.Test;

public class ConverterTest {

  private static final int MIN_STONE_VALUE = 0;
  private static final int MAX_STONE_VALUE = 8;
  private final Converter converter = new Converter();

  @Test
  public void testCorrectConvertStringToStone1() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.WHITE;
    String inputString = "1 a";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone2() {
    final int rowNumber = MAX_STONE_VALUE;
    final int colsNumber = MAX_STONE_VALUE;
    final Color color = Color.WHITE;
    String inputString = "9 j";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone3() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.WHITE;
    String inputString = "1 A";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone4() {
    final int rowNumber = MAX_STONE_VALUE;
    final int colsNumber = MAX_STONE_VALUE;
    final Color color = Color.WHITE;
    String inputString = "9 J";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }


  @Test
  public void testCorrectConvertStringToStone5() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MAX_STONE_VALUE;
    final Color color = Color.BLACK;
    String inputString = "1 J";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone6() {
    final int rowNumber = MAX_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.BLACK;
    String inputString = "9 A";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone7() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.BLACK;
    String inputString = " 1 A ";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone8() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.BLACK;
    String inputString = "1    A";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone9() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.BLACK;
    String inputString = "    1    A    ";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone1() {
    final Color color = Color.BLACK;
    String inputString = "0 b";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone2() {
    final Color color = Color.BLACK;
    String inputString = "1 z";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone3() {
    final Color color = Color.BLACK;
    String inputString = "-1 a";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone4() {
    final Color color = Color.BLACK;
    String inputString = "1 Z";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone5() {
    final Color color = Color.BLACK;
    String inputString = "20 b";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone6() {
    final Color color = Color.BLACK;
    String inputString = "1 U";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone7() {
    final Color color = Color.BLACK;
    String inputString = "20 b";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone8() {
    final Color color = Color.BLACK;
    String inputString = "1 u";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone9() {
    final Color color = Color.BLACK;
    String inputString = "20 U";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone10() {
    final Color color = Color.WHITE;
    String inputString = "1 1 A";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone11() {
    final Color color = Color.WHITE;
    String inputString = "1A";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone12() {
    final Color color = Color.WHITE;
    String inputString = "1 1 A A";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone13() {
    final Color color = Color.WHITE;
    String inputString = "11 AA";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone14() {
    final Color color = Color.WHITE;
    String inputString = "9 AA";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

}
