package io.deeplay.intership.ui.gui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.ui.gui.DisplayGui;
import io.deeplay.intership.ui.gui.stuff.Settings;

public class CreateGamePanel extends Panel {
    public final JDialog jDialog;
    public final GridLayout layout;
    public final JLabel enemyLabel;
    public final JLabel colorLabel;
    public final JButton buttonSubmit;
    public final JRadioButton buttonBot;
    public final JRadioButton buttonHuman;
    public final JRadioButton buttonBlack;
    public final JRadioButton buttonWhite;
    public final JPanel jPanel;


    public CreateGamePanel(DisplayGui displayGui, String name) {
        super(displayGui, name);
        jPanel = new JPanel();
        jDialog = new JDialog(displayGui.frame, Settings.ENTRANCE);
        layout = new GridLayout(3, 3, 50, 10);

        enemyLabel = new JLabel("Choose enemy");
        colorLabel = new JLabel("Choose color");
        buttonSubmit = new JButton(Settings.SUBMIT);
        buttonSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonSubmit.addActionListener(this);
        buttonBot = new JRadioButton(Settings.BOT);
        buttonBot.addActionListener(this);
        buttonHuman = new JRadioButton(Settings.HUMAN);
        buttonHuman.addActionListener(this);
        buttonBlack = new JRadioButton(Settings.BLACK);
        buttonBlack.addActionListener(this);
        buttonWhite = new JRadioButton(Settings.WHITE);
        buttonWhite.addActionListener(this);
        setPanel();
    }

    @Override
    public void showPanel() {
        jDialog.setVisible(true);
    }

    @Override
    public void hidePanel() {
        jDialog.setVisible(false);
        buttonBot.setSelected(false);
        buttonHuman.setSelected(false);
        buttonBlack.setSelected(false);
        buttonWhite.setSelected(false);
        buttonSubmit.setSelected(false);
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
        jDialog.setSize(Settings.PANEL_WIDTH, Settings.PANEL_HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals(Settings.BLACK)) {
            buttonWhite.setSelected(false);
            buttonBlack.setSelected(true);
        }
        if(line.equals(Settings.WHITE)) {
            buttonWhite.setSelected(true);
            buttonBlack.setSelected(false);
        }
        if(line.equals(Settings.BOT)) {
            buttonHuman.setSelected(false);
            buttonBot.setSelected(true);
        }
        if(line.equals(Settings.HUMAN)) {
            buttonHuman.setSelected(true);
            buttonBot.setSelected(false);
        }

        if(line.equals(Settings.SUBMIT)) {
            if (buttonWhite.isSelected()) {
                displayGui.scannerGui.setColor(Color.WHITE);
            } else if (buttonBlack.isSelected()) {
                displayGui.scannerGui.setColor(Color.BLACK);
            } else {
                displayGui.scannerGui.setColor(Color.EMPTY);
            }

            if (buttonBot.isSelected() || buttonHuman.isSelected()) {
                if(buttonBot.isSelected()){
                    displayGui.scannerGui.setWithBot(true);
                }
                else {
                    displayGui.scannerGui.setWithBot(false);
                }
                changeSwitch(Settings.CREATE_PANEL, false);
                changeSwitch(Settings.FIELD_PANEL, true);
            }
            else {
                displayGui.showMessage("Ошибка", "Выберите с кем играть!");
            }
        }
    }
}