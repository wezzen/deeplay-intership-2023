package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntrancePanelTest {
    @Test
    public void entrancePanelTest(){
        DrawGui drawGui = new DrawGui();
        EntrancePanel entrancePanel = new EntrancePanel(drawGui);
        entrancePanel.jTextLogin.setText("aboba");
        entrancePanel.jTextPassword.setText("pupa&lupa228");
        entrancePanel.jButtonSubmit.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getLogin(), "aboba"),
                () -> assertEquals(drawGui.scannerGui.getPassword(), "pupa&lupa228")
        );
    }
}
