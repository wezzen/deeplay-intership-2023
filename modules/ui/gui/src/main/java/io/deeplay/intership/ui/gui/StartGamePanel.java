package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.Command;

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
    public boolean isVisible;

    public StartGamePanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, Settings.START);
        layout = new GridLayout(4, 1, 50, 10);
        jButtonCreate = new JButton(Settings.CREATE);
        jButtonCreate.addActionListener(this);
        jButtonJoin = new JButton(Settings.JOIN);
        jButtonJoin.addActionListener(this);
        jButtonLogout = new JButton(Settings.LOGOUT);
        jButtonLogout.addActionListener(this);
        jButtonExit = new JButton(Settings.EXIT);
        jButtonExit.addActionListener(this);
        isVisible = false;
        setPanel();
    }

    @Override
    public void showPanel() {
        jDialog.setVisible(true);
    }

    @Override
    public void hidePanel() {
        jButtonCreate.setSelected(false);
        jButtonJoin.setSelected(false);
        jButtonLogout.setSelected(false);
        jButtonExit.setSelected(false);
        jDialog.setVisible(false);
    }

    @Override
    public void setPanel() {
        jPanel.setLayout(layout);
        jPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
        jPanel.add(jButtonCreate);
        jPanel.add(jButtonJoin);
        jPanel.add(jButtonLogout);
        jPanel.add(jButtonExit);
        jDialog.add(jPanel);
        jDialog.setSize(Settings.PANEL_WIDTH, Settings.PANEL_HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals(Settings.JOIN)) {
            drawGui.scannerGui.setCommandType(Command.REGISTRATION_OR_JOIN);
            drawGui.startGamePanel.hidePanel();
            drawGui.joinGamePanel.showPanel();
        }
        else if(line.equals(Settings.CREATE)) {
            drawGui.scannerGui.setCommandType(Command.LOGIN_OR_CREATE);
            drawGui.startGamePanel.hidePanel();
            drawGui.createGamePanel.showPanel();
        }
        else {

        }
    }
}