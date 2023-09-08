package io.deeplay.intership.gui;

import io.deeplay.intership.decision.maker.gui.Action;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameFieldPanelTest {

    @Test
    public void gameFieldPanelMoveTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        GameFieldPanel gameFieldPanel = new GameFieldPanel(drawGui);
        gameFieldPanel.setStone(70, 190);
        Color[][] field = gameFieldPanel.getField();
        gameFieldPanel.buttonMove.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getActionType(), Action.MOVE),
                () -> assertEquals(field[0][0], Color.BLACK)
        );
    }

    @Test
    public void gameFieldPanelPassTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        GameFieldPanel gameFieldPanel = new GameFieldPanel(drawGui);
        gameFieldPanel.buttonPass.doClick();
        assertEquals(drawGui.scannerGui.getActionType(), Action.SKIP);
    }

    @Test
    public void gameFieldPanelSurrenderTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        GameFieldPanel gameFieldPanel = new GameFieldPanel(drawGui);
        gameFieldPanel.buttonSurrender.doClick();
        assertEquals(drawGui.scannerGui.getActionType(), Action.SURRENDER);
    }
}