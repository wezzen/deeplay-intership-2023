package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EntrancePanel implements Panel {
    public final JDialog jDialog;
    public final GridLayout layout;
    public final DrawGui drawGui;
    public final JTextField jTextPassword;
    public final JTextField jTextLogin;
    public final JButton jButtonSubmit;
    public final JPanel jPanel;

    public EntrancePanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, "Entrance");
        layout = new GridLayout(3, 1, 50, 30);
        jTextLogin = new JTextField( 64);
        jTextPassword = new JTextField( 64);
        jButtonSubmit = new JButton("Submit");
        jButtonSubmit.addActionListener(this);
        setPanel();
    }

    @Override
    public void setPanel(){
        jPanel.setLayout(layout);
        jPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        jTextLogin.setPreferredSize(new Dimension(120, 30));
        jTextPassword.setPreferredSize(new Dimension(120, 30));
        jButtonSubmit.setPreferredSize(new Dimension(80, 20));
        jPanel.add(jTextLogin);
        jPanel.add(jTextPassword);
        jPanel.add(jButtonSubmit);
        jDialog.add(jPanel);
        jDialog.setSize(500, 300);
    }

    @Override
    public void showPanel(){
        jDialog.setVisible(true);
    }

    @Override
    public void hidePanel(){
        jDialog.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Submit")) {
            drawGui.scannerGui.setLogin(jTextLogin.getText().toString());
            drawGui.scannerGui.setPassword(jTextPassword.getText().toString());
            drawGui.entrancePanel.hidePanel();
            drawGui.startGamePanel.showPanel();
        }
    }
}
