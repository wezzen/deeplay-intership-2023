package io.deeplay.intership.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InitialPanelTest {
    /*@BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }*/

    @Test
    public void initialPanelRegisterTest(){
        DrawGui drawGui = new DrawGui();
        InitialPanel initialPanel = new InitialPanel(drawGui);
        //initialPanel.jButtonRegister.doClick();
        initialPanel.register();
        assertEquals(initialPanel.drawGui.scannerGui.getCommandType(), 1);
    }

    @Test
    public void initialPanelLoginTest(){
        DrawGui drawGui = new DrawGui();
        InitialPanel initialPanel = new InitialPanel(drawGui);
        //initialPanel.jButtonLogin.doClick();
        initialPanel.login();
        assertEquals(initialPanel.drawGui.scannerGui.getCommandType(), 2);
    }
}
