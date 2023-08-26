package io.deeplay.intership.bot;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RandomBotTest {
    @Test
    public void RandomMoveTest() {
        final String token = UUID.randomUUID().toString();
        final Color color = Color.BLACK;
        final RandomBot testBot = new RandomBot(token, color);
        final Board board = new Board();

        //assertAll(
        //        () -> assertDoesNotThrow(() -> testBot.getGameAction())
        //);

        Stone[][] field = board.getField();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j].setColor(Color.WHITE);
            }
        }
        //assertEquals(Color.EMPTY.name(), testBot.getGameAction(field).color());
    }
}
