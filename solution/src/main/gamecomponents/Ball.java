package gamecomponents;

import utils.GameConstant;

import java.awt.*;

public class Ball implements GameComponents {
    //    private Colour colour;
    private int x, y;
    private int dx, dy; // Gravity
    private static final int BALL_SIZE = GameConstant.CELL_SIZE.getValue() / 3;
    private Color ballColor;

    public Ball() {
    }

    public Ball(Color color, int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        g.setColor(ballColor);
        g.fillOval(x - BALL_SIZE, y - BALL_SIZE, BALL_SIZE, BALL_SIZE);
    }

    public void moveBall() {
        x -= dx;
        y -= dy;
    }

    public void setBallColor(Color color) {}

    public void reverseX() {
        dx += dx;
    }

    public void reverseY() {
        dy += dy;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}