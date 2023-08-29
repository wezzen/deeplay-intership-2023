package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class StartGamePanelTest {
    /*@BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }*/

    /*@Test
    public void startGamePanelTest(){
        DrawGui drawGui = mock(DrawGui.class);
        assertNotNull(new StartGamePanel(drawGui));
    }*/

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
