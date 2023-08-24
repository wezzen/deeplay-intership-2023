package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGamePanel implements ActionListener {
    private final JDialog jDialog;
    private final GridLayout layout;
    private final DrawGui drawGui;
    private final JButton jButtonCreate;
    private final JButton jButtonJoin;
    private final JButton jButtonLogout;
    private final JButton jButtonExit;
    private final JPanel jPanel;

    public StartGamePanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, "Start");
        layout = new GridLayout(4, 1, 50, 10);
        jButtonCreate = new JButton("Create game");
        jButtonCreate.addActionListener(this);
        jButtonJoin = new JButton("Join game");
        jButtonJoin.addActionListener(this);
        jButtonLogout = new JButton("Log out");
        jButtonLogout.addActionListener(this);
        jButtonExit = new JButton("Exit");
        jButtonExit.addActionListener(this);
        setPanel();
    }

    public void showPanel() {
        jDialog.setVisible(true);
    }

    public void hidePanel(){
        jDialog.setVisible(false);
    }

    public void setPanel(){
        jPanel.setLayout(layout);
        jPanel.add(jButtonCreate);
        jPanel.add(jButtonJoin);
        jPanel.add(jButtonLogout);
        jPanel.add(jButtonExit);
        jDialog.add(jPanel);
        jDialog.setSize(500, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Create game")) {
            drawGui.startGamePanel.hidePanel();
            drawGui.createGamePanel.showPanel();
        }
        else if(line.equals("Join game")) {
            drawGui.startGamePanel.hidePanel();
            drawGui.joinGamePanel.showPanel();
        }
        else if(line.equals("Log out")) {
            drawGui.entrancePanel.showPanel();
        }
        else {

        }
    }
}
