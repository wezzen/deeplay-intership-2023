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
    final String inputString = "1 a";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone2() {
    final int rowNumber = MAX_STONE_VALUE;
    final int colsNumber = MAX_STONE_VALUE;
    final Color color = Color.WHITE;
    final String inputString = "9 j";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone3() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.WHITE;
    final String inputString = "1 A";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone4() {
    final int rowNumber = MAX_STONE_VALUE;
    final int colsNumber = MAX_STONE_VALUE;
    final Color color = Color.WHITE;
    final String inputString = "9 J";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }


  @Test
  public void testCorrectConvertStringToStone5() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MAX_STONE_VALUE;
    final Color color = Color.BLACK;
    final String inputString = "1 J";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone6() {
    final int rowNumber = MAX_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.BLACK;
    final String inputString = "9 A";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone7() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.BLACK;
    final String inputString = " 1 A ";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone8() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.BLACK;
    final String inputString = "1    A";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertStringToStone9() {
    final int rowNumber = MIN_STONE_VALUE;
    final int colsNumber = MIN_STONE_VALUE;
    final Color color = Color.BLACK;
    final String inputString = "    1    A    ";

    Stone expected = new Stone(color, rowNumber, colsNumber);
    assertEquals(expected, converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone1() {
    final Color color = Color.BLACK;
    final String inputString = "0 b";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone2() {
    final Color color = Color.BLACK;
    final String inputString = "1 z";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone3() {
    final Color color = Color.BLACK;
    final String inputString = "-1 a";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone4() {
    final Color color = Color.BLACK;
    final String inputString = "1 Z";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone5() {
    final Color color = Color.BLACK;
    final String inputString = "20 b";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone6() {
    final Color color = Color.BLACK;
    final String inputString = "1 U";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone7() {
    final Color color = Color.BLACK;
    final String inputString = "20 b";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone8() {
    final Color color = Color.BLACK;
    final String inputString = "1 u";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone9() {
    final Color color = Color.BLACK;
    final String inputString = "20 U";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone10() {
    final Color color = Color.WHITE;
    final String inputString = "1 1 A";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone11() {
    final Color color = Color.WHITE;
    final String inputString = "1A";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone12() {
    final Color color = Color.WHITE;
    final String inputString = "1 1 A A";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone13() {
    final Color color = Color.WHITE;
    final String inputString = "11 AA";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testIncorrectConvertStringToStone14() {
    final Color color = Color.WHITE;
    final String inputString = "9 AA";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertStringToStone(inputString, color));
  }

  @Test
  public void testCorrectConvertCharToInt1() {
    final String input = "A";
    final int expected = 1;

    assertEquals(expected, converter.convertStringToInt(input));
  }

  @Test
  public void testCorrectConvertCharToInt2() {
    final String input = "a";
    final int expected = 1;

    assertEquals(expected, converter.convertStringToInt(input));
  }

  @Test
  public void testCorrectConvertCharToInt3() {
    final String input = "1";
    final int expected = 1;

    assertEquals(expected, converter.convertStringToInt(input));
  }

  @Test
  public void testCorrectConvertCharToInt4() {
    final String input = "J";
    final int expected = 9;

    assertEquals(expected, converter.convertStringToInt(input));
  }

  @Test
  public void testCorrectConvertCharToInt5() {
    final String input = "j";
    final int expected = 9;

    assertEquals(expected, converter.convertStringToInt(input));
  }

  @Test
  public void testCorrectConvertCharToInt6() {
    final String input = "9";
    final int expected = 9;

    assertEquals(expected, converter.convertStringToInt(input));
  }

  @Test
  public void testCorrectConvertCharToInt7() {
    final String input = "T";
    final int expected = 19;

    assertEquals(expected, converter.convertStringToInt(input));
  }

  @Test
  public void testCorrectConvertCharToInt8() {
    final String input = "t";
    final int expected = 19;

    assertEquals(expected, converter.convertStringToInt(input));
  }

  @Test
  public void testCorrectConvertCharToInt9() {
    final String input = "19";
    final int expected = 19;

    assertEquals(expected, converter.convertStringToInt(input));
  }

  @Test
  public void testIncorrectConvertCharToInt1() {
    final String input = "0";

    assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
  }

  @Test
  public void testIncorrectConvertCharToInt2() {
    final String input = "z";

    assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
  }

  @Test
  public void testIncorrectConvertCharToInt3() {
    final String input = "Z";

    assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
  }

  @Test
  public void testIncorrectConvertCharToInt4() {
    final String input = "20";

    assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
  }

  @Test
  public void testIncorrectConvertCharToInt5() {
    final String input = "-1";

    assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
  }

  @Test
  public void testCorrectConvertIntToChar1() {
    final int inputNumber = 1;

    final char expected = 'A';
    assertEquals(expected, converter.convertIntToChar(inputNumber));
  }

  @Test
  public void testCorrectConvertIntToChar2() {
    final int inputNumber = 19;

    final char expected = 'T';
    assertEquals(expected, converter.convertIntToChar(inputNumber));
  }

  @Test
  public void testCorrectConvertIntToChar3() {
    final int inputNumber = 7;

    final char expected = 'G';
    assertEquals(expected, converter.convertIntToChar(inputNumber));
  }

  @Test
  public void testIncorrectConvertIntToChar1() {
    final int inputNumber = 0;

    assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber));
  }

  @Test
  public void testIncorrectConvertIntToChar2() {
    final int inputNumber = -1;

    assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber));
  }

  @Test
  public void testIncorrectConvertIntToChar3() {
    final int inputNumber = 20;

    assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber));
  }

  @Test
  public void testIncorrectConvertIntToChar4() {
    final int inputNumber = Integer.MAX_VALUE;

    assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber));
  }

  @Test
  public void testIncorrectConvertIntToChar5() {
    final int inputNumber = Integer.MIN_VALUE;

    assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber));
  }

  @Test
  public void testCorrectGetStoneSymbol1() {
    final Color color = Color.WHITE;
    final Stone stone = new Stone(color, 0, 0);

    final char expected = '+';
    assertEquals(expected, converter.getStoneSymbol(stone));
  }

  @Test
  public void testCorrectGetStoneSymbol2() {
    final Color color = Color.BLACK;
    final Stone stone = new Stone(color, 0, 0);

    final char expected = '-';
    assertEquals(expected, converter.getStoneSymbol(stone));
  }

  @Test
  public void testCorrectGetStoneSymbol3() {
    final Color color = Color.EMPTY;
    final Stone stone = new Stone(color, 0, 0);

    final char expected = '0';
    assertEquals(expected, converter.getStoneSymbol(stone));
  }

  @Test
  public void testIncorrectGetStoneSymbol() {
    final Color color = null;
    final Stone stone = new Stone(color, 0, 0);

    assertThrows(NullPointerException.class, () -> converter.getStoneSymbol(stone));
  }

  @Test
  public void testCorrectConvertActionToColor1() {
    final UserAction action = UserAction.CHOOSE_BLACK_COLOR;
    final Color expected = Color.BLACK;

    assertEquals(expected, converter.convertActionToColor(action));
  }

  @Test
  public void testCorrectConvertActionToColor2() {
    final UserAction action = UserAction.CHOOSE_WHITE_COLOR;
    final Color expected = Color.WHITE;

    assertEquals(expected, converter.convertActionToColor(action));
  }

  @Test
  public void testCorrectConvertActionToColor3() {
    final UserAction action = UserAction.CHOOSE_EMPTY_COLOR;
    final Color expected = Color.EMPTY;

    assertEquals(expected, converter.convertActionToColor(action));
  }

  @Test
  public void testIncorrectConvertActionToColor1() {
    final UserAction action = UserAction.START_GAME;

    assertThrows(IllegalArgumentException.class, () -> converter.convertActionToColor(action));
  }

  @Test
  public void testIncorrectConvertActionToColor2() {
    final UserAction action = UserAction.SKIP;

    assertThrows(IllegalArgumentException.class, () -> converter.convertActionToColor(action));
  }

  @Test
  public void testIncorrectConvertActionToColor3() {
    final UserAction action = UserAction.MOVE;

    assertThrows(IllegalArgumentException.class, () -> converter.convertActionToColor(action));
  }

  @Test
  public void testIncorrectConvertActionToColor4() {
    final UserAction action = UserAction.CHOOSE_COLOR;

    assertThrows(IllegalArgumentException.class, () -> converter.convertActionToColor(action));
  }

  @Test
  public void testIncorrectConvertActionToColor5() {
    final UserAction action = UserAction.END_GAME;

    assertThrows(IllegalArgumentException.class, () -> converter.convertActionToColor(action));
  }

  @Test
  public void testCorrectConvertStringToAction1() {
    final String action = "1";
    final UserAction expected = UserAction.START_GAME;

    assertEquals(expected, converter.convertStringToAction(action));
  }

  @Test
  public void testCorrectConvertStringToAction2() {
    final String action = "2";
    final UserAction expected = UserAction.SKIP;

    assertEquals(expected, converter.convertStringToAction(action));
  }

  @Test
  public void testCorrectConvertStringToAction3() {
    final String action = "3";
    final UserAction expected = UserAction.MOVE;

    assertEquals(expected, converter.convertStringToAction(action));
  }

  @Test
  public void testCorrectConvertStringToAction4() {
    final String action = "4";
    final UserAction expected = UserAction.CHOOSE_COLOR;

    assertEquals(expected, converter.convertStringToAction(action));
  }

  @Test
  public void testCorrectConvertStringToAction5() {
    final String action = "5";
    final UserAction expected = UserAction.END_GAME;

    assertEquals(expected, converter.convertStringToAction(action));
  }

  @Test
  public void testCorrectConvertStringToAction6() {
    final String action = "6";
    final UserAction expected = UserAction.CHOOSE_WHITE_COLOR;

    assertEquals(expected, converter.convertStringToAction(action));
  }

  @Test
  public void testCorrectConvertStringToAction7() {
    final String action = "7";
    final UserAction expected = UserAction.CHOOSE_BLACK_COLOR;

    assertEquals(expected, converter.convertStringToAction(action));
  }

  @Test
  public void testCorrectConvertStringToAction8() {
    final String action = "8";
    final UserAction expected = UserAction.CHOOSE_EMPTY_COLOR;

    assertEquals(expected, converter.convertStringToAction(action));
  }

}
