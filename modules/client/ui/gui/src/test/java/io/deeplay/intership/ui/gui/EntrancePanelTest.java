package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.ui.gui.panel.EntrancePanel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntrancePanelTest {

    @Test
    public void entrancePanelTest(){
        ScannerGui scannerGui = new ScannerGui();
        DisplayGui displayGui = new DisplayGui(scannerGui);
        EntrancePanel entrancePanel = new EntrancePanel(displayGui, "entrance_panel");
        entrancePanel.jTextLogin.setText("aboba");
        entrancePanel.jTextPassword.setText("pupa&lupa228");
        entrancePanel.jButtonSubmit.doClick();
        assertAll(
                () -> assertEquals(displayGui.scannerGui.getLogin(), "aboba"),
                () -> assertEquals(displayGui.scannerGui.getPassword(), "pupa&lupa228"),
                () -> assertFalse(entrancePanel.jDialog.isVisible())
        );
    }
}