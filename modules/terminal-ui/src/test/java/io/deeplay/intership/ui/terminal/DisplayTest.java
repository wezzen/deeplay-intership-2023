package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DisplayTest {
    private final Display display = new Display();

    @Test
    public void testShowMoveRules() {
        assertDoesNotThrow(display::showMoveRules);
    }

    @Test
    public void testShowGameActions() {
        assertDoesNotThrow(display::showGameActions);
    }

    @Test
    public void testShowAwaitState() {
        assertDoesNotThrow(display::showAwaitState);
        assertDoesNotThrow(display::showAuthorizationActions);
        assertDoesNotThrow(display::showLogin);
        assertDoesNotThrow(display::showRegistration);
        assertDoesNotThrow(display::showRoomActions);
        assertDoesNotThrow(display::showJoin);
    }

    @Test
    public void testShowBoard() {
        final Board board = new Board();
        board.getField()[2][3].setColor(Color.BLACK);
        board.getField()[3][2].setColor(Color.WHITE);
        assertDoesNotThrow(() -> display.showBoard(board.getField()));
    }

    @Test
    public void testShowBoardNumeric() {
        final Board board = new Board();
        assertDoesNotThrow(() -> display.showBoardNumeric(board.getField()));
    }

    @Test
    public void testShowHorizontalBorder() {
        Board board = new Board();
        assertDoesNotThrow(() -> display.showHorizontalBorder(board.getField()));
    }

    @Test
    public void testShowHorizontalLine() {
        assertDoesNotThrow(display::showHorizontalLine);
    }

    @Test
    public void testShowColorSelection() {
        assertDoesNotThrow(display::showColorSelection);
    }

    @Test
    public void testShowGameResult() {
        assertDoesNotThrow(() -> display.showGameResult(""));
    }
}
