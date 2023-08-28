package io.deeplay.intership.gui;

import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CreateGamePanelTest {
    /*@BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }*/

    @Test
    public void createGameTest(){
        DrawGui drawGui = new DrawGui();
        CreateGamePanel createGamePanel = new CreateGamePanel(drawGui);
        //createGamePanel.buttonBlack.doClick();
        //createGamePanel.buttonBot.doClick();
        //createGamePanel.buttonSubmit.doClick();
        createGamePanel.drawGui.scannerGui.setWithBot(true);
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getColor(), Color.EMPTY),
                () -> assertEquals(drawGui.scannerGui.isWithBot(), true)
        );
    }
}
