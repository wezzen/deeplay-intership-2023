package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CreateGamePanelTest {

    @Test
    public void createGameTest(){
        DrawGui drawGui = mock(DrawGui.class);
        CreateGamePanel createGamePanel = new CreateGamePanel(drawGui);
        createGamePanel.buttonBlack.doClick();
        createGamePanel.buttonBot.doClick();
        createGamePanel.buttonSubmit.doClick();
        assertAll(
                () -> assertTrue(drawGui.scannerGui.isWithBot()),
                () -> assertEquals(drawGui.scannerGui.getColor(), Color.BLACK)
        );
    }
}
