package io.deeplay.intership.gui;

import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;
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
                () -> assertEquals(drawGui.scannerGui.getColor(), Color.BLACK),
                () -> assertEquals(drawGui.scannerGui.isWithBot(), true)
        );
    }
}
