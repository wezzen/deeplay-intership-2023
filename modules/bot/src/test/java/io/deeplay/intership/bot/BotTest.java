package io.deeplay.intership.bot;
import io.deeplay.intership.game.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BotTest {
    @Test
    public void RandomMoveTest(){
        Bot testBot = new Bot();
        Board board = new Board();
        Assertions.assertDoesNotThrow(() -> testBot.randomMove(board));
    }
}
