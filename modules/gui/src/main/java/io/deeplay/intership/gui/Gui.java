package io.deeplay.intership.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gui extends JPanel {
    private ObjectGui square;
    private RgbColor lineColor;
    private int paddingX = 50;
    private int paddingY = 50;
    private String resourceDir = "modules\\gui\\src\\main\\resources\\";
    private String boardFileName = resourceDir + "board\\board.jpg";
    private String whiteStoneFileName = resourceDir + "stone\\white_stone.png";
    private String blackStoneFileName = resourceDir + "stone\\black_stone.png";
    private Graphics2D graphics2D;

    public Gui(){
        this.square = new ObjectGui(50, 50, 500, new RgbColor(103,60,44), boardFileName);
        this.lineColor = new RgbColor(240, 142, 62);
    }

    public BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        Image tmp = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage imageResized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = imageResized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return imageResized;
    }

    private void drawLine(Line line){
        float[] hsb = getHsbColor(lineColor.getRgbColor());
        graphics2D.setColor(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        graphics2D.drawLine(line.x1(), line.y1(), line.x2(), line.y2());
    }

    public void drawCustomSquare(){
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(square.fileName()));
            BufferedImage image = bufferedImage.getSubimage(0, 0, 500, 500);
            graphics2D.drawImage(image, 50, 50, null);
        } catch (IOException e) {
            float[] hsb = getHsbColor(square.rgbColor().getRgbColor());
            graphics2D.setColor(new Color(hsb[0], hsb[1], hsb[2]));
            graphics2D.fillRect(50, 50, 500, 500);
        }
    }

    public void drawCustomStone(ObjectGui figure){
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(figure.fileName()));
            BufferedImage newBufferedImage = resize(bufferedImage, 38, 38);
            graphics2D.drawImage(newBufferedImage, figure.x() - 19, figure.y() - 19, null);
        } catch (IOException e) {
            float[] hsb = getHsbColor(figure.rgbColor().getRgbColor());
            graphics2D.setColor(new Color(hsb[0], hsb[1], hsb[2]));
            graphics2D.fillOval(figure.x(), figure.y(), figure.size(),  figure.size());
        }
    }

    private float[] getHsbColor(int[] rgbColor){
        return Color.RGBtoHSB(rgbColor[0], rgbColor[1], rgbColor[2], new float[3]);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics2D = (Graphics2D) graphics;
        drawCustomSquare();
        for(int i = 1; i < 10; i++) {
            drawLine(new Line(paddingX + square.x(), i * paddingY+square.y(), square.size() + square.x() - paddingX, i * paddingY+square.y()));
        }
        for(int i = 1; i < 10; i++) {
            drawLine(new Line(i * paddingX + square.x(), paddingY + square.y(), i * paddingX + square.x(), square.size() + square.y() - paddingY));
        }
        for(int i = 1; i < 10; i++){
            for(int j = 1; j < 10; j++){
                if(i == 1 || i == 9 || j == 1 || j == 9) {
                    drawCustomStone(new ObjectGui(i * paddingX + square.x(), j * paddingY + square.y(), 38, new RgbColor(0,0,0), whiteStoneFileName));
                    //drawFigure(graphics, new Figure(i * paddingX + square.x(), j * paddingY + square.y(), 34, new RgbColor(3, 19, 16)));
                }
                else if(i != 2 && i != 8 && j != 2 && j != 8){
                    drawCustomStone(new ObjectGui(i * paddingX + square.x(), j * paddingY + square.y(), 38, new RgbColor(255,255,255), blackStoneFileName));
                    //drawFigure(graphics, new Figure(i * paddingX + square.x(), j * paddingY + square.y(), 34, new RgbColor(236,238,233)));
                }
            }
        }
    }
}
