package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class JoinGamePanelTest {
    /*@BeforeEach
    public void setUpHeadlessMode() {
        System.setProperty("java.awt.headless", "false");
    }*/

    @Test
    public void joinGamePanelTest(){
        DrawGui drawGui = mock(DrawGui.class);
        assertNotNull(new JoinGamePanel(drawGui));
        //joinGamePanel.buttonWhite.doClick();
        /*joinGamePanel.drawGui.scannerGui.setGameId(56789);
        //joinGamePanel.buttonSubmit.doClick();
        assertAll(
                () -> assertEquals(drawGui.scannerGui.getColor(), Color.EMPTY),
                () -> assertEquals(drawGui.scannerGui.getGameId(), 56789)
        );*/
    }
}
