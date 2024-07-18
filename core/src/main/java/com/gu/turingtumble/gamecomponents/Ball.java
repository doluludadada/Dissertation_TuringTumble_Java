package com.gu.turingtumble.gamecomponents;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.utils.GameConstant;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Ball {
    private Body body;
    private Color ballColour;
    private static final float RADIUS = GameConstant.CELL_SIZE.getValue() / 6f;


    public Ball(World world, Color colour, float x, float y) {

        this.ballColour = colour;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);


        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(ballColour);
        shapeRenderer.circle(body.getPosition().x, body.getPosition().y, RADIUS);
        shapeRenderer.end();
    }


}
