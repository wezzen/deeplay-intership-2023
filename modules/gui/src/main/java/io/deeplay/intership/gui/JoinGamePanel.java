package io.deeplay.intership.gui;

import io.deeplay.intership.model.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinGamePanel implements ActionListener {
    private final JDialog jDialog;
    private final GridLayout layout;
    private final DrawGui drawGui;
    private final JLabel gameIdLabel;
    private final JTextField gameId;
    private final JButton buttonBlack;
    private final JButton buttonWhite;
    private final JButton buttonSubmit;
    private final JPanel jPanel;

    public JoinGamePanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, "Entrance");
        layout = new GridLayout(3, 1, 50, 10);
        gameIdLabel = new JLabel("Enter game id");
        gameId = new JTextField( 64);
        buttonBlack = new JButton("Black");
        buttonBlack.addActionListener(this);
        buttonWhite = new JButton("White");
        buttonWhite.addActionListener(this);
        buttonSubmit = new JButton("Submit");
        buttonSubmit.addActionListener(this);
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
        jPanel.add(gameIdLabel);
        jPanel.add(gameId);
        jPanel.add(buttonBlack);
        jPanel.add(buttonWhite);
        jPanel.add(buttonSubmit);
        jDialog.add(jPanel);
        jDialog.setSize(500, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Submit")) {
            drawGui.scannerGui.setGameId(Integer.valueOf(line));
            drawGui.joinGamePanel.hidePanel();
        }
        else if(line.equals("White")) {
            drawGui.scannerGui.setColor(Color.WHITE);
        }
        else if(line.equals("Black")) {
            drawGui.scannerGui.setColor(Color.BLACK);
        }
    }
}
