package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InitialPanelTest {
    @Test
    public void initialPanelRegisterTest(){
        DrawGui drawGui = new DrawGui();
        InitialPanel initialPanel = new InitialPanel(drawGui);
        initialPanel.jButtonRegister.doClick();
        assertEquals(initialPanel.drawGui.scannerGui.getCommandType(), 1);
    }

    @Test
    public void initialPanelLoginTest(){
        DrawGui drawGui = new DrawGui();
        InitialPanel initialPanel = new InitialPanel(drawGui);
        initialPanel.jButtonLogin.doClick();
        assertEquals(initialPanel.drawGui.scannerGui.getCommandType(), 2);
    }
}
