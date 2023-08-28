package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StartGamePanelTest {
    /*@BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }*/

    @Test
    public void startGamePanelJoinTest(){
        DrawGui drawGui = new DrawGui();
        StartGamePanel startGamePanel = new StartGamePanel(drawGui);
        //startGamePanel.jButtonJoin.doClick();
        startGamePanel.join();
        assertEquals(startGamePanel.drawGui.scannerGui.getCommandType(), 1);
    }

    @Test
    public void startGamePanelCreateTest(){
        DrawGui drawGui = new DrawGui();
        StartGamePanel startGamePanel = new StartGamePanel(drawGui);
        //startGamePanel.jButtonCreate.doClick();
        startGamePanel.create();
        assertEquals(startGamePanel.drawGui.scannerGui.getCommandType(), 2);
    }
}
