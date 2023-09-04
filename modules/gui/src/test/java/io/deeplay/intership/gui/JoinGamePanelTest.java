package io.deeplay.intership.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import io.deeplay.intership.model.Color;

public class JoinGamePanelTest {

    @Test
    public void joinGamePanelTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        JoinGamePanel joinGamePanel = new JoinGamePanel(drawGui);
        joinGamePanel.buttonWhite.doClick();
        joinGamePanel.gameId.setText("56789");
        joinGamePanel.buttonSubmit.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getColor(), 1),
                () -> assertEquals(drawGui.scannerGui.getGameId(), 56789)
        );
    }
}
