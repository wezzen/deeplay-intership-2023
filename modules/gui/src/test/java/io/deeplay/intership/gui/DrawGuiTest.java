package io.deeplay.intership.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DrawGuiTest {

    @Test
    public void drawGuiTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
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