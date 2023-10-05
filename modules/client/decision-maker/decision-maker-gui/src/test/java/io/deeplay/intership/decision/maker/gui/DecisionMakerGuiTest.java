package io.deeplay.intership.decision.maker.gui;

import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DecisionMakerGuiTest {
    @Test
    public void decisionMakerTest() {
        ScannerGui scannerGui = new ScannerGui(Command.REGISTRATION_OR_JOIN, Action.MOVE, "Aboba", "322", 1, 1, Color.WHITE, 555, 9, true);
        DecisionMakerGui decisionMakerGui = new DecisionMakerGui(scannerGui);
        assertAll(
                () -> assertNotNull(decisionMakerGui.getLoginPassword()),
                () -> assertNotNull(decisionMakerGui.getColor()),
                () -> assertNotNull(decisionMakerGui.getGameConfig()),
                () -> assertNotNull(decisionMakerGui.getGameAction()),
                () -> assertDoesNotThrow(decisionMakerGui::startGame),
                () -> assertDoesNotThrow(decisionMakerGui::endGame),
                () -> assertEquals(scannerGui.getCommandType(), Command.REGISTRATION_OR_JOIN),
                () -> assertEquals(scannerGui.getLogin(), "Aboba"),
                () -> assertEquals(scannerGui.getPassword(), "322"),
                () -> assertEquals(scannerGui.getRowNumber(), 1),
                () -> assertEquals(scannerGui.getColumnNumber(), 1),
                () -> assertEquals(scannerGui.getColor(), Color.WHITE),
                () -> assertEquals(scannerGui.getGameId(), 555)
        );
    }

    @Test
    public void decisionMaker_LoginTest() {
        ScannerGui scannerGui = new ScannerGui(Command.LOGIN_OR_CREATE, Action.SKIP, "Aboba", "322", 1, 1, Color.WHITE, 555, 9, true);
        DecisionMakerGui decisionMakerGui = new DecisionMakerGui(scannerGui);
        assertAll(
                () -> assertNotNull(decisionMakerGui.getLoginPassword()),
                () -> assertNotNull(decisionMakerGui.getColor()),
                () -> assertNotNull(decisionMakerGui.getGameConfig()),
                () -> assertNotNull(decisionMakerGui.getGameAction()),
                () -> assertDoesNotThrow(decisionMakerGui::startGame),
                () -> assertDoesNotThrow(decisionMakerGui::endGame),
                () -> assertEquals(scannerGui.getCommandType(), Command.LOGIN_OR_CREATE),
                () -> assertEquals(scannerGui.getLogin(), "Aboba"),
                () -> assertEquals(scannerGui.getPassword(), "322"),
                () -> assertEquals(scannerGui.getRowNumber(), 1),
                () -> assertEquals(scannerGui.getColumnNumber(), 1),
                () -> assertEquals(scannerGui.getColor(), Color.WHITE),
                () -> assertEquals(scannerGui.getGameId(), 555)
        );
    }
}