package io.deeplay.intership.decision.maker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DecisionMakerGuiTest {
    @Test
    public void decisionMakerTest(){
        ScannerGui scannerGui = new ScannerGui();
        scannerGui.setPassword("322");
        scannerGui.setLogin("Aboba");
        scannerGui.setCommandType(1);
       
    }
}
