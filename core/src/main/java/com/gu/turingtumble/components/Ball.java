package com.gu.turingtumble.components;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.utils.GameConstant;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Ball {
    private Body body;
    private Color ballColour;
    private static final float RADIUS = GameConstant.CELL_SIZE.get() / 8f;
//    private Vector2 customGravity;


    public Ball(World world, Color colour, float x, float y) {
        this.ballColour = colour;
//        this.customGravity = customGravity;


        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x, y);
        body = world.createBody(bd);

        bd.bullet = true;

        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 50f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        shape.dispose();
//        body.setGravityScale(1f);

//        For contact detect
        body.setUserData(this);

    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(ballColour);
        shapeRenderer.circle(body.getPosition().x, body.getPosition().y, RADIUS);
        shapeRenderer.end();
    }

    public Body getBody() {
        return body;
    }
}
