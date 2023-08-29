package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import io.deeplay.intership.model.Color;
import static org.junit.jupiter.api.Assertions.*;

public class CreateGamePanelTest {

    @Test
    public void createGameTest(){
        DrawGui drawGui = new DrawGui();
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
