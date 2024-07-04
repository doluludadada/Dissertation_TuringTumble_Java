package utils;


import gamecomponents.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static utils.GameConstant.*;


public class GameLogic {

    public static final List<Line2D> tableLines = new ArrayList<>();


    public static void initialiseBalls(List<Ball> redBalls, List<Ball> blueBalls) {


        int centreX = GameConstant.TABLE_WIDTH.getValue() / 2 * GameConstant.CELL_SIZE.getValue();
        int startY = GameConstant.UPPER_SIDE_HEIGHT.getValue() / 3;
        int redStartX = centreX + GameConstant.CELL_SIZE.getValue();
        int blueStartX = centreX - GameConstant.CELL_SIZE.getValue();

        for (int i = 0; i < GameConstant.RED_BALL_COUNT.getValue(); i++) {
            redBalls.add(new Ball(Color.RED, redStartX, startY));
        }
        for (int i = 0; i < GameConstant.BLUE_BALL_COUNT.getValue(); i++) {
            blueBalls.add(new Ball(Color.BLUE, blueStartX, startY));
        }

    }

    public static void moveBalls(List<Ball> balls, Map<Point, GameComponents> components, Set<Point> slots, Set<Point> slotsWithArc) {
        for (Ball ball : balls) {
            ball.moveBall();
            collision(ball, slots, slotsWithArc);
        }
    }

    private static void collision(Ball ball, Set<Point> slots, Set<Point> slotsWithArc) {

        if ((ball.getX() <= 0) || (ball.getX() >= TABLE_WIDTH.getValue() * CELL_SIZE.getValue())) {
            ball.reverseX();
        }

        if ((ball.getY() <= 0) || (ball.getY() >= (TABLE_HEIGHT.getValue() * CELL_SIZE.getValue()) + UPPER_SIDE_HEIGHT.getValue() + BOTTOM_SIDE_HEIGHT.getValue())) {
            ball.reverseY();
        }

        Point ballPoint = new Point(ball.getX() / CELL_SIZE.getValue(), (ball.getY() - (GameTable.UPPER_SIDE_HEIGHT) / CELL_SIZE.getValue()));
        if (slots.contains(ballPoint) || slotsWithArc.contains(ballPoint)) {
            return;
        }


        for (Line2D line : tableLines) {
            if (lineIntersectsBall(line, ball)) {
                handleLineCollision(ball, line);
            }
        }

    }

    public static void initialiseTableLines() {
        int tableWidth = GameConstant.TABLE_WIDTH.getValue() * GameConstant.CELL_SIZE.getValue();
        int tableHeight = GameConstant.TABLE_HEIGHT.getValue() * GameConstant.CELL_SIZE.getValue();
        int upperSideHeight = GameConstant.UPPER_SIDE_HEIGHT.getValue();

        // Centre vertical line
        tableLines.add(new Line2D.Double(tableWidth / 2, 0, tableWidth / 2, upperSideHeight / 3));

        // Left channel
        tableLines.add(new Line2D.Double(tableWidth / 2, upperSideHeight / 3, 2.5 * GameConstant.CELL_SIZE.getValue(), upperSideHeight / 1.6));
        tableLines.add(new Line2D.Double(0, 1.2 * GameConstant.CELL_SIZE.getValue(), 3 * GameConstant.CELL_SIZE.getValue(), 2 * GameConstant.CELL_SIZE.getValue()));

        // Right channel
        tableLines.add(new Line2D.Double(tableWidth / 2, upperSideHeight / 3, 8.5 * GameConstant.CELL_SIZE.getValue(), upperSideHeight / 1.6));
        tableLines.add(new Line2D.Double(tableWidth, 1.2 * GameConstant.CELL_SIZE.getValue(), tableWidth - 3 * GameConstant.CELL_SIZE.getValue(), 2 * GameConstant.CELL_SIZE.getValue()));

        // Bottom area lines
        tableLines.add(new Line2D.Double(0, tableHeight + GameConstant.BOTTOM_SIDE_HEIGHT.getValue(), 4.5 * GameConstant.CELL_SIZE.getValue(), tableHeight + upperSideHeight + GameConstant.CELL_SIZE.getValue() / 1.5));
        tableLines.add(new Line2D.Double(tableWidth, tableHeight + GameConstant.BOTTOM_SIDE_HEIGHT.getValue(), tableWidth / 2, tableHeight + upperSideHeight + GameConstant.CELL_SIZE.getValue() / 1.5));
    }

    private static boolean lineIntersectsBall(Line2D line, Ball ball) {
        double ballRadius = GameConstant.CELL_SIZE.getValue() / 6.0;
        double distanceToLine = line.ptSegDist(ball.getX(), ball.getY());
        return distanceToLine <= ballRadius;
    }

    private static void handleLineCollision(Ball ball, Line2D line) {
        double dx = line.getX2() - line.getX1();
        double dy = line.getY2() - line.getY1();
        double length = Math.sqrt(dx * dx + dy * dy);
        double nx = -dy / length;
        double ny = dx / length;

        double dotProduct = ball.getDx() * nx + ball.getDy() * ny;

        int newDx = (int) (ball.getDx() - 2 * dotProduct * nx);
        int newDy = (int) (ball.getDy() - 2 * dotProduct * ny);

        ball.setDx(newDx);
        ball.setDy(newDy);

        ball.setX(ball.getX() + newDx);
        ball.setY(ball.getY() + newDy);


    }


}
