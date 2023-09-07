package io.deeplay.intership.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntrancePanelTest {

    @Test
    public void entrancePanelTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        EntrancePanel entrancePanel = new EntrancePanel(drawGui);
        entrancePanel.jTextLogin.setText("aboba");
        entrancePanel.jTextPassword.setText("pupa&lupa228");
        entrancePanel.jButtonSubmit.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getLogin(), "aboba"),
                () -> assertEquals(drawGui.scannerGui.getPassword(), "pupa&lupa228"),
                () -> assertFalse(entrancePanel.jDialog.isVisible()),
                () -> assertTrue(drawGui.startGamePanel.jDialog.isVisible())
        );
    }
}