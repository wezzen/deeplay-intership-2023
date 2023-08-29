package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import io.deeplay.intership.model.Color;

public class JoinGamePanelTest {
    /*@BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }*/

    @Test
    public void joinGamePanelTest(){
        DrawGui drawGui = new DrawGui();
        JoinGamePanel joinGamePanel = new JoinGamePanel(drawGui);
        //assertNotNull(new JoinGamePanel(drawGui));
        joinGamePanel.buttonWhite.doClick();
        joinGamePanel.gameId.setText("56789");
        joinGamePanel.buttonSubmit.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getColor(), Color.WHITE),
                () -> assertEquals(drawGui.scannerGui.getGameId(), 56789)
        );
    }
}
