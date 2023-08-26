package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InitialPanel implements Panel {
    public final JDialog jDialog;
    public final GridLayout layout;
    //private final FlowLayout layout;
    public final DrawGui drawGui;
    public final JButton jButtonRegister;
    public final JButton jButtonLogin;
    public final JButton jButtonExit;
    public final JPanel jPanel;

    public InitialPanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, "Initial");
        layout = new GridLayout(3, 1);
        //layout = new FlowLayout();
        jButtonRegister = new JButton("Register");
        jButtonRegister.addActionListener(this);
        jButtonLogin = new JButton("Log in");
        jButtonLogin.addActionListener(this);
        jButtonExit = new JButton("Exit");
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
    }

    @Override
    public void setPanel() {
        //jPanel.setLayout(layout);
        jPanel.add(jButtonRegister);
        jPanel.add(jButtonLogin);
        jPanel.add(jButtonExit);
        jPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
        //jButtonRegister.setMargin(new Insets(10, 30, 50, 10));
        //jButtonLogin.setMargin(new Insets(10, 30, 50, 10));
        //jButtonExit.setMargin(new Insets(10, 30, 30, 10));
        jButtonRegister.setLocation(30, 50);
        jButtonLogin.setLocation(130, 50);
        jButtonExit.setLocation(230, 50);
        jButtonRegister.setPreferredSize(new Dimension(120, 40));
        jButtonLogin.setPreferredSize(new Dimension(120, 40));
        jButtonExit.setPreferredSize(new Dimension(120, 40));
        //jButtonLogin.setVerticalAlignment(30);
        //jButtonExit.setVerticalAlignment(30);
        jDialog.add(jPanel);
        jDialog.setSize(500, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Register")) {
            drawGui.scannerGui.setCommandType(1);
            drawGui.initialPanel.hidePanel();
            drawGui.entrancePanel.showPanel();
        }
        else if(line.equals("Log in")) {
            drawGui.scannerGui.setCommandType(2);
            drawGui.initialPanel.hidePanel();
            drawGui.entrancePanel.showPanel();
        }
        else {

        }
    }
}
