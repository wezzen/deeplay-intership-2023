package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.service.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisplayTest {
    private final Display display = new Display();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStream() {
        System.setOut(originalOut);
    }

    @Test
    public void testDrawLine() {
        display.showHorizontalLine();
        final String expected = "====================================================================\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void testShowHorizontalBorder() {
        final Board board = new Board();
        final int symbolsForDisplay = 3;
        final String expected = " " + "#".repeat(symbolsForDisplay * board.getField().length + 2) + "\n";

        display.showHorizontalBorder(board);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void testShowColorSelection() {
        display.showColorSelection();
        final int symbolCount = 193;
        assertEquals(symbolCount, outContent.toString().length());
    }

    @Test
    public void testMoveRules() {
        String expected = "Введите координаты нового камня через пробел\n" +
                "Формат записи: 1 d\n" +
                "Диапазон допустимых значений для строки от 1 до 9\n" +
                "Введите координаты нового камня через пробел:\n";
        display.showMoveRules();
        assertEquals(expected, outContent.toString());
    }
}
