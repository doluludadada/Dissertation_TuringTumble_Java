package utils;


import gamecomponents.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static utils.GameConstant.*;

public class GameLogic {
    public static void initialiseBalls(List<Ball> redBalls, List<Ball> blueBalls) {
        for (int i = 0; i < GameConstant.RED_BALL_COUNT.getValue(); i++) {
            redBalls.add(new Ball(Color.RED, GameConstant.CELL_SIZE.getValue() * 2, TABLE_HEIGHT.getValue() / 2));
        }
        for (int i = 0; i < GameConstant.BLUE_BALL_COUNT.getValue(); i++) {
            blueBalls.add(new Ball(Color.BLUE, GameConstant.CELL_SIZE.getValue() * 2, TABLE_HEIGHT.getValue() / 2));
        }
    }

    public static void moveBalls(List<Ball> balls, Map<Point, GameComponents> components) {
        for (Ball ball : balls) {
            ball.moveBall();
            collision(ball, components);
        }
    }

    private static void collision(Ball ball, Map<Point, GameComponents> components) {
        for (Map.Entry<Point, GameComponents> entry : components.entrySet()) {
            Point point = entry.getKey();
            GameComponents gameComponents = entry.getValue();
            int x = point.x * GameConstant.CELL_SIZE.getValue();
            int y = point.y * GameConstant.CELL_SIZE.getValue();


            if ((ball.getX() <= 0) || (ball.getX() >= TABLE_WIDTH.getValue() * GameConstant.CELL_SIZE.getValue())) {

            }

            if ((ball.getY() <= 0) || (ball.getY() >= TABLE_HEIGHT.getValue() * GameConstant.CELL_SIZE.getValue())) {

            }

        }

    }


}
