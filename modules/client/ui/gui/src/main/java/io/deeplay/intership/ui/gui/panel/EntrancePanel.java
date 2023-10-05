package io.deeplay.intership.ui.gui.panel;

import io.deeplay.intership.ui.gui.DisplayGui;
import io.deeplay.intership.ui.gui.stuff.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntrancePanel extends Panel {
    public final JDialog jDialog;
    public final JTextField jTextPassword;
    public final JTextField jTextLogin;
    public final JButton jButtonSubmit;
    public final JPanel jPanel;

    public EntrancePanel(DisplayGui displayGui, String name) {
        super(displayGui, name);
        jPanel = new JPanel();
        jDialog = new JDialog(displayGui.frame, Settings.ENTRANCE);
        jTextLogin = new JTextField( Settings.INPUT_LENGTH);
        jTextPassword = new JTextField( Settings.INPUT_LENGTH);
        jButtonSubmit = new JButton(Settings.SUBMIT);
        jButtonSubmit.addActionListener(this);
        setPanel();
    }

    @Override
    public void setPanel() {
        jPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 10, 100));
        jTextLogin.setPreferredSize(new Dimension(120, 35));
        jTextPassword.setPreferredSize(new Dimension(120, 35));
        jButtonSubmit.setPreferredSize(new Dimension(80, 35));
        jPanel.add(jTextLogin);
        jPanel.add(jTextPassword);
        jPanel.add(jButtonSubmit);
        jDialog.add(jPanel);
        jDialog.setSize(Settings.PANEL_WIDTH, Settings.PANEL_HEIGHT);
    }

    @Override
    public void showPanel() {
        jDialog.setVisible(true);
    }

    @Override
    public void hidePanel() {
        jDialog.setVisible(false);
        jTextLogin.setText("");
        jTextPassword.setText("");
        jButtonSubmit.setSelected(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals(Settings.SUBMIT)) {
            displayGui.scannerGui.setLogin(jTextLogin.getText().toString());
            displayGui.scannerGui.setPassword(jTextPassword.getText().toString());

            changeSwitch(Settings.ENTRANCE_PANEL, false);

            JOptionPane pane = new JOptionPane("Вы вошли на сервер!",
                    JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = pane.createDialog(null, "Вход");
            dialog.setModal(false);
            dialog.setVisible(true);

            new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.setVisible(false);
                }
            }).start();

            changeSwitch(Settings.START_PANEL, true);
        }
    }
}