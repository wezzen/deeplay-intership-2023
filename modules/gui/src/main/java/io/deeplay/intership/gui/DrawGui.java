package io.deeplay.intership.gui;

import io.deeplay.intership.decision.maker.ScannerGui;

import javax.swing.*;

public class DrawGui {
    public final JFrame frame;
    public final InitialPanel initialPanel;
    public final EntrancePanel entrancePanel;
    public final StartGamePanel startGamePanel;
    public final CreateGamePanel createGamePanel;
    public final JoinGamePanel joinGamePanel;
    public final GameFieldPanel gameFieldPanel;
    public final ScannerGui scannerGui;

    public DrawGui(){
        frame = new JFrame("GO");
        scannerGui = new ScannerGui();
        gameFieldPanel = new GameFieldPanel(this);
        initialPanel = new InitialPanel(this);
        entrancePanel = new EntrancePanel(this);
        startGamePanel = new StartGamePanel(this);
        createGamePanel = new CreateGamePanel(this);
        joinGamePanel = new JoinGamePanel(this);
    }

    public void start(){
        initialPanel.showPanel();
    }
}
