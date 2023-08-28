package io.deeplay.intership.gui;

import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameFieldPanelTest {
    /*@BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }*/

    @Test
    public void gameFieldPanelMoveTest(){
        DrawGui drawGui = new DrawGui();
        Color[][] field = drawGui.gameFieldPanel.getField();
        drawGui.gameFieldPanel.setStone(100, 130);
        //drawGui.gameFieldPanel.buttonMove.doClick();
        drawGui.gameFieldPanel.move();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getCommandType(), 1),
                () -> assertEquals(field[0][0], Color.BLACK)
        );
    }

    @Test
    public void gameFieldPanelPassTest(){
        DrawGui drawGui = new DrawGui();
        //drawGui.gameFieldPanel.buttonPass.doClick();
        drawGui.gameFieldPanel.pass();
        assertEquals(drawGui.scannerGui.getCommandType(), 2);
    }

    @Test
    public void gameFieldPanelSurrenderTest(){
        DrawGui drawGui = new DrawGui();
        //drawGui.gameFieldPanel.buttonSurrender.doClick();
        drawGui.gameFieldPanel.giveup();
        assertEquals(drawGui.scannerGui.getCommandType(), 3);
    }
}
