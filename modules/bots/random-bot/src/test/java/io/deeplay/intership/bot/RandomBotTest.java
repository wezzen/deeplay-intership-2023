package io.deeplay.intership.bot;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomBotTest {
    @Test
    public void RandomMoveTest() {
        final String token = UUID.randomUUID().toString();
        final Color color = Color.BLACK;
        final RandomBot testBot = new RandomBot(token, color);
        final Board board = new Board();

        assertEquals(Color.EMPTY, testBot.chooseGameAction(board.getField()));

        Stone[][] field = board.getField();
        for (int i = 0; i < board.DEFAULT_BOARD_SIZE; i++) {
            for (int j = 0; j < board.DEFAULT_BOARD_SIZE; j += 2) {
                field[i][j].setColor(Color.WHITE);
            }
        }
//        Assertions.assertEquals(Color.EMPTY, testBot.chooseGameAction(board).getColor());
    }
}
