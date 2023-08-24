package io.deeplay.intership.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
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

public class GameFieldPanel extends JPanel implements ActionListener {
    private final DrawGui drawGui;
    private final ObjectGui square;
    private final RgbColor lineColor;
    private final int paddingX = 50;
    private final int paddingY = 50;
    private final int sizeOfStone = 38;
    private final int buttonWidth = 150;
    private final int buttonHeight = 50;
    private final String resourceDir = "modules\\gui\\src\\main\\resources\\";
    private final String[] actionNames = new String[]{"Move", "Pass", "Give up"};
    private final String boardFileName = resourceDir + "board\\board.jpg";
    private final String whiteStoneFileName = resourceDir + "stone\\white_stone.png";
    private final String blackStoneFileName = resourceDir + "stone\\black_stone.png";
    private Graphics2D graphics2D;
    
    private Color[][] field;

    public GameFieldPanel(DrawGui drawGui){
        field = new Color[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                field[i][j] = Color.EMPTY;
            }
        }
        this.drawGui = drawGui;
        this.square = new ObjectGui(50, 50, 500, new RgbColor(103,60,44), boardFileName);
        //this.lineColor = new RgbColor(240, 142, 62);
        //this.lineColor = new RgbColor(255, 196, 77);
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

    private void drawLine(Line line){
        float[] hsb = getHsbColor(lineColor.getRgbColor());
        graphics2D.setColor(java.awt.Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
        graphics2D.drawLine(line.x1(), line.y1(), line.x2(), line.y2());
    }

    public void drawCustomSquare(){
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(square.fileName()));
            BufferedImage image = bufferedImage.getSubimage(0, 0, 500, 500);
            graphics2D.drawImage(image, 50, 50, null);
        } catch (IOException e) {
            float[] hsb = getHsbColor(square.rgbColor().getRgbColor());
            graphics2D.setColor(new java.awt.Color(hsb[0], hsb[1], hsb[2]));
            graphics2D.fillRect(50, 50, 500, 500);
        }
    }

    public void drawCustomStone(ObjectGui figure){
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(figure.fileName()));
            BufferedImage newBufferedImage = resize(bufferedImage, 38, 38);
            graphics2D.drawImage(newBufferedImage, figure.x() - sizeOfStone/2,
                    figure.y() - sizeOfStone/2, null);
        } catch (IOException e) {
            float[] hsb = getHsbColor(figure.rgbColor().getRgbColor());
            graphics2D.setColor(new java.awt.Color(hsb[0], hsb[1], hsb[2]));
            graphics2D.fillOval(figure.x(), figure.y(), figure.size(),  figure.size());
        }
    }

    private float[] getHsbColor(int[] rgbColor){
        return java.awt.Color.RGBtoHSB(rgbColor[0], rgbColor[1], rgbColor[2], new float[3]);
    }

    public JButton getActionButton(int numberOfName, int x, int y) {
        JButton jButton = new JButton(actionNames[numberOfName]);
        jButton.setLocation(x, y);
        jButton.setSize(buttonWidth, buttonHeight);
        return jButton;
    }

    public void setStone(int x, int y) {
        for(int i = 1; i < 10; i++) {
            for(int j = 1; j < 10; j++) {
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

    public void showPanel(){
        drawGui.frame.setVisible(true);
    }

    public void hidePanel(){
        drawGui.frame.setVisible(false);
    }

    public void setPanel(){
        JPanel controlPanel = new JPanel();
        drawGui.frame.add(controlPanel);
        drawGui.frame.add(this);
        GridLayout layout = new GridLayout(3, 1, 0, 20);
        controlPanel.setLocation(700, 100);
        controlPanel.setSize(150, 210);
        controlPanel.setLayout(layout);
        drawGui.frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setStone(e.getX(), e.getY());
            }
        });
        //controlPanel.setBorder(BorderFactory.createEmptyBorder(150, 100, 150, 100));
        for(int i = 0; i < actionNames.length; i++){
            JButton jButton = getActionButton(i, 1000, 100 + i * 50);
            jButton.addActionListener(this);
            controlPanel.add(jButton);
        }
        drawGui.frame.setSize(1000, 650);
        drawGui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paintComponent(Graphics graphics) {
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
                if(field[i-1][j-1] == Color.WHITE) {
                    drawCustomStone(new ObjectGui(i * paddingX + square.x(), j * paddingY + square.y(), 38, new RgbColor(0,0,0), whiteStoneFileName));
                }
                else if(field[i-1][j-1] == Color.BLACK){
                    drawCustomStone(new ObjectGui(i * paddingX + square.x(), j * paddingY + square.y(), 38, new RgbColor(255,255,255), blackStoneFileName));
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line = e.getActionCommand();
        if(line.equals("Move")){
            this.revalidate();
            this.repaint();
            // Realize move interaction with client
        }
        else if(line.equals("Pass")){
            // Realize pass interaction with client
        }
        else if(line.equals("Give up")){
            drawGui.gameFieldPanel.hidePanel();
            // Realize surrender interaction with client
        }
    }
}
