package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.Command;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.ui.gui.panel.InitialPanel;
import io.deeplay.intership.ui.gui.stuff.Settings;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InitialPanelTest {

    @Test
    public void initialPanelRegisterTest(){
        ScannerGui scannerGui = new ScannerGui();
        DisplayGui displayGui = new DisplayGui(scannerGui);
        InitialPanel initialPanel = new InitialPanel(displayGui, Settings.INITIAL_PANEL);
        initialPanel.jButtonRegister.doClick();
        assertAll(
                () -> assertFalse(initialPanel.jDialog.isVisible()),
                () -> assertEquals(initialPanel.displayGui.scannerGui.getCommandType(), Command.REGISTRATION_OR_JOIN)
        );
    }

    @Test
    public void initialPanelLoginTest(){
        ScannerGui scannerGui = new ScannerGui();
        DisplayGui displayGui = new DisplayGui(scannerGui);
        InitialPanel initialPanel = new InitialPanel(displayGui, Settings.INITIAL_PANEL);
        initialPanel.jButtonLogin.doClick();
        assertAll(
                () -> assertFalse(initialPanel.jDialog.isVisible()),
                () -> assertEquals(displayGui.scannerGui.getCommandType(), Command.LOGIN_OR_CREATE)
        );
    }
}