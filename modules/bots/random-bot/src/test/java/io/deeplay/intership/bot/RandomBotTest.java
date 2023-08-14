package io.deeplay.intership.bot;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomBotTest {
    @Test
    public void RandomMoveTest(){
        RandomBot testBot = new RandomBot();
        Board board = new Board();
        Assertions.assertEquals(Color.EMPTY, testBot.chooseGameAction(board).getColor());
        Stone[][] field = board.getField();
        for (int i = 0; i < board.DEFAULT_BOARD_SIZE; i++) {
            for (int j = 0; j < board.DEFAULT_BOARD_SIZE; j+= 2) {
                field[i][j].setColor(Color.WHITE);
            }
        }
        Assertions.assertEquals(Color.EMPTY, testBot.chooseGameAction(board).getColor());
    }
}
