package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.service.Color;
import io.deeplay.intership.service.Stone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testConvertStringToInt_WithCorrectData1() {

        assertAll(
                () -> assertEquals(1, converter.convertStringToInt("A")),
                () -> assertEquals(2, converter.convertStringToInt("B")),
                () -> assertEquals(3, converter.convertStringToInt("C")),
                () -> assertEquals(4, converter.convertStringToInt("D")),
                () -> assertEquals(5, converter.convertStringToInt("E")),
                () -> assertEquals(6, converter.convertStringToInt("F")),
                () -> assertEquals(7, converter.convertStringToInt("G")),
                () -> assertEquals(8, converter.convertStringToInt("H")),
                () -> assertEquals(9, converter.convertStringToInt("J")),
                () -> assertEquals(10, converter.convertStringToInt("K")),
                () -> assertEquals(11, converter.convertStringToInt("L")),
                () -> assertEquals(12, converter.convertStringToInt("M")),
                () -> assertEquals(13, converter.convertStringToInt("N")),
                () -> assertEquals(14, converter.convertStringToInt("O")),
                () -> assertEquals(15, converter.convertStringToInt("P")),
                () -> assertEquals(16, converter.convertStringToInt("Q")),
                () -> assertEquals(17, converter.convertStringToInt("R")),
                () -> assertEquals(18, converter.convertStringToInt("S")),
                () -> assertEquals(19, converter.convertStringToInt("T"))
        );
    }

    @Test
    public void testConvertStringToInt_WithCorrectData2() {

        assertAll(
                () -> assertEquals(1, converter.convertStringToInt("a")),
                () -> assertEquals(2, converter.convertStringToInt("b")),
                () -> assertEquals(3, converter.convertStringToInt("c")),
                () -> assertEquals(4, converter.convertStringToInt("d")),
                () -> assertEquals(5, converter.convertStringToInt("e")),
                () -> assertEquals(6, converter.convertStringToInt("f")),
                () -> assertEquals(7, converter.convertStringToInt("g")),
                () -> assertEquals(8, converter.convertStringToInt("h")),
                () -> assertEquals(9, converter.convertStringToInt("j")),
                () -> assertEquals(10, converter.convertStringToInt("k")),
                () -> assertEquals(11, converter.convertStringToInt("l")),
                () -> assertEquals(12, converter.convertStringToInt("m")),
                () -> assertEquals(13, converter.convertStringToInt("n")),
                () -> assertEquals(14, converter.convertStringToInt("o")),
                () -> assertEquals(15, converter.convertStringToInt("p")),
                () -> assertEquals(16, converter.convertStringToInt("q")),
                () -> assertEquals(17, converter.convertStringToInt("r")),
                () -> assertEquals(18, converter.convertStringToInt("s")),
                () -> assertEquals(19, converter.convertStringToInt("t"))
        );
    }

    @Test
    public void testConvertStringToInt_WithCorrectData3() {

        assertAll(
                () -> assertEquals(1, converter.convertStringToInt("1")),
                () -> assertEquals(2, converter.convertStringToInt("2")),
                () -> assertEquals(3, converter.convertStringToInt("3")),
                () -> assertEquals(4, converter.convertStringToInt("4")),
                () -> assertEquals(5, converter.convertStringToInt("5")),
                () -> assertEquals(6, converter.convertStringToInt("6")),
                () -> assertEquals(7, converter.convertStringToInt("7")),
                () -> assertEquals(8, converter.convertStringToInt("8")),
                () -> assertEquals(9, converter.convertStringToInt("9")),
                () -> assertEquals(10, converter.convertStringToInt("10")),
                () -> assertEquals(11, converter.convertStringToInt("11")),
                () -> assertEquals(12, converter.convertStringToInt("12")),
                () -> assertEquals(13, converter.convertStringToInt("13")),
                () -> assertEquals(14, converter.convertStringToInt("14")),
                () -> assertEquals(15, converter.convertStringToInt("15")),
                () -> assertEquals(16, converter.convertStringToInt("16")),
                () -> assertEquals(17, converter.convertStringToInt("17")),
                () -> assertEquals(18, converter.convertStringToInt("18")),
                () -> assertEquals(19, converter.convertStringToInt("19"))
        );
    }

    @Test
    public void testConvertCharToInt_WithIncorrectData1() {
        final String input = "0";

        assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
    }

    @Test
    public void testConvertCharToInt_WithIncorrectData2() {
        final String input = "z";

        assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
    }

    @Test
    public void testConvertCharToInt_WithIncorrectData3() {
        final String input = "Z";

        assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
    }

    @Test
    public void testConvertCharToInt_WithIncorrectData4() {
        final String input = "20";

        assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
    }

    @Test
    public void testConvertCharToInt_WithIncorrectData5() {
        final String input = "-1";

        assertThrows(IllegalArgumentException.class, () -> converter.convertStringToInt(input));
    }

    @Test
    public void testCorrectConvertIntToChar1() {
        assertAll(
                () -> assertEquals('A', converter.convertIntToChar(1)),
                () -> assertEquals('B', converter.convertIntToChar(2)),
                () -> assertEquals('C', converter.convertIntToChar(3)),
                () -> assertEquals('D', converter.convertIntToChar(4)),
                () -> assertEquals('E', converter.convertIntToChar(5)),
                () -> assertEquals('F', converter.convertIntToChar(6)),
                () -> assertEquals('G', converter.convertIntToChar(7)),
                () -> assertEquals('H', converter.convertIntToChar(8)),
                () -> assertEquals('J', converter.convertIntToChar(9)),
                () -> assertEquals('K', converter.convertIntToChar(10)),
                () -> assertEquals('L', converter.convertIntToChar(11)),
                () -> assertEquals('M', converter.convertIntToChar(12)),
                () -> assertEquals('N', converter.convertIntToChar(13)),
                () -> assertEquals('O', converter.convertIntToChar(14)),
                () -> assertEquals('P', converter.convertIntToChar(15)),
                () -> assertEquals('Q', converter.convertIntToChar(16)),
                () -> assertEquals('R', converter.convertIntToChar(17)),
                () -> assertEquals('S', converter.convertIntToChar(18)),
                () -> assertEquals('T', converter.convertIntToChar(19))
        );
    }

    @Test
    public void testIncorrectConvertIntToChar_WithInvalidData() {
        final int inputNumber1 = 0;
        final int inputNumber2 = -1;
        final int inputNumber3 = 20;
        final int inputNumber4 = Integer.MAX_VALUE;
        final int inputNumber5 = Integer.MIN_VALUE;

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber1)),
                () -> assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber2)),
                () -> assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber3)),
                () -> assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber4)),
                () -> assertThrows(IllegalArgumentException.class, () -> converter.convertIntToChar(inputNumber5))
        );

    }

    @Test
    public void testGetStoneSymbol() {
        final Stone stone1 = new Stone(Color.WHITE, 0, 0);
        final Stone stone2 = new Stone(Color.BLACK, 0, 0);
        final Stone stone3 = new Stone(Color.EMPTY, 0, 0);
        final Stone stone4 = new Stone(null, 0, 0);

        assertAll(
                () -> assertDoesNotThrow(() -> converter.getStoneSymbol(stone1)),
                () -> assertDoesNotThrow(() -> converter.getStoneSymbol(stone2)),
                () -> assertDoesNotThrow(() -> converter.getStoneSymbol(stone3)),
                () -> assertEquals('+', converter.getStoneSymbol(stone1)),
                () -> assertEquals('-', converter.getStoneSymbol(stone2)),
                () -> assertEquals('0', converter.getStoneSymbol(stone3)),
                () -> assertThrows(NullPointerException.class, () -> converter.getStoneSymbol(stone4))
        );

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
        final String action1 = "1";
        final String action2 = "2";
        final String action3 = "3";
        final String action4 = "4";
        final String action5 = "5";
        final String action6 = "6";
        final String action7 = "7";
        final String action8 = "8";

        final UserAction expected1 = UserAction.START_GAME;
        final UserAction expected2 = UserAction.SKIP;
        final UserAction expected3 = UserAction.MOVE;
        final UserAction expected4 = UserAction.CHOOSE_COLOR;
        final UserAction expected5 = UserAction.END_GAME;
        final UserAction expected6 = UserAction.CHOOSE_WHITE_COLOR;
        final UserAction expected7 = UserAction.CHOOSE_BLACK_COLOR;
        final UserAction expected8 = UserAction.CHOOSE_EMPTY_COLOR;

        assertAll(
                () -> assertEquals(expected1, converter.convertStringToAction(action1)),
                () -> assertEquals(expected2, converter.convertStringToAction(action2)),
                () -> assertEquals(expected3, converter.convertStringToAction(action3)),
                () -> assertEquals(expected4, converter.convertStringToAction(action4)),
                () -> assertEquals(expected5, converter.convertStringToAction(action5)),
                () -> assertEquals(expected6, converter.convertStringToAction(action6)),
                () -> assertEquals(expected7, converter.convertStringToAction(action7)),
                () -> assertEquals(expected8, converter.convertStringToAction(action8))
        );
    }
}
