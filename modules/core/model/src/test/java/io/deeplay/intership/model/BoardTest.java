package io.deeplay.intership.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testBoardInitField() {
        Board board = new Board();
        board.getField();

        assertNotNull(board.getField());
        for (int i = 0; i < board.getField().length; i++) {
            for (int j = 0; j < board.getField()[i].length; j++) {
                assertEquals(Color.EMPTY, board.getField()[i][j].getColor());
                assertEquals(i, board.getField()[i][j].getRowNumber());
                assertEquals(j, board.getField()[i][j].getColumnNumber());
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

    @Test
    public void testUpdateLastMoveState() {
        final int x = 0;
        final int y = 0;
        final Stone blackStone = new Stone(Color.BLACK, x, y);
        final Stone whiteStone = new Stone(Color.WHITE, x, y);
        final Stone emptyStone = new Stone(Color.EMPTY, x, y);
        final Stone nullStone = null;
        final Board board = new Board();

        assertAll(
                () -> assertDoesNotThrow(() -> board.updateLastMoveState(blackStone)),
                () -> assertDoesNotThrow(() -> board.updateLastMoveState(whiteStone)),
                () -> assertThrows(IllegalStateException.class, () -> board.updateLastMoveState(emptyStone)),
                () -> assertThrows(NullPointerException.class, () -> board.updateLastMoveState(nullStone))
        );
    }

    @Test
    public void testResetLastMoveState() {
        final Color colorBlack = Color.BLACK;
        final Color colorWhite = Color.WHITE;
        final Color colorEmpty = Color.EMPTY;
        final Color nullColor = null;
        final Board board = new Board();

        assertAll(
                () -> assertDoesNotThrow(() -> board.resetLastMoveState(colorBlack)),
                () -> assertDoesNotThrow(() -> board.resetLastMoveState(colorWhite)),
                () -> assertThrows(IllegalStateException.class, () -> board.resetLastMoveState(colorEmpty)),
                () -> assertThrows(NullPointerException.class, () -> board.resetLastMoveState(nullColor))
        );
    }

    @Test
    public void testGetLastMoveByColor() {
        final Color colorBlack = Color.BLACK;
        final Color colorWhite = Color.WHITE;
        final Color colorEmpty = Color.EMPTY;
        final Color nullColor = null;
        final Board board = new Board();

        assertAll(
                () -> assertDoesNotThrow(() -> board.getLastMoveByColor(colorBlack)),
                () -> assertNull(board.getLastMoveByColor(colorBlack)),

                () -> assertDoesNotThrow(() -> board.getLastMoveByColor(colorWhite)),
                () -> assertNull(board.getLastMoveByColor(colorWhite)),

                () -> assertThrows(IllegalStateException.class, () -> board.getLastMoveByColor(colorEmpty)),
                () -> assertThrows(NullPointerException.class, () -> board.getLastMoveByColor(nullColor))
        );


    }
}
