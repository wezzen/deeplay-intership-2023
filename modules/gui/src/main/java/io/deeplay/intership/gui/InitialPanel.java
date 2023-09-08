package io.deeplay.intership.gui;

import io.deeplay.intership.decision.maker.gui.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InitialPanel implements Panel {
    public final JDialog jDialog;
    public final GridLayout layout;
    public final DrawGui drawGui;
    public final JButton jButtonRegister;
    public final JButton jButtonLogin;
    public final JButton jButtonExit;
    public final JPanel jPanel;
    public boolean isVisible;

    public InitialPanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, Settings.INITIAL);
        layout = new GridLayout(3, 1);
        jButtonRegister = new JButton(Settings.REGISTER);
        jButtonRegister.addActionListener(this);
        jButtonLogin = new JButton(Settings.LOGIN);
        jButtonLogin.addActionListener(this);
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
        jButtonRegister.setSelected(false);
        jButtonLogin.setSelected(false);
        jButtonExit.setSelected(false);
        jDialog.setVisible(false);
    }

    @Override
    public void setPanel() {
        jPanel.add(jButtonRegister);
        jPanel.add(jButtonLogin);
        jPanel.add(jButtonExit);
        jPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
        jButtonRegister.setLocation(30, 50);
        jButtonLogin.setLocation(130, 50);
        jButtonExit.setLocation(230, 50);
        jButtonRegister.setPreferredSize(new Dimension(120, 40));
        jButtonLogin.setPreferredSize(new Dimension(120, 40));
        jButtonExit.setPreferredSize(new Dimension(120, 40));
        jDialog.add(jPanel);
        jDialog.setSize(Settings.PANEL_WIDTH, Settings.PANEL_HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals(Settings.REGISTER)) {
            drawGui.scannerGui.setCommandType(Command.REGISTRATION_OR_JOIN);
            drawGui.initialPanel.hidePanel();
            drawGui.entrancePanel.showPanel();
        }
        else if(line.equals(Settings.LOGIN)) {
            drawGui.scannerGui.setCommandType(Command.LOGIN_OR_CREATE);
            drawGui.initialPanel.hidePanel();
            drawGui.entrancePanel.showPanel();
        }
        else {

        }
    }
}