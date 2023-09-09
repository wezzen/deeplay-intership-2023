package io.deeplay.intership.ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntrancePanel implements Panel {
    public final JDialog jDialog;
    public final DrawGui drawGui;
    public final JTextField jTextPassword;
    public final JTextField jTextLogin;
    public final JButton jButtonSubmit;
    public final JPanel jPanel;
    public boolean isVisible;

    public EntrancePanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, Settings.ENTRANCE);
        jTextLogin = new JTextField( Settings.INPUT_LENGTH);
        jTextPassword = new JTextField( Settings.INPUT_LENGTH);
        jButtonSubmit = new JButton(Settings.SUBMIT);
        jButtonSubmit.addActionListener(this);
        isVisible = false;
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
        jTextLogin.setText("");
        jTextPassword.setText("");
        jButtonSubmit.setSelected(false);
        jDialog.setVisible(false);
        isVisible = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals(Settings.SUBMIT)) {
            drawGui.scannerGui.setLogin(jTextLogin.getText().toString());
            drawGui.scannerGui.setPassword(jTextPassword.getText().toString());
            drawGui.entrancePanel.hidePanel();

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

            drawGui.startGamePanel.showPanel();
        }
    }
}