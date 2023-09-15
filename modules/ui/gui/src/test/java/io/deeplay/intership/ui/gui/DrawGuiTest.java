package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.gui.DrawGui;
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
                () -> assertNotNull(drawGui.gameFieldPanel),
                () -> assertDoesNotThrow(() -> drawGui.showGameResult("")),
                () -> assertDoesNotThrow(() -> drawGui.showMoveRules()),
                () -> assertDoesNotThrow(() -> drawGui.showGameActions()),
                () -> assertDoesNotThrow(() -> drawGui.showJoin()),
                () -> assertDoesNotThrow(() -> drawGui.showLogin()),
                () -> assertDoesNotThrow(() -> drawGui.showMessage("", "")),
                () -> assertDoesNotThrow(() -> drawGui.showColorSelection()),
                () -> assertDoesNotThrow(() -> drawGui.showAuthorizationActions()),
                () -> assertDoesNotThrow(() -> drawGui.showRoomActions())
        );
    }
}