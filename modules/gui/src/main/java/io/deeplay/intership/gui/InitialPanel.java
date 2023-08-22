package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialPanel implements ActionListener {
    private final JDialog jDialog;
    private final GridLayout layout;
    private final DrawGui drawGui;
    private final JButton jButtonRegister;
    private final JButton jButtonLogin;
    private final JButton jButtonExit;
    private final JPanel jPanel;

    public InitialPanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, "Initial");
        layout = new GridLayout(3, 1, 50, 10);
        jButtonRegister = new JButton("Register");
        jButtonLogin = new JButton("Log in");
        jButtonExit = new JButton("Exit");
    }

    public void showPanel() {
        jPanel.setLayout(layout);
        jPanel.add(jButtonRegister);
        jPanel.add(jButtonLogin);
        jPanel.add(jButtonExit);
        jDialog.add(jPanel);
        jDialog.setSize(500, 300);
        jDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Register")) {
            drawGui.entrancePanel.showPanel();
        }
        else if(line.equals("Log in")) {
            drawGui.entrancePanel.showPanel();
        }
        else {

        }
    }
}
