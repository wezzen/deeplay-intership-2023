package io.deeplay.intership.ui.gui.stuff;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.gui.*;

import static java.lang.Math.abs;
import java.util.Random;

public class GameField extends JPanel {
    public final DisplayGui displayGui;
    public final ObjectGui square;
    private RgbColor lineColor;
    public final int N;
    public final JLabel gameId;
    private Graphics2D graphics2D;
    private Color[][] field;

    public GameField(DisplayGui displayGui) {
        N = displayGui.scannerGui.getSize();
        field = new Color[N][N];
        fillField();

        gameId = new JLabel();

        this.displayGui = displayGui;
        this.square = new ObjectGui(0, 100, Settings.SIZE_OF_FIELD, new RgbColor(242,176,109), Settings.BOARD_FILE_NAME);
        this.lineColor = new RgbColor(Settings.LINE_RED, Settings.LINE_GREEN, Settings.LINE_BLUE);
    }

    public void fillField(){
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                field[i][j] = Color.EMPTY;
            }
        }
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
                    if (field[i - 1][j - 1] == Color.EMPTY &&
                            displayGui.scannerGui.isTurn()) {
                        field[i - 1][j - 1] = displayGui.scannerGui.getColor();
                        displayGui.scannerGui.setRowNumber(i - 1);
                        displayGui.scannerGui.setColumnNumber(j - 1);
                        return;
                    }
                }
            }
        }
    }

    public String getGameId() {
        int max = 100000, min = 10000;
        return String.valueOf(new Random().nextInt((max - min) + 1) + min);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics2D = (Graphics2D) graphics;
        if(!displayGui.scannerGui.isBackStyle()) {
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

    public void setField(Stone[][] gameField){
        int n = gameField.length;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++){
                this.field[i][j] = gameField[i][j].getColor();
            }
        }
    }

    public Color[][] getField() {
        return field;
    }
}