package io.deeplay.intership.gui;

import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DrawGuiTest {

    @Test
    public void drawGuiTest(){
        DrawGui drawGui = mock(DrawGui.class);
        //drawGui.start();
        assertAll(
                () -> assertNull(drawGui.scannerGui),
                () -> assertNull(drawGui.initialPanel),
                () -> assertNull(drawGui.startGamePanel),
                () -> assertNull(drawGui.frame),
                () -> assertNull(drawGui.entrancePanel),
                () -> assertNull(drawGui.createGamePanel),
                () -> assertNull(drawGui.joinGamePanel),
                () -> assertNull(drawGui.gameFieldPanel)
        );
    }
}
