package com.gu.turingtumble.gamecomponents;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;




public class Crossover implements GameComponents  {
    private BufferedImage image;

    public Crossover() {
        try {
            image = ImageIO.read(new File("B:\\2840781t-development-project\\solution\\photos\\Orange.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void draw(Graphics2D g, int x, int y) {
        g.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
    }
}
