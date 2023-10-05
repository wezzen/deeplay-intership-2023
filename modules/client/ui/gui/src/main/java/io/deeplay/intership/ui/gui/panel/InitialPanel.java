package io.deeplay.intership.ui.gui.panel;

import io.deeplay.intership.decision.maker.gui.Command;
import io.deeplay.intership.ui.gui.DisplayGui;
import io.deeplay.intership.ui.gui.stuff.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InitialPanel extends Panel {
    public final JDialog jDialog;
    public final GridLayout layout;
    public final JButton jButtonRegister;
    public final JButton jButtonLogin;
    public final JButton jButtonExit;
    public final JPanel jPanel;
    public boolean isVisible;

    public InitialPanel(DisplayGui displayGui, String name) {
        super(displayGui, name);
        jPanel = new JPanel();
        jDialog = new JDialog(displayGui.frame, Settings.INITIAL);
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
        jDialog.setVisible(false);
        jButtonRegister.setSelected(false);
        jButtonLogin.setSelected(false);
        jButtonExit.setSelected(false);
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
            displayGui.scannerGui.setCommandType(Command.REGISTRATION_OR_JOIN);
            changeSwitch(Settings.INITIAL_PANEL, false);
            changeSwitch(Settings.ENTRANCE_PANEL, true);
        }
        else if(line.equals(Settings.LOGIN)) {
            displayGui.scannerGui.setCommandType(Command.LOGIN_OR_CREATE);
            changeSwitch(Settings.INITIAL_PANEL, false);
            changeSwitch(Settings.ENTRANCE_PANEL, true);
        }
        else {

        }
    }
}