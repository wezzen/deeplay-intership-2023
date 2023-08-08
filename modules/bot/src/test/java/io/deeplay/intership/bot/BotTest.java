package io.deeplay.intership.bot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BotTest {
    @Test
    public void RandomMoveTest(){
        Bot testBot = new Bot();
        Assertions.assertDoesNotThrow(() -> testBot.randomMove());
    }
}
