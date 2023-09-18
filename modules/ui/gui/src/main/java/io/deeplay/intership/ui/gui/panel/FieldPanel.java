package io.deeplay.intership.ui.gui.panel;

import io.deeplay.intership.decision.maker.gui.Action;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.gui.DisplayGui;
import io.deeplay.intership.ui.gui.stuff.GameField;
import io.deeplay.intership.ui.gui.stuff.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FieldPanel extends Panel {
    public final GameField gameField;
    public final JButton buttonMove;
    public final JButton buttonPass;
    public final JButton buttonSurrender;
    public final DisplayGui displayGui;

    public FieldPanel(DisplayGui displayGui, String name){
        super(displayGui, name);
        this.buttonMove = new JButton(Settings.MOVE);
        this.buttonPass = new JButton(Settings.PASS);
        this.buttonSurrender = new JButton(Settings.GIVE_UP);
        this.displayGui = displayGui;
        this.gameField = new GameField(displayGui);
        setPanel();
    }

    @Override
    public void drawField(Stone[][] gameField){
        this.gameField.setField(gameField);
        displayGui.frame.revalidate();
        displayGui.frame.repaint();
        showPanel();
    }

    @Override
    public void setPanel() {
        JPanel controlPanel = new JPanel();

        gameField.gameId.setFont(new Font("serif", Font.BOLD, 20));
        gameField.gameId.setText("Номер игры: " + displayGui.scannerGui.getGameId());
        gameField.gameId.setSize(600, 50);
        gameField.gameId.setForeground(java.awt.Color.BLACK);
        gameField.gameId.setBorder(new EmptyBorder(10,50,10,0));

        GridLayout gameIdLayout = new GridLayout(1,1);
        JPanel gameIdPanel = new JPanel();
        gameIdPanel.setSize(600, 50);
        gameIdPanel.setLocation(0, 0);
        gameIdPanel.setLayout(gameIdLayout);
        gameIdPanel.add(gameField.gameId, BorderLayout.CENTER);

        displayGui.frame.add(gameIdPanel);
        displayGui.frame.add(controlPanel);
        displayGui.frame.add(this.gameField);

        displayGui.frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameField.setStone(e.getX(), e.getY());
            }
        });

        GridLayout layout = new GridLayout(1, 3);
        controlPanel.setLocation(0, 50);
        controlPanel.setSize(600, 50);
        controlPanel.setLayout(layout);

        buttonMove.setLocation(1000, 150);
        buttonPass.setLocation(1000, 200);
        buttonSurrender.setLocation(1000, 250);
        buttonMove.setFont(new Font("serif", Font.BOLD, 20));
        buttonPass.setFont(new Font("serif", Font.BOLD, 20));
        buttonSurrender.setFont(new Font("serif", Font.BOLD, 20));
        buttonMove.addActionListener(this);
        buttonPass.addActionListener(this);
        buttonSurrender.addActionListener(this);
        controlPanel.add(buttonMove);
        controlPanel.add(buttonPass);
        controlPanel.add(buttonSurrender);
        displayGui.frame.setSize(Settings.GAME_PANEL_WIDTH, Settings.GAME_PANEL_HEIGHT);
        displayGui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void showPanel() {
        gameField.gameId.setText("Номер игры: " + displayGui.scannerGui.getGameId());
        displayGui.frame.setVisible(true);
    }

    @Override
    public void hidePanel() {
        displayGui.frame.setVisible(false);
        gameField.fillField();
        buttonMove.setSelected(false);
        buttonPass.setSelected(false);
        buttonSurrender.setSelected(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(displayGui.scannerGui.isTurn()) {
            if (line.equals(Settings.MOVE)) {
                displayGui.scannerGui.setActionType(Action.MOVE);
                displayGui.scannerGui.setTurn(false);
                gameField.setStoneColor(displayGui.scannerGui.getRowNumber()-1,
                        displayGui.scannerGui.getColumnNumber()-1);
                displayGui.frame.revalidate();
                displayGui.frame.repaint();

                // Realize move interaction with client
            } else if (line.equals(Settings.PASS)) {
                displayGui.scannerGui.setActionType(Action.SKIP);
                displayGui.scannerGui.setTurn(false);

                // Realize pass interaction with client
            } else if (line.equals(Settings.GIVE_UP)) {
                displayGui.scannerGui.setActionType(Action.SURRENDER);
                changeSwitch(Settings.FIELD_PANEL, false);
                changeSwitch(Settings.START_PANEL, true);

                // Realize surrender interaction with client
            }
        }
    }
}
