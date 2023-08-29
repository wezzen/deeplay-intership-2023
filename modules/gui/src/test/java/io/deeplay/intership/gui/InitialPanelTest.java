package io.deeplay.intership.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InitialPanelTest {
    /*@BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }*/

    /*@Test
    public void initialPanelTest(){
        DrawGui drawGui = mock(DrawGui.class);
        assertNotNull(new InitialPanel(drawGui));
    }*/

    @Test
    public void initialPanelRegisterTest(){
        DrawGui drawGui = new DrawGui();
        InitialPanel initialPanel = new InitialPanel(drawGui);
        initialPanel.jButtonRegister.doClick();
        assertFalse(initialPanel.jDialog.isVisible());
        assertTrue(drawGui.entrancePanel.jDialog.isVisible());
        assertEquals(initialPanel.drawGui.scannerGui.getCommandType(), 1);
    }

    @Test
    public void initialPanelLoginTest(){
        DrawGui drawGui = new DrawGui();
        InitialPanel initialPanel = new InitialPanel(drawGui);
        initialPanel.jButtonLogin.doClick();
        initialPanel.drawGui.scannerGui.setCommandType(2);
        assertFalse(initialPanel.jDialog.isVisible());
        assertTrue(drawGui.entrancePanel.jDialog.isVisible());
        assertEquals(drawGui.scannerGui.getCommandType(), 2);
    }
}
