package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.Action;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameFieldPanelTest {

    private static GameFieldPanel gameFieldPanel;

    @BeforeAll
    public static void createGameField(){
        gameFieldPanel = new GameFieldPanel(new DrawGui(new ScannerGui()));
    }

    @Test
    public void gameFieldPanelMoveTest(){
        gameFieldPanel.setStone(70, 190);
        Color[][] field = gameFieldPanel.getField();
        gameFieldPanel.buttonMove.doClick();
        assertAll(
                () -> assertEquals(gameFieldPanel.drawGui.scannerGui.getActionType(), Action.MOVE),
                () -> assertEquals(field[0][0], Color.BLACK)
        );
    }

    @Test
    public void gameFieldPanelPassTest(){
        gameFieldPanel.buttonPass.doClick();
        assertEquals(gameFieldPanel.drawGui.scannerGui.getActionType(), Action.SKIP);
    }

    @Test
    public void gameFieldPanelSurrenderTest(){
        gameFieldPanel.buttonSurrender.doClick();
        assertEquals(gameFieldPanel.drawGui.scannerGui.getActionType(), Action.SURRENDER);
    }

    @Test
    public void testGetColors(){
        Stone[][] stones = new Stone[1][1];
        stones[0][0] = new Stone(Color.BLACK, 0, 0);
        assertDoesNotThrow(() -> gameFieldPanel.getColors(stones));
    }

    @Test
    public void testDrawField(){
        Stone[][] stones = new Stone[9][9];
        stones[0][0] = new Stone(Color.BLACK, 0, 0);
        assertDoesNotThrow(() -> gameFieldPanel.drawField(stones));
    }

    @Test
    public void testSetStone(){
        gameFieldPanel.drawGui.scannerGui.setColor(Color.BLACK);
        gameFieldPanel.setStone(70, 190);

        assertEquals(gameFieldPanel.getField()[0][0], Color.BLACK);
    }

    @Test
    public void testPaint(){
        gameFieldPanel.revalidate();
        gameFieldPanel.repaint();
    }
}