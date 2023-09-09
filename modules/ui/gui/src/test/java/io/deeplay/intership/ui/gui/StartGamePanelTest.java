package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.Command;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.ui.gui.DrawGui;
import io.deeplay.intership.ui.gui.StartGamePanel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StartGamePanelTest {
    @Test
    public void startGamePanelJoinTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        StartGamePanel startGamePanel = new StartGamePanel(drawGui);
        startGamePanel.jButtonJoin.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getCommandType(), Command.REGISTRATION_OR_JOIN),
                () -> assertFalse(startGamePanel.jDialog.isVisible()),
                () -> assertTrue(drawGui.joinGamePanel.jDialog.isVisible())
        );
    }

    @Test
    public void startGamePanelCreateTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        StartGamePanel startGamePanel = new StartGamePanel(drawGui);
        startGamePanel.jButtonCreate.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getCommandType(), Command.LOGIN_OR_CREATE),
                () -> assertFalse(startGamePanel.jDialog.isVisible()),
                () -> assertTrue(drawGui.createGamePanel.jDialog.isVisible())
        );
    }
}