package io.deeplay.intership.gui;

import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class GameFieldPanelTest {
    @Test
    public void gameFieldPanelMoveTest(){
        DrawGui drawGui = new DrawGui();
        Color[][] field = drawGui.gameFieldPanel.getField();
        drawGui.gameFieldPanel.setStone(100, 130);
        drawGui.gameFieldPanel.buttonMove.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getCommandType(), 1),
                () -> assertEquals(field[0][0], Color.BLACK)
        );
    }

    @Test
    public void gameFieldPanelPassTest(){
        DrawGui drawGui = new DrawGui();
        drawGui.gameFieldPanel.buttonPass.doClick();
        assertEquals(drawGui.scannerGui.getCommandType(), 2);
    }

    @Test
    public void gameFieldPanelSurrenderTest(){
        DrawGui drawGui = new DrawGui();
        drawGui.gameFieldPanel.buttonSurrender.doClick();
        assertEquals(drawGui.scannerGui.getCommandType(), 3);
    }
}
