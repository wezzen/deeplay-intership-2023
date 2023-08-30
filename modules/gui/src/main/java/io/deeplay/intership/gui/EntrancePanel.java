package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EntrancePanel implements Panel {
    public final JDialog jDialog;
    public final DrawGui drawGui;
    public final JTextField jTextPassword;
    public final JTextField jTextLogin;
    public final JButton jButtonSubmit;
    public final JPanel jPanel;

    public EntrancePanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, "Entrance");
        jTextLogin = new JTextField( 32);
        jTextPassword = new JTextField( 32);
        jButtonSubmit = new JButton("Submit");
        jButtonSubmit.addActionListener(this);
        setPanel();
    }

    @Override
    public void setPanel(){
        jPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 10, 100));
        jTextLogin.setPreferredSize(new Dimension(120, 35));
        jTextPassword.setPreferredSize(new Dimension(120, 35));
        jButtonSubmit.setPreferredSize(new Dimension(80, 35));
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
        jTextLogin.setText("");
        jTextPassword.setText("");
        jButtonSubmit.setSelected(false);
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
