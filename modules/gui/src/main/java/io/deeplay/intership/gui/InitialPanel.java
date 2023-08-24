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
        jButtonRegister.addActionListener(this);
        jButtonLogin = new JButton("Log in");
        jButtonLogin.addActionListener(this);
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
        jPanel.add(jButtonRegister);
        jPanel.add(jButtonLogin);
        jPanel.add(jButtonExit);
        jDialog.add(jPanel);
        jDialog.setSize(500, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Register")) {
            drawGui.initialPanel.hidePanel();
            drawGui.entrancePanel.showPanel();
        }
        else if(line.equals("Log in")) {
            drawGui.initialPanel.hidePanel();
            drawGui.entrancePanel.showPanel();
        }
        else {

        }
    }
}
