package com.gu.turingtumble.gamecomponents;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.gu.turingtumble.utils.GameConstant;



public class Ramp implements GameComponents {
    private BufferedImage image;


    public Ramp() {
        try {
            image = ImageIO.read(new File("B:\\2840781t-development-project\\solution\\photos\\Green.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        double scale = (GameConstant.CELL_SIZE.getValue() - 10.0) / Math.max(image.getWidth(), image.getHeight());
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);
        g2d.scale(0.45, 0.45);
        g2d.drawImage((Image) image, (int) (-image.getWidth() / 2.4), (int) (-image.getHeight() / 1.9), null);
        g2d.dispose();
    }

}
