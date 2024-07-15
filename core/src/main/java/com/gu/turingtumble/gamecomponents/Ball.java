package com.gu.turingtumble.gamecomponents;



import java.awt.*;

import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.utils.GameConstant;


public class Ball implements GameComponents {
    //    private Colour colour;
    private int x, y;
    private double dx, dy; // Gravity
    private static final int BALL_SIZE = GameConstant.CELL_SIZE.getValue() / 3;
    private Color ballColor;
    private Body body;


    public Ball() {
    }

    public Ball(World world, Color color, int x, int y) {

//        this.x = x;
//        this.y = y;
//        this.dx = 0;
//        this.dy = 1;
        this.ballColor = color;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);


        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(BALL_SIZE / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);


    }

    @Override
    public void draw(Graphics2D g, int x, int y) {

        g.setColor(ballColor);
        g.fillOval(x - BALL_SIZE / 2, y - BALL_SIZE / 2, BALL_SIZE, BALL_SIZE);

    }


    public void setBallColor(Color color) {
        this.ballColor = color;
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

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}
