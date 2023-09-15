package io.deeplay.intership.ui.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import io.deeplay.intership.decision.maker.gui.Action;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

import static java.lang.Math.abs;
import java.util.Random;

public class GameFieldPanel extends JPanel implements ActionListener {
    public final DrawGui drawGui;
    public final ObjectGui square;
    private RgbColor lineColor;
    public final int N;
    public final JButton buttonMove;
    public final JButton buttonPass;
    public final JButton buttonSurrender;
    public final JLabel gameId;
    private Graphics2D graphics2D;
    private Color[][] field;
    public boolean isVisible;
    private Color color;

    public GameFieldPanel(DrawGui drawGui) {
        N = drawGui.scannerGui.getSize();
        field = new Color[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                field[i][j] = Color.EMPTY;
            }
        }
        buttonMove = new JButton(Settings.MOVE);
        buttonPass = new JButton(Settings.PASS);
        buttonSurrender = new JButton(Settings.GIVE_UP);
        gameId = new JLabel();
        this.drawGui = drawGui;
        this.square = new ObjectGui(0, 100, Settings.SIZE_OF_FIELD, new RgbColor(242,176,109), Settings.BOARD_FILE_NAME);
        this.lineColor = new RgbColor(Settings.LINE_RED, Settings.LINE_GREEN, Settings.LINE_BLUE);
        isVisible = false;
        color = drawGui.scannerGui.getColor();
        setPanel();
    }

    public BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        Image tmp = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage imageResized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = imageResized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return imageResized;
    }

    private void drawLine(Line line) {
        int[] rgb = lineColor.getRgbColor();
        graphics2D.setColor(java.awt.Color.getHSBColor(rgb[0], rgb[1], rgb[2]));
        graphics2D.drawLine(line.x1(), line.y1(), line.x2(), line.y2());
    }

    public void drawCustomSquare() {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(square.fileName()));
            BufferedImage image = bufferedImage.getSubimage(0, 0, Settings.SIZE_OF_FIELD, Settings.SIZE_OF_FIELD);
            graphics2D.drawImage(image, square.x(), square.y(), null);
        } catch (IOException e) {
            drawStandardSquare();
        }
    }

    public void drawStandardSquare(){
        int[] rgb = square.rgbColor().getRgbColor();
        this.lineColor = new RgbColor(0, 0, 0);
        graphics2D.setColor(new java.awt.Color(rgb[0], rgb[1], rgb[2]));
        graphics2D.fillRect(square.x(), square.y(), Settings.SIZE_OF_FIELD, Settings.SIZE_OF_FIELD);
    }

    public void drawCustomStone(ObjectGui figure) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(figure.fileName()));
            BufferedImage newBufferedImage = resize(bufferedImage, Settings.SIZE_OF_STONE, Settings.SIZE_OF_STONE);
            graphics2D.drawImage(newBufferedImage, figure.x() - Settings.SIZE_OF_STONE /2,
                    figure.y() - Settings.SIZE_OF_STONE /2, null);
        } catch (IOException e) {
            int[] rgb = figure.rgbColor().getRgbColor();
            graphics2D.setColor(new java.awt.Color(rgb[0], rgb[1], rgb[2]));
            graphics2D.fillOval(figure.x() - Settings.SIZE_OF_STONE / 2,
                    figure.y() - Settings.SIZE_OF_STONE / 2,
                    figure.size(),  figure.size());
        }
    }

    public void setStone(int x, int y) {
        for(int i = 1; i < N + 1; i++) {
            for(int j = 1; j < N + 1; j++) {
                if (abs(x - 10 - i * Settings.PADDING_X - square.x()) <= Settings.PADDING_X / 2 &&
                        abs((y-30) - j * Settings.PADDING_Y - square.y()) <= Settings.PADDING_Y / 2) {
                    if (field[i - 1][j - 1] == Color.EMPTY) {
                        field[i - 1][j - 1] = this.color;
                        return;
                    }
                }
            }
        }
    }

    public void showPanel() {
        color = drawGui.scannerGui.getColor();
        drawGui.frame.setVisible(true);
    }

    public void hidePanel() {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                field[i][j] = Color.EMPTY;
            }
        }
        buttonMove.setSelected(false);
        buttonPass.setSelected(false);
        buttonSurrender.setSelected(false);
        drawGui.frame.setVisible(false);
    }

    public String getGameId() {
        int max = 100000, min = 10000;
        return String.valueOf(new Random().nextInt((max - min) + 1) + min);
    }

    public void setPanel() {
        JPanel controlPanel = new JPanel();

        gameId.setFont(new Font("serif", Font.BOLD, 20));
        gameId.setText("Номер игры: " + String.valueOf(getGameId()));
        gameId.setSize(600, 50);
        gameId.setForeground(java.awt.Color.BLACK);
        gameId.setBorder(new EmptyBorder(10,210,10,0));

        GridLayout gameIdLayout = new GridLayout(1,1);
        JPanel gameIdPanel = new JPanel();
        gameIdPanel.setSize(600, 50);
        gameIdPanel.setLocation(0, 0);
        gameIdPanel.setLayout(gameIdLayout);
        gameIdPanel.add(gameId, BorderLayout.CENTER);

        drawGui.frame.add(gameIdPanel);
        drawGui.frame.add(controlPanel);
        drawGui.frame.add(this);

        GridLayout layout = new GridLayout(1, 3);
        controlPanel.setLocation(0, 50);
        controlPanel.setSize(600, 50);
        controlPanel.setLayout(layout);
        drawGui.frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setStone(e.getX(), e.getY());
            }
        });

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
        drawGui.frame.setSize(Settings.GAME_PANEL_WIDTH, Settings.GAME_PANEL_HEIGHT);
        drawGui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics2D = (Graphics2D) graphics;
        if(!drawGui.scannerGui.isBackStyle()) {
            drawStandardSquare();
        }
        else {
            drawCustomSquare();
        }
        for(int i = 1; i < N + 1; i++) {
            drawLine(new Line(Settings.PADDING_X + square.x(), i * Settings.PADDING_Y +square.y(), square.size() + square.x() - Settings.PADDING_X, i * Settings.PADDING_Y +square.y()));
        }
        for(int i = 1; i < N + 1; i++) {
            drawLine(new Line(i * Settings.PADDING_X + square.x(), Settings.PADDING_Y + square.y(), i * Settings.PADDING_X + square.x(), square.size() + square.y() - Settings.PADDING_Y));
        }
        for(int i = 1; i < N + 1; i++) {
            for(int j = 1; j < N + 1; j++) {
                if(field[i-1][j-1] == Color.WHITE) {
                    drawCustomStone(new ObjectGui(i * Settings.PADDING_X + square.x(), j * Settings.PADDING_Y + square.y(), Settings.SIZE_OF_STONE, new RgbColor(255,255,255), Settings.WHITE_STONE_FILE_NAME));
                }
                else if(field[i-1][j-1] == Color.BLACK) {
                    drawCustomStone(new ObjectGui(i * Settings.PADDING_X + square.x(), j * Settings.PADDING_Y + square.y(), Settings.SIZE_OF_STONE, new RgbColor(0,0,0), Settings.BLACK_STONE_FILE_NAME));
                }
            }
        }
    }

    public Color[][] getColors(Stone[][] gameField){
        int n = gameField.length;
        Color colors[][] = new Color[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                colors[i][j] = gameField[i][j].getColor();
            }
        }
        return colors;
    }

    public void drawField(Stone[][] gameField){
        this.field = getColors(gameField);
        drawGui.frame.revalidate();
        drawGui.frame.repaint();
        showPanel();
    }

    public Color[][] getField() {
        return field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals(Settings.MOVE)) {
            drawGui.scannerGui.setActionType(Action.MOVE);
            drawGui.frame.revalidate();
            drawGui.frame.repaint();

            // Realize move interaction with client
        }
        else if(line.equals(Settings.PASS)) {
            drawGui.scannerGui.setActionType(Action.SKIP);

            // Realize pass interaction with client
        }
        else if(line.equals(Settings.GIVE_UP)) {
            drawGui.scannerGui.setActionType(Action.SURRENDER);
            drawGui.gameFieldPanel.hidePanel();
            drawGui.startGamePanel.showPanel();

            // Realize surrender interaction with client
        }
    }
}