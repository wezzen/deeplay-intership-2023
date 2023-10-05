package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.Command;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.ui.gui.panel.CreateGamePanel;
import io.deeplay.intership.ui.gui.stuff.Settings;
import org.junit.jupiter.api.Test;
import io.deeplay.intership.model.Color;
import static org.junit.jupiter.api.Assertions.*;

public class CreateGamePanelTest {

    @Test
    public void createGameTest(){
        ScannerGui scannerGui = new ScannerGui();
        DisplayGui displayGui = new DisplayGui(scannerGui);
        CreateGamePanel createGamePanel = new CreateGamePanel(displayGui, Settings.CREATE_PANEL);
        createGamePanel.buttonBlack.doClick();
        createGamePanel.buttonBot.doClick();
        createGamePanel.buttonSubmit.doClick();
        createGamePanel.displayGui.scannerGui.setCommandType(Command.LOGIN_OR_CREATE);
        assertAll(
                () -> assertEquals(displayGui.scannerGui.getCommandType(), Command.LOGIN_OR_CREATE),
                () -> assertEquals(displayGui.scannerGui.getColor(), Color.BLACK)
        );
    }
}