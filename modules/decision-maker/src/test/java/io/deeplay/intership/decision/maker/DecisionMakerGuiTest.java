package io.deeplay.intership.decision.maker;

import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DecisionMakerGuiTest {
    @Test
    public void decisionMakerTest(){
        ScannerGui scannerGui = new ScannerGui(1, "Aboba", "322", 1, 1, Color.WHITE, 555);
        DecisionMakerGui decisionMakerGui = new DecisionMakerGui(scannerGui);
        assertAll(
                () -> assertNotNull(decisionMakerGui.getLoginPassword()),
                () -> assertNotNull(decisionMakerGui.getColor()),
                () -> assertNotNull(decisionMakerGui.getGameId()),
                () -> assertNotNull(decisionMakerGui.getGameAction()),
                () -> assertEquals(scannerGui.getCommandType(), 1),
                () -> assertEquals(scannerGui.getLogin(), "Aboba"),
                () -> assertEquals(scannerGui.getPassword(), "322"),
                () -> assertEquals(scannerGui.getRowNumber(), 1),
                () -> assertEquals(scannerGui.getColumnNumber(), 1),
                () -> assertEquals(scannerGui.getColor(), Color.WHITE),
                () -> assertEquals(scannerGui.getGameId(), 555)
        );
    }
}
