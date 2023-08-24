package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntrancePanel implements ActionListener {
    private final JDialog jDialog;
    private final GridLayout layout;
    private final DrawGui drawGui;
    private final JTextField jTextPassword;
    private final JTextField jTextLogin;
    private final JButton jButtonSubmit;
    private final JPanel jPanel;

    public EntrancePanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, "Entrance");
        layout = new GridLayout(3, 1, 50, 10);
        jTextLogin = new JTextField( 64);
        jTextPassword = new JTextField( 64);
        jButtonSubmit = new JButton("Submit");
        jButtonSubmit.addActionListener(this);
        setPanel();
    }

    public void setPanel(){
        jPanel.setLayout(layout);
        jPanel.add(jTextLogin);
        jPanel.add(jTextPassword);
        jPanel.add(jButtonSubmit);
        jDialog.add(jPanel);
        jDialog.setSize(500, 300);
    }

    public void showPanel(){
        jDialog.setVisible(true);
    }

    public void hidePanel(){
        jDialog.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Submit")) {
            drawGui.scannerGui.setLogin(jTextLogin.getText().toString());
            drawGui.scannerGui.setPassword(jTextPassword.getText().toString());
            System.out.println(drawGui.scannerGui.getLogin());
            System.out.println(drawGui.scannerGui.getPassword());
            drawGui.entrancePanel.hidePanel();
            drawGui.startGamePanel.showPanel();
        }
    }
}
