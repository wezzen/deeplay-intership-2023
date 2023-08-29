package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InitialPanelTest {

    @Test
    public void initialPanelRegisterTest(){
        DrawGui drawGui = new DrawGui();
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
        DrawGui drawGui = new DrawGui();
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
