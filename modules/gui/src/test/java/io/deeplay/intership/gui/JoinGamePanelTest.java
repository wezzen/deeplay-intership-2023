package io.deeplay.intership.gui;

import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JoinGamePanelTest {
    /*@BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }*/

    @Test
    public void joinGamePanelTest(){
        DrawGui drawGui = new DrawGui();
        JoinGamePanel joinGamePanel = new JoinGamePanel(drawGui);
        assertNotNull(joinGamePanel);
        //joinGamePanel.buttonWhite.doClick();
        /*joinGamePanel.drawGui.scannerGui.setGameId(56789);
        //joinGamePanel.buttonSubmit.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getColor(), Color.EMPTY),
                () -> assertEquals(drawGui.scannerGui.getGameId(), 56789)
        );*/
    }
}
