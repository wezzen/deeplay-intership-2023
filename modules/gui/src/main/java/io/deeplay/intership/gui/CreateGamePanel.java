package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import io.deeplay.intership.model.Color;

public class CreateGamePanel implements Panel {
    public final JDialog jDialog;
    public final GridLayout layout;
    public final DrawGui drawGui;
    public final JLabel enemyLabel;
    public final JLabel colorLabel;
    public final JButton buttonSubmit;
    public final JRadioButton buttonBot;
    public final JRadioButton buttonHuman;
    public final JRadioButton buttonBlack;
    public final JRadioButton buttonWhite;
    public final JPanel jPanel;

    public CreateGamePanel(DrawGui drawGui) {
        this.drawGui = drawGui;
        jPanel = new JPanel();
        jDialog = new JDialog(drawGui.frame, "Entrance");
        layout = new GridLayout(3, 3, 50, 10);

        enemyLabel = new JLabel("Choose enemy");
        colorLabel = new JLabel("Choose color");
        buttonSubmit = new JButton("Submit");
        buttonSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonSubmit.addActionListener(this);
        buttonBot = new JRadioButton("Bot");
        buttonBot.addActionListener(this);
        buttonHuman = new JRadioButton("Human");
        buttonHuman.addActionListener(this);
        buttonBlack = new JRadioButton("Black");
        buttonBlack.addActionListener(this);
        buttonWhite = new JRadioButton("White");
        buttonWhite.addActionListener(this);
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
        jPanel.add(enemyLabel);
        jPanel.add(buttonBot);
        jPanel.add(buttonHuman);
        jPanel.add(colorLabel);
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
            if (buttonBot.isSelected()) {
                drawGui.scannerGui.setWithBot(true);
            } else if (buttonHuman.isSelected()) {
                drawGui.scannerGui.setWithBot(false);
            }
            if (buttonBlack.isSelected()) {
                drawGui.scannerGui.setColor(Color.BLACK);
            } else if (buttonWhite.isSelected()) {
                drawGui.scannerGui.setColor(Color.WHITE);
            }
            drawGui.createGamePanel.hidePanel();
            drawGui.gameFieldPanel.showPanel();
        }
    }
}
