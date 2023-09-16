package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.ui.gui.panel.Panel;
import io.deeplay.intership.ui.gui.stuff.Settings;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DrawGuiTest {

    @Test
    public void drawGuiTest(){
        ScannerGui scannerGui = new ScannerGui();
        DisplayGui displayGui = new DisplayGui(scannerGui);
        Map<String, Panel> panels = displayGui.switcher.getPanels();
        assertAll(
                () -> assertNotNull(displayGui.scannerGui),
                () -> assertNotNull(panels.get(Settings.INITIAL_PANEL)),
                () -> assertNotNull(panels.get(Settings.START_PANEL)),
                () -> assertNotNull(displayGui.frame),
                () -> assertNotNull(panels.get(Settings.ENTRANCE_PANEL)),
                () -> assertNotNull(panels.get(Settings.CREATE_PANEL)),
                () -> assertNotNull(panels.get(Settings.JOIN_PANEL)),
                () -> assertNotNull(panels.get(Settings.FIELD_PANEL)),
                () -> assertDoesNotThrow(() -> displayGui.showGameResult("")),
                () -> assertDoesNotThrow(() -> displayGui.showMoveRules()),
                () -> assertDoesNotThrow(() -> displayGui.showGameActions()),
                () -> assertDoesNotThrow(() -> displayGui.showJoin()),
                () -> assertDoesNotThrow(() -> displayGui.showLogin()),
                () -> assertDoesNotThrow(() -> displayGui.showMessage("", "")),
                () -> assertDoesNotThrow(() -> displayGui.showColorSelection()),
                () -> assertDoesNotThrow(() -> displayGui.showAuthorizationActions()),
                () -> assertDoesNotThrow(() -> displayGui.showRoomActions())
        );
    }
}