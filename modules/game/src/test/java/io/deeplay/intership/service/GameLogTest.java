package io.deeplay.intership.service;

import io.deeplay.intership.game.Color;
import io.deeplay.intership.game.GameLog;
import io.deeplay.intership.game.Stone;
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
                () -> gameLog.move(new Stone(Color.EMPTY, 4, 5), Color.WHITE),
                () -> gameLog.wrongMove(Color.BLACK),
                () -> gameLog.deleteStones(15, Color.WHITE),
                () -> gameLog.endGame(-34),
                () -> gameLog.endSession()
        );
    }
}
