package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StartGamePanelTest {
    @Test
    public void startGamePanelJoinTest(){
        DrawGui drawGui = new DrawGui();
        StartGamePanel startGamePanel = new StartGamePanel(drawGui);
        startGamePanel.jButtonJoin.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getCommandType(), 1),
                () -> assertFalse(startGamePanel.jDialog.isVisible()),
                () -> assertTrue(drawGui.joinGamePanel.jDialog.isVisible())
        );
    }

    @Test
    public void startGamePanelCreateTest(){
        DrawGui drawGui = new DrawGui();
        StartGamePanel startGamePanel = new StartGamePanel(drawGui);
        startGamePanel.jButtonCreate.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getCommandType(), 2),
                () -> assertFalse(startGamePanel.jDialog.isVisible()),
                () -> assertTrue(drawGui.createGamePanel.jDialog.isVisible())
        );
    }
}
