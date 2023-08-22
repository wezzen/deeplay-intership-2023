package io.deeplay.intership.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameField extends JPanel{
    private String fieldImgName = "C:\\Users\\Gleb1\\Downloads\\board912.jpg";
    private BufferedImage image;

    public GameField(){
        try {
            image = ImageIO.read(new File(fieldImgName));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        image = resize(image, 700, 700);
        g.drawImage(image, 10, 10, this); // see javadoc for more info on the parameters
    }
}
