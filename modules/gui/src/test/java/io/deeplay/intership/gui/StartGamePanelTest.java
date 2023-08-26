package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StartGamePanelTest {
    @Test
    public void startGamePanelJoinTest(){
        DrawGui drawGui = new DrawGui();
        StartGamePanel startGamePanel = new StartGamePanel(drawGui);
        startGamePanel.jButtonJoin.doClick();
        assertEquals(startGamePanel.drawGui.scannerGui.getCommandType(), 1);
    }

    @Test
    public void startGamePanelCreateTest(){
        DrawGui drawGui = new DrawGui();
        StartGamePanel startGamePanel = new StartGamePanel(drawGui);
        startGamePanel.jButtonCreate.doClick();
        assertEquals(startGamePanel.drawGui.scannerGui.getCommandType(), 2);
    }
}
