package io.deeplay.intership.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui implements ActionListener {
    private static String[] actionNames = new String[]{"Move!", "Pass!", "Give up!"};
    private static String[] serviceNames = new String[]{"Log in", "Register", "Exit"};
    private static int buttonWidth = 150;
    private static int buttonHeight = 50;
    private static DrawGui drawGui;

    public static JButton getActionButton(int numberOfName, int x, int y) {
        JButton jButton = new JButton(actionNames[numberOfName]);
        jButton.setLocation(x, y);
        jButton.setSize(buttonWidth, buttonHeight);
        //jButton.setMargin(new Insets(50, 0, 50, 0));
        return jButton;
    }

    public void f() {
        GameField gameField = new GameField();
        JFrame frame = new JFrame();

        frame.add(gameField);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        drawGui = new DrawGui();
        MainGui mainGui = new MainGui();

        JPanel controlPanel = new JPanel();
        drawGui.frame.add(controlPanel);
        drawGui.frame.add(new Gui());
        GridLayout layout = new GridLayout(3, 1, 0, 20);
        controlPanel.setLocation(700, 100);
        controlPanel.setSize(150, 210);
        controlPanel.setLayout(layout);
        //controlPanel.setBorder(BorderFactory.createEmptyBorder(150, 100, 150, 100));
        for(int i = 0; i < actionNames.length; i++){
            if(i == 2) {
                JButton jButton = getActionButton(i, 1000, 100 + i * 50);
                jButton.addActionListener(mainGui);
                controlPanel.add(jButton);
            }
            else{
                controlPanel.add(getActionButton(i, 1000, 100 + i * 50));
            }
        }
        drawGui.frame.setSize(1000, 650);
        drawGui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawGui.frame.setVisible(true);
        //103,60,44 rect
        //188,140,102 line 242, 160, 97
        //236,238,233 white
        //3,19,16 black
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String line = event.getActionCommand();
        if(line.equals(actionNames[2])){
            drawGui.frame.setVisible(false);
            drawGui.startGamePanel.showPanel();
        }
    }
}
