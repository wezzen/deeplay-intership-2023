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
    public void hidePanel() {
        buttonBot.setSelected(false);
        buttonHuman.setSelected(false);
        buttonBlack.setSelected(false);
        buttonWhite.setSelected(false);
        buttonSubmit.setSelected(false);
        jDialog.setVisible(false);
    }

    @Override
    public void setPanel() {
        //jPanel.setLayout(layout);
        jPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 60, 100));

        enemyLabel.setLocation(80, 50);
        buttonBot.setLocation(150, 50);
        buttonHuman.setLocation(220, 50);
        colorLabel.setLocation(80, 100);
        buttonBlack.setLocation(150, 100);
        buttonWhite.setLocation(220, 100);
        buttonSubmit.setLocation(150, 200);

        enemyLabel.setPreferredSize(new Dimension(100, 35));
        buttonBot.setPreferredSize(new Dimension(80, 35));
        buttonHuman.setPreferredSize(new Dimension(80, 35));
        colorLabel.setPreferredSize(new Dimension(100, 35));
        buttonBlack.setPreferredSize(new Dimension(80, 35));
        buttonWhite.setPreferredSize(new Dimension(80, 35));
        buttonSubmit.setPreferredSize(new Dimension(80, 35));

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
        if(line.equals("Black")) {
            buttonWhite.setSelected(false);
        }
        if(line.equals("White")) {
            buttonBlack.setSelected(false);
        }
        if(line.equals("Bot")) {
            buttonHuman.setSelected(false);
        }
        if(line.equals("Human")) {
            buttonBot.setSelected(false);
        }

        if(line.equals("Submit")) {
            if (buttonBlack.isSelected()) {
                drawGui.scannerGui.setColor(2);
            } else if (buttonWhite.isSelected()) {
                drawGui.scannerGui.setColor(1);
            } else {
                drawGui.scannerGui.setColor(3);
            }

            if (buttonBot.isSelected() || buttonHuman.isSelected()) {
                if(buttonBot.isSelected()){
                    drawGui.scannerGui.setCommandType(1);
                }
                else {
                    drawGui.scannerGui.setCommandType(2);
                }
                drawGui.createGamePanel.hidePanel();
                drawGui.gameFieldPanel.showPanel();
            }
            else {
                drawGui.showMessage("Ошибка", "Выберите с кем играть!");
            }
        }
    }
}
