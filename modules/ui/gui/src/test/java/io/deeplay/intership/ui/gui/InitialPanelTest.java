package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.Command;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.ui.gui.DrawGui;
import io.deeplay.intership.ui.gui.InitialPanel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InitialPanelTest {

    @Test
    public void initialPanelRegisterTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        InitialPanel initialPanel = new InitialPanel(drawGui);
        initialPanel.jButtonRegister.doClick();
        assertAll(
                () -> assertFalse(initialPanel.jDialog.isVisible()),
                () -> assertTrue(drawGui.entrancePanel.jDialog.isVisible()),
                () -> assertEquals(initialPanel.drawGui.scannerGui.getCommandType(), Command.REGISTRATION_OR_JOIN)
        );
    }

    @Test
    public void initialPanelLoginTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        InitialPanel initialPanel = new InitialPanel(drawGui);
        initialPanel.jButtonLogin.doClick();
        assertAll(
                () -> assertFalse(initialPanel.jDialog.isVisible()),
                () -> assertTrue(drawGui.entrancePanel.jDialog.isVisible()),
                () -> assertEquals(drawGui.scannerGui.getCommandType(), Command.LOGIN_OR_CREATE)
        );
    }
}