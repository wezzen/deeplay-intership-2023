package io.deeplay.intership.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;
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
                () -> assertEquals(initialPanel.drawGui.scannerGui.getCommandType(), 1)
        );
    }

    @Test
    public void initialPanelLoginTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        InitialPanel initialPanel = new InitialPanel(drawGui);
        initialPanel.jButtonLogin.doClick();
        initialPanel.drawGui.scannerGui.setCommandType(2);
        assertAll(
                () -> assertFalse(initialPanel.jDialog.isVisible()),
                () -> assertTrue(drawGui.entrancePanel.jDialog.isVisible()),
                () -> assertEquals(drawGui.scannerGui.getCommandType(), 2)
        );
    }
}
