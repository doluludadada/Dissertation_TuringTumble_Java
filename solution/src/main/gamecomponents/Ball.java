package gamecomponents;

import utils.GameConstant;

import java.awt.*;

public class Ball implements GameComponents {
    //    private Colour colour;
    private int x, y;
    private int dx, dy; // Gravity
    private static final int BALL_SIZE = GameConstant.CELL_SIZE.getValue() / 3;
    private Color ballColor;
    private static final int GRAVITY = GameConstant.GRAVITY.getValue();

    public Ball() {
    }

    public Ball(Color color, int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 1;
        this.ballColor = color;

    }

    @Override
    public void draw(Graphics2D g, int x, int y) {
        g.setColor(ballColor);
        g.fillOval(x - BALL_SIZE / 2, y - BALL_SIZE / 2, BALL_SIZE, BALL_SIZE);
    }

    public void moveBall() {
        dy += GRAVITY;
        this.x += this.dx;
        this.y += this.dy;
    }

    public void setBallColor(Color color) {
    }

    public void reverseX() {
        dx = -dx;
    }

    public void reverseY() {
        dy = -dy;
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

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}