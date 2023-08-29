package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import io.deeplay.intership.model.Color;

public class JoinGamePanelTest {

    @Test
    public void joinGamePanelTest(){
        DrawGui drawGui = new DrawGui();
        JoinGamePanel joinGamePanel = new JoinGamePanel(drawGui);
        joinGamePanel.buttonWhite.doClick();
        joinGamePanel.gameId.setText("56789");
        joinGamePanel.buttonSubmit.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getColor(), Color.WHITE),
                () -> assertEquals(drawGui.scannerGui.getGameId(), 56789)
        );
    }
}
