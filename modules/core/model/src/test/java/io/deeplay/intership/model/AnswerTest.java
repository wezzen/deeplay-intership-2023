package io.deeplay.intership.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerTest {
    @Test
    public void testConstructor() {
        final AnswerType answerType = AnswerType.TURN;
        final int row = 0;
        final int column = 0;

        assertDoesNotThrow(() -> new Answer(answerType, row, column));
    }

    @Test
    public void testGettersTurn() {
        final AnswerType answerType = AnswerType.TURN;
        final int row = 0;
        final int column = 0;

        final Answer answer = new Answer(answerType, row, column);

        assertAll(
                () -> assertEquals(answerType, answer.answerType()),
                () -> assertEquals(row, answer.row()),
                () -> assertEquals(column, answer.column())
        );
    }


    @Test
    public void testGettersPass() {
        final AnswerType answerType = AnswerType.PASS;
        final int row = 9;
        final int column = 9;

        final Answer answer = new Answer(answerType, row, column);

        assertAll(
                () -> assertEquals(answerType, answer.answerType()),
                () -> assertEquals(row, answer.row()),
                () -> assertEquals(column, answer.column())
        );
    }
}
