package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.gui.stuff.GameField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameFieldTest {

    private static GameField gameField;

    @BeforeAll
    public static void createGameField(){
        ScannerGui scannerGui = new ScannerGui();
        scannerGui.setColor(Color.BLACK);
        gameField = new GameField(new DisplayGui(scannerGui));
    }

    @Test
    public void testGetColors(){
        Stone[][] stones = new Stone[1][1];
        stones[0][0] = new Stone(Color.BLACK, 0, 0);
        assertDoesNotThrow(() -> gameField.getColors(stones));
    }

    @Test
    public void testDrawField(){
        Stone[][] stones = new Stone[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                stones[i][j] = new Stone(Color.BLACK, i, j);
            }
        }
        assertDoesNotThrow(() -> gameField.setField(stones));
    }

    @Test
    public void testSetStone(){
        gameField.setStone(130, 250);

        assertEquals(gameField.getField()[1][1], Color.BLACK);
    }

    @Test
    public void testPaint(){
        assertDoesNotThrow(() -> gameField.revalidate());
        assertDoesNotThrow(() -> gameField.repaint());
    }

    @Test
    public void testFillField(){
        assertDoesNotThrow(() -> gameField.fillField());
    }
}