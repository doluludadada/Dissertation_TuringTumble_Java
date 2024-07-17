package com.gu.turingtumble.gamecomponents;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public void draw(SpriteBatch batch, float x, float y) {

    }
}
