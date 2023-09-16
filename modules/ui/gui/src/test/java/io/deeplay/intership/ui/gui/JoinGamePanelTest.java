package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.ui.gui.panel.JoinGamePanel;
import io.deeplay.intership.ui.gui.stuff.Settings;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import io.deeplay.intership.model.Color;

public class JoinGamePanelTest {

    @Test
    public void joinGamePanelTest(){
        ScannerGui scannerGui = new ScannerGui();
        DisplayGui displayGui = new DisplayGui(scannerGui);
        JoinGamePanel joinGamePanel = new JoinGamePanel(displayGui, Settings.JOIN_PANEL);
        joinGamePanel.buttonWhite.doClick();
        joinGamePanel.gameId.setText("56789");
        joinGamePanel.buttonSubmit.doClick();
        assertAll(
                () -> assertEquals(displayGui.scannerGui.getColor(), Color.WHITE),
                () -> assertEquals(displayGui.scannerGui.getGameId(), 56789)
        );
    }
}