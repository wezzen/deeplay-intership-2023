package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.Command;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.ui.gui.panel.StartGamePanel;
import io.deeplay.intership.ui.gui.stuff.Settings;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StartGamePanelTest {
    @Test
    public void startGamePanelJoinTest(){
        ScannerGui scannerGui = new ScannerGui();
        DisplayGui displayGui = new DisplayGui(scannerGui);
        StartGamePanel startGamePanel = new StartGamePanel(displayGui, Settings.START_PANEL);
        startGamePanel.jButtonJoin.doClick();
        assertAll(
                () -> assertEquals(displayGui.scannerGui.getCommandType(), Command.REGISTRATION_OR_JOIN),
                () -> assertFalse(startGamePanel.jDialog.isVisible())
        );
    }

    @Test
    public void startGamePanelCreateTest(){
        ScannerGui scannerGui = new ScannerGui();
        DisplayGui displayGui = new DisplayGui(scannerGui);
        StartGamePanel startGamePanel = new StartGamePanel(displayGui, Settings.START_PANEL);
        startGamePanel.jButtonCreate.doClick();
        assertAll(
                () -> assertEquals(displayGui.scannerGui.getCommandType(), Command.LOGIN_OR_CREATE),
                () -> assertFalse(startGamePanel.jDialog.isVisible())
        );
    }
}