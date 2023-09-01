package io.deeplay.intership.gui;

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
import io.deeplay.intership.model.Color;
import static java.lang.Math.abs;
import java.util.Random;

public class GameFieldPanel extends JPanel implements ActionListener {
    public final DrawGui drawGui;
    public final ObjectGui square;
    private RgbColor lineColor;
    public final int paddingX = 60;
    public final int paddingY = 60;
    public final int sizeOfStone = 40;
    public final int sizeOfField = 600;
    public final int buttonWidth = 150;
    public final int buttonHeight = 50;
    public final int N;
    public final JButton buttonMove;
    public final JButton buttonPass;
    public final JButton buttonSurrender;
    public final JLabel gameId;
    public final String resourceDir = "modules/gui/src/main/resources/";
    public final String[] actionNames = new String[]{"Move", "Pass", "Give up"};
    public final String boardFileName = resourceDir + "board/board.jpg";
    public final String whiteStoneFileName = resourceDir + "stone/white_stone.png";
    public final String blackStoneFileName = resourceDir + "stone/black_stone.png";
    private Graphics2D graphics2D;
    private Color[][] field;

    public GameFieldPanel(DrawGui drawGui) {
        N = drawGui.scannerGui.getSize();
        field = new Color[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                field[i][j] = Color.EMPTY;
            }
        }
        buttonMove = new JButton("Move");
        buttonPass = new JButton("Pass");
        buttonSurrender = new JButton("Give up");
        gameId = new JLabel();
        this.drawGui = drawGui;
        this.square = new ObjectGui(0, 100, sizeOfField, new RgbColor(242,176,109), boardFileName);
        this.lineColor = new RgbColor(235, 233, 230);
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
        float[] hsb = getHsbColor(lineColor.getRgbColor());
        graphics2D.setColor(java.awt.Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        graphics2D.drawLine(line.x1(), line.y1(), line.x2(), line.y2());
    }

    public void drawCustomSquare() {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(square.fileName()));
            BufferedImage image = bufferedImage.getSubimage(0, 0, sizeOfField, sizeOfField);
            graphics2D.drawImage(image, square.x(), square.y(), null);
        } catch (IOException e) {
            drawStandardSquare();
        }
    }

    public void drawStandardSquare(){
        int[] rgb = square.rgbColor().getRgbColor();
        this.lineColor = new RgbColor(0, 0, 0);
        //graphics2D.setColor(new java.awt.Color(hsb[0], hsb[1], hsb[2]));
        graphics2D.setColor(new java.awt.Color(rgb[0], rgb[1], rgb[2]));
        graphics2D.fillRect(square.x(), square.y(), sizeOfField, sizeOfField);
    }

    public void drawCustomStone(ObjectGui figure) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(figure.fileName()));
            BufferedImage newBufferedImage = resize(bufferedImage, sizeOfStone, sizeOfStone);
            graphics2D.drawImage(newBufferedImage, figure.x() - sizeOfStone/2,
                    figure.y() - sizeOfStone/2, null);
        } catch (IOException e) {
            float[] hsb = getHsbColor(figure.rgbColor().getRgbColor());
            graphics2D.setColor(new java.awt.Color(hsb[0], hsb[1], hsb[2]));
            graphics2D.fillOval(figure.x(), figure.y(), figure.size(),  figure.size());
        }
    }

    private float[] getHsbColor(int[] rgbColor) {
        return java.awt.Color.RGBtoHSB(rgbColor[0], rgbColor[1], rgbColor[2], new float[3]);
    }

    public void setStone(int x, int y) {
        for(int i = 1; i < N + 1; i++) {
            for(int j = 1; j < N + 1; j++) {
                if (abs(x - i * paddingX - square.x()) <= paddingX / 2 &&
                        abs((y-30) - j * paddingY - square.y()) <= paddingY / 2) {
                    if (field[i - 1][j - 1] == Color.EMPTY) {
                        boolean turn = drawGui.scannerGui.isTurn();
                        if (turn) {
                            field[i-1][j-1] = Color.BLACK;
                            drawGui.scannerGui.setTurn(false);
                        } else {
                            field[i-1][j-1] = Color.WHITE;
                            drawGui.scannerGui.setTurn(true);
                        }
                        return;
                    }
                }
            }
        }
    }

    public void showPanel() {
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
        //return String.valueOf(Math.random());
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
        drawGui.frame.setSize(616, 738);
        drawGui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics2D = (Graphics2D) graphics;
        if(drawGui.scannerGui.isBackStyle()) {
            drawStandardSquare();
        }
        else {
            drawCustomSquare();
        }
        for(int i = 1; i < N + 1; i++) {
            drawLine(new Line(paddingX + square.x(), i * paddingY+square.y(), square.size() + square.x() - paddingX, i * paddingY+square.y()));
        }
        for(int i = 1; i < N + 1; i++) {
            drawLine(new Line(i * paddingX + square.x(), paddingY + square.y(), i * paddingX + square.x(), square.size() + square.y() - paddingY));
        }
        for(int i = 1; i < N + 1; i++) {
            for(int j = 1; j < N + 1; j++) {
                if(field[i-1][j-1] == Color.WHITE) {
                    drawCustomStone(new ObjectGui(i * paddingX + square.x(), j * paddingY + square.y(), sizeOfStone, new RgbColor(0,0,0), whiteStoneFileName));
                }
                else if(field[i-1][j-1] == Color.BLACK) {
                    drawCustomStone(new ObjectGui(i * paddingX + square.x(), j * paddingY + square.y(), sizeOfStone, new RgbColor(255,255,255), blackStoneFileName));
                }
            }
        }
    }

    public Color[][] getField() {
        return field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Move")) {
            drawGui.scannerGui.setCommandType(1);
            drawGui.frame.revalidate();
            drawGui.frame.repaint();
            //this.revalidate();
            //this.repaint();
            // Realize move interaction with client
        }
        else if(line.equals("Pass")) {
            drawGui.scannerGui.setCommandType(2);
            // Realize pass interaction with client
        }
        else if(line.equals("Give up")) {
            drawGui.scannerGui.setCommandType(3);
            drawGui.gameFieldPanel.hidePanel();
            drawGui.startGamePanel.showPanel();
            // Realize surrender interaction with client
        }
    }
}
