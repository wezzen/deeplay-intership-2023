package io.deeplay.intership.gui;

import io.deeplay.intership.decision.maker.gui.Command;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import org.junit.jupiter.api.Test;
import io.deeplay.intership.model.Color;
import static org.junit.jupiter.api.Assertions.*;

public class CreateGamePanelTest {

    @Test
    public void createGameTest(){
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        CreateGamePanel createGamePanel = new CreateGamePanel(drawGui);
        createGamePanel.buttonBlack.doClick();
        createGamePanel.buttonBot.doClick();
        createGamePanel.buttonSubmit.doClick();
        createGamePanel.drawGui.scannerGui.setCommandType(Command.LOGIN_OR_CREATE);
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getCommandType(), Command.LOGIN_OR_CREATE),
                () -> assertEquals(drawGui.scannerGui.getColor(), Color.BLACK)
        );
    }
}