package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.service.Board;
import io.deeplay.intership.service.Color;
import io.deeplay.intership.service.Stone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserPlayerActionsTest {

    @Test
    public void testMakeMove() {
        final Color color = Color.BLACK;
        final UserPlayerActions terminalUI = mock(UserPlayerActions.class);
        final Board board = mock(Board.class);
        final Stone stone = new Stone(color, 1, 1);
        when(terminalUI.makeMove(board)).thenReturn(stone);

        assertEquals(stone, terminalUI.makeMove(board));
        assertDoesNotThrow(() -> terminalUI.makeMove(board));
    }

    @Test
    public void testChooseColor() {
        final UserPlayerActions terminalUI = mock(UserPlayerActions.class);
        final Color color = Color.BLACK;
        when(terminalUI.chooseColor()).thenReturn(color);

        assertDoesNotThrow(terminalUI::chooseColor);
        assertEquals(color, terminalUI.chooseColor());
    }


    @Test
    public void testSkipTurn() {
        final Color color = Color.BLACK;
        final UserPlayerActions terminalUI = new UserPlayerActions(color);
        assertDoesNotThrow(terminalUI::skipTurn);
    }
}
