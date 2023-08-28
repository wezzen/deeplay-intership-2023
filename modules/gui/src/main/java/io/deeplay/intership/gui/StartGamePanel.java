package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartGamePanel implements Panel {
    public final JDialog jDialog;
    public final GridLayout layout;
    public final DrawGui drawGui;
    public final JButton jButtonCreate;
    public final JButton jButtonJoin;
    public final JButton jButtonLogout;
    public final JButton jButtonExit;
    public final JPanel jPanel;

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

    @Override
    public void showPanel() {
        jDialog.setVisible(true);
    }

    @Override
    public void hidePanel(){
        jDialog.setVisible(false);
    }

    @Override
    public void setPanel(){
        jPanel.setLayout(layout);
        jPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
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
        if(line.equals("Join game")) {
            drawGui.scannerGui.setCommandType(1);
            drawGui.startGamePanel.hidePanel();
            drawGui.joinGamePanel.showPanel();
        }
        else if(line.equals("Create game")) {
            drawGui.scannerGui.setCommandType(2);
            drawGui.startGamePanel.hidePanel();
            drawGui.createGamePanel.showPanel();
        }
        else {

        }
    }
}
