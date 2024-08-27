package com.gu.turingtumble.components;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.utils.GameConstant;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Ball {
    private final Body ballBody;
    private final Color ballColour;
    private final Color borderColour;
    private static final float RADIUS = GameConstant.CELL_SIZE.get() / 8f;
    private static final float BORDER_WIDTH = 2f;
//    private Vector2 customGravity;


    public Ball(World world, Color colour, float x, float y) {
        this.ballColour = colour;
        this.borderColour = colour.equals(Color.BLUE) ? new Color(0.5f, 0.75f, 1f, 1f) : new Color(0.5f, 0f, 0f, 1f);
//        this.customGravity = customGravity;


        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x, y);
        ballBody = world.createBody(bd);
        bd.allowSleep = true;
        bd.bullet = false;

        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 100f;
        fixtureDef.restitution = 0f;

        ballBody.createFixture(fixtureDef);
        shape.dispose();
//        body.setGravityScale(1f);

//        For contact detect
        ballBody.setUserData(this);

    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw border
        shapeRenderer.setColor(borderColour);
        shapeRenderer.circle(ballBody.getPosition().x, ballBody.getPosition().y, RADIUS + BORDER_WIDTH);

        // Draw ball
        shapeRenderer.setColor(ballColour);
        shapeRenderer.circle(ballBody.getPosition().x, ballBody.getPosition().y, RADIUS);

        shapeRenderer.end();

    }

    public Body getBallBody() {
        return ballBody;
    }

    public Color getBallColour() {
        return ballColour;
    }

    public void sleep() {
        ballBody.setAwake(false);
    }

    public void wakeUp() {
        ballBody.setAwake(true);
    }

    public void dispose() {
        if (ballBody != null) {
            ballBody.getWorld().destroyBody(ballBody);
        }
    }
}
