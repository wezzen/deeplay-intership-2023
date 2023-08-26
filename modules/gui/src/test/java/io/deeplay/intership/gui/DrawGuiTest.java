package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DrawGuiTest {
    @Test
    public void drawGuiTest(){
        DrawGui drawGui = new DrawGui();
        drawGui.start();
        assertAll(
                () -> assertNotNull(drawGui.scannerGui),
                () -> assertNotNull(drawGui.initialPanel),
                () -> assertNotNull(drawGui.startGamePanel),
                () -> assertNotNull(drawGui.frame),
                () -> assertNotNull(drawGui.entrancePanel),
                () -> assertNotNull(drawGui.createGamePanel),
                () -> assertNotNull(drawGui.joinGamePanel),
                () -> assertNotNull(drawGui.gameFieldPanel)
        );
    }
}
