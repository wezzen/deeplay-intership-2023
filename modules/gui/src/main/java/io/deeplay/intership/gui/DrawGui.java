package io.deeplay.intership.gui;

import javax.swing.*;

public class DrawGui {
    public final JFrame frame;
    public final InitialPanel initialPanel;
    public final EntrancePanel entrancePanel;
    public final StartGamePanel startGamePanel;

    public DrawGui(){
        frame = new JFrame("GO");
        initialPanel = new InitialPanel(this);
        entrancePanel = new EntrancePanel(this);
        startGamePanel = new StartGamePanel(this);
    }
}
