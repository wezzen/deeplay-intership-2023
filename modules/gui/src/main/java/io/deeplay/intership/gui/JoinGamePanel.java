package io.deeplay.intership.gui;

import io.deeplay.intership.model.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JoinGamePanel implements Panel {
    public final JDialog jDialog;
    public final GridLayout layout;
    public final DrawGui drawGui;
    public final JLabel gameIdLabel;
    public final JTextField gameId;
    public final JRadioButton buttonBlack;
    public final JRadioButton buttonWhite;
    public final JButton buttonSubmit;
    public final JPanel jPanel;

    public JoinGamePanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, "Entrance");
        layout = new GridLayout(3, 1, 50, 10);
        gameIdLabel = new JLabel("Enter game id");
        gameId = new JTextField( 64);
        buttonBlack = new JRadioButton("Black");
        buttonBlack.addActionListener(this);
        buttonWhite = new JRadioButton("White");
        buttonWhite.addActionListener(this);
        buttonSubmit = new JButton("Submit");
        buttonSubmit.addActionListener(this);
        setPanel();
    }

    @Override
    public void showPanel() {
        jDialog.setVisible(true);
    }

    @Override
    public void hidePanel(){
        jDialog.setVisible(false);
    }

    @Override
    public void setPanel(){
        jPanel.setLayout(layout);
        jPanel.setBorder(BorderFactory.createEmptyBorder(30, 70, 30, 70));
        jPanel.add(gameIdLabel);
        jPanel.add(gameId);
        jPanel.add(buttonBlack);
        jPanel.add(buttonWhite);
        jPanel.add(buttonSubmit);
        jDialog.add(jPanel);
        jDialog.setSize(500, 300);
    }

    public void submit(){
        drawGui.scannerGui.setGameId(Integer.valueOf(gameId.getText()));
        drawGui.joinGamePanel.hidePanel();
        drawGui.gameFieldPanel.showPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Submit")) {
            if (buttonWhite.isSelected()) {
                drawGui.scannerGui.setColor(Color.WHITE);
            } else if (buttonBlack.isSelected()) {
                drawGui.scannerGui.setColor(Color.BLACK);
            } else {
                drawGui.scannerGui.setColor(Color.EMPTY);
            }
            submit();
        }
    }
}
