package com.gu.turingtumble.utils;



import com.badlogic.gdx.physics.box2d.World;
import com.gu.turingtumble.gamecomponents.Ball;

import java.awt.*;
import java.awt.geom.Line2D;

import java.util.List;


public class GameLogic {

    private static final double ENERGY_LOSS = GameConstant.ENERGY_LOSS_VALUE.getDoubleValue();


    public static void initialiseBalls(World world, List<Ball> redBalls, List<Ball> blueBalls) {
        int centreX = GameConstant.TABLE_WIDTH.getValue() / 2 * GameConstant.CELL_SIZE.getValue();
        int startY = GameConstant.UPPER_SIDE_HEIGHT.getValue() / 3 - 10;
        int redStartX = centreX + 10 + GameConstant.CELL_SIZE.getValue();
        int blueStartX = centreX - GameConstant.CELL_SIZE.getValue();

        for (int i = 0; i < GameConstant.RED_BALL_COUNT.getValue(); i++) {
            redBalls.add(new Ball(world, Color.RED, redStartX, startY + i * 5));
        }
        for (int i = 0; i < GameConstant.BLUE_BALL_COUNT.getValue(); i++) {
            blueBalls.add(new Ball(world, Color.BLUE, blueStartX, startY + i * 5));
        }
    }


    public static void moveBalls(List<Ball> balls, List<Line2D> tableLines) {
        for (Ball ball : balls) {
            moveBall(ball, tableLines);
        }
    }

    private static void moveBall(Ball ball, List<Line2D> tableLines) {

    }

    private void OnCollision() {

    }


}
