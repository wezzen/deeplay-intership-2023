package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import io.deeplay.intership.model.Color;

public class CreateGamePanel implements ActionListener {
    private final JDialog jDialog;
    private final GridLayout layout;
    private final DrawGui drawGui;
    private final JLabel enemyLabel;
    private final JLabel colorLabel;
    private final JButton buttonSubmit;
    private final JButton buttonBot;
    private final JButton buttonHuman;
    private final JButton buttonBlack;
    private final JButton buttonWhite;
    private final JPanel jPanel;

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
        buttonBot = new JButton("Bot");
        buttonBot.addActionListener(this);
        buttonHuman = new JButton("Human");
        buttonHuman.addActionListener(this);
        buttonBlack = new JButton("Black");
        buttonBlack.addActionListener(this);
        buttonWhite = new JButton("White");
        buttonWhite.addActionListener(this);
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
        if(line.equals("Bot")) {
            drawGui.scannerGui.setWithBot(true);
            System.out.println(drawGui.scannerGui.isWithBot());
        }
        else if(line.equals("Human")){
            drawGui.scannerGui.setWithBot(false);
        }
        else if(line.equals("Black")){
            drawGui.scannerGui.setColor(Color.BLACK);
            System.out.println(drawGui.scannerGui.getColor());
        }
        else if(line.equals("White")){
            drawGui.scannerGui.setColor(Color.WHITE);
        }
        else {
            drawGui.createGamePanel.hidePanel();
            drawGui.gameFieldPanel.showPanel();
        }
    }
}
