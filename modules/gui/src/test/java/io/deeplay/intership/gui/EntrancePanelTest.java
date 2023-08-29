package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class EntrancePanelTest {

    @Test
    public void entrancePanelTest(){
        DrawGui drawGui = new DrawGui();
        EntrancePanel entrancePanel = new EntrancePanel(drawGui);
        //assertNotNull(new EntrancePanel(drawGui));
        //assertNotNull(new EntrancePanel(drawGui));
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
