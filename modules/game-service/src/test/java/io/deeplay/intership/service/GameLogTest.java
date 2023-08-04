package io.deeplay.intership.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class GameLogTest {
    private static final GameLog gameLog = new GameLog();
    @BeforeAll
    public static void initLogger(){
        gameLog.init();
    }
    @Test
    public void testLogger(){
        Assertions.assertAll(
                () -> gameLog.startSession(),
                () -> gameLog.startGame(1),
                () -> gameLog.introducePlayers("Simon", true, "Terminator", false),
                () -> gameLog.move(2, 5, true),
                () -> gameLog.wrongMove(true),
                () -> gameLog.deleteStones(15, true),
                () -> gameLog.endGame(-34),
                () -> gameLog.endSession()
        );
    }
}
