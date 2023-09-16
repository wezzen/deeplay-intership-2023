package io.deeplay.intership.ui.gui.panel;

import io.deeplay.intership.decision.maker.gui.Command;
import io.deeplay.intership.ui.gui.DisplayGui;
import io.deeplay.intership.ui.gui.stuff.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartGamePanel extends Panel {
    public final JDialog jDialog;
    public final GridLayout layout;
    public final JButton jButtonCreate;
    public final JButton jButtonJoin;
    public final JButton jButtonLogout;
    public final JButton jButtonExit;
    public final JPanel jPanel;

    public StartGamePanel(DisplayGui displayGui, String name) {
        super(displayGui, name);
        jPanel = new JPanel();
        jDialog = new JDialog(displayGui.frame, Settings.START);
        layout = new GridLayout(4, 1, 50, 10);
        jButtonCreate = new JButton(Settings.CREATE);
        jButtonCreate.addActionListener(this);
        jButtonJoin = new JButton(Settings.JOIN);
        jButtonJoin.addActionListener(this);
        jButtonLogout = new JButton(Settings.LOGOUT);
        jButtonLogout.addActionListener(this);
        jButtonExit = new JButton(Settings.EXIT);
        jButtonExit.addActionListener(this);

        setPanel();
    }

    @Override
    public void showPanel() {
        jDialog.setVisible(true);
    }

    @Override
    public void hidePanel() {
        jDialog.setVisible(false);
        jButtonCreate.setSelected(false);
        jButtonJoin.setSelected(false);
        jButtonLogout.setSelected(false);
        jButtonExit.setSelected(false);
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
            displayGui.scannerGui.setCommandType(Command.REGISTRATION_OR_JOIN);
            changeSwitch(Settings.START_PANEL, false);
            changeSwitch(Settings.JOIN_PANEL, true);
        }
        else if(line.equals(Settings.CREATE)) {
            displayGui.scannerGui.setCommandType(Command.LOGIN_OR_CREATE);
            changeSwitch(Settings.START_PANEL, false);
            changeSwitch(Settings.CREATE_PANEL, true);
        }
        else {

        }
    }
}