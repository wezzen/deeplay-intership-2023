package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.model.Move;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserPlayerActionsTest {

    @Test
    public void testMakeMove() {
        final String token = UUID.randomUUID().toString();
        final Color color = Color.BLACK;
        final UserPlayerActions terminalUI = mock(UserPlayerActions.class);
        final Board board = mock(Board.class);
        final Move move = new Move(token, color.name(), 1, 1);

        when(terminalUI.makeMove(board.getField())).thenReturn(move);

        assertEquals(move, terminalUI.makeMove(board.getField()));
        assertDoesNotThrow(() -> terminalUI.makeMove(board.getField()));
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
        final UserPlayerActions terminalUI = new UserPlayerActions();
        assertDoesNotThrow(terminalUI::skipTurn);
    }
}
