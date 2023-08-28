package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameFieldPanelTest {

    @Test
    public void gameFieldPanelTest(){
        DrawGui drawGui = mock(DrawGui.class);
        assertThrows(NullPointerException.class, () -> new GameFieldPanel(drawGui));
    }

    /*@Test
    public void gameFieldPanelMoveTest(){
        DrawGui drawGui = new DrawGui();
        Color[][] field = drawGui.gameFieldPanel.getField();
        drawGui.gameFieldPanel.setStone(100, 130);
        //drawGui.gameFieldPanel.buttonMove.doClick();
        drawGui.scannerGui.setCommandType(1);
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getCommandType(), 1),
                () -> assertEquals(field[0][0], Color.BLACK)
        );
    }

    @Test
    public void gameFieldPanelPassTest(){
        DrawGui drawGui = new DrawGui();
        //drawGui.gameFieldPanel.buttonPass.doClick();
        drawGui.scannerGui.setCommandType(2);
        assertEquals(drawGui.scannerGui.getCommandType(), 2);
    }

    @Test
    public void gameFieldPanelSurrenderTest(){
        DrawGui drawGui = new DrawGui();
        drawGui.scannerGui.setCommandType(3);
        //drawGui.gameFieldPanel.buttonSurrender.doClick();
        assertEquals(drawGui.scannerGui.getCommandType(), 3);
    }*/
}
