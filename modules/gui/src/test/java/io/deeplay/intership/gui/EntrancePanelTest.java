package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class EntrancePanelTest {

    @Test
    public void entrancePanelTest(){
        DrawGui drawGui = mock(DrawGui.class);
        assertNotNull(new EntrancePanel(drawGui));
        //assertNotNull(new EntrancePanel(drawGui));
        /*entrancePanel.drawGui.scannerGui.setLogin("aboba");
        entrancePanel.drawGui.scannerGui.setPassword("pupa&lupa228");
        entrancePanel.jButtonSubmit.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getLogin(), "aboba"),
                () -> assertEquals(drawGui.scannerGui.getPassword(), "pupa&lupa228")
        );*/
    }
}
