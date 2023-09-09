package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.ScannerGui;

public class MainGui  {
    public static void main(String[] args) {
        ScannerGui scannerGui = new ScannerGui();
        DrawGui drawGui = new DrawGui(scannerGui);
        drawGui.start();
    }
}
