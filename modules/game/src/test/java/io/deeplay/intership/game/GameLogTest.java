package io.deeplay.intership.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GameLogTest {
    private static final GameLog gameLog = new GameLog();

    @BeforeAll
    public static void initLogger() {
        gameLog.init();
    }

    @Test
    public void testLogger() {
        Assertions.assertAll(
                () -> gameLog.startSession(),
                () -> gameLog.startGame(1),
                () -> gameLog.introducePlayers("Simon", true, "Terminator", false),
                () -> gameLog.move(new Stone(Color.WHITE, 4, 5)),
                () -> gameLog.wrongMove(Color.BLACK),
                () -> gameLog.deleteStones(15, Color.WHITE),
                () -> gameLog.skipMove(Color.WHITE),
                () -> gameLog.endGame(-34),
                () -> gameLog.endSession()
        );
    }
}
