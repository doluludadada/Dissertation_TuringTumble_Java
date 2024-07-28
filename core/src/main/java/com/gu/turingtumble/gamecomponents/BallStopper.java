package com.gu.turingtumble.gamecomponents;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gu.turingtumble.utils.GameManager;

public class BallStopper {
    //    MODEL
    private Body body;
    private CircleShape shape;
    //    DATE
    public static final float RADIUS = 24f;
    private boolean active;


    public BallStopper(float pos_x, float pos_y, World world) {
        createBody(pos_x, pos_y, world);
    }

    private void createBody(float pos_x, float pos_y, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos_x, pos_y);
        body = world.createBody(bodyDef);

        shape = new CircleShape();
        shape.setRadius(RADIUS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef);

        body.setUserData(this);
        active = true;

    }

    public void toggle() {
        // Toggle the active state
        active = !active;
        for (Fixture fixture : body.getFixtureList()) {
            fixture.setSensor(active);
        }

        if (active) {
            applyForceToBallsInRange();
        }
    }

    private void applyForceToBallsInRange() {
        World world = GameManager.getWorld();
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (body.getUserData() instanceof Ball) {
                Vector2 ballPos = body.getPosition();
                Vector2 stopperPos = this.body.getPosition();
                float distance = ballPos.dst(stopperPos);

                if (distance < RADIUS * 2) {
                    Vector2 force = new Vector2(0, 1f);
                    body.applyLinearImpulse(force, ballPos, true);
                }
            }
        }
    }


    public boolean isActive() {
        return active;
    }


    public void draw(ShapeRenderer shapeRenderer, float x, float y) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (active) {
            shapeRenderer.setColor(Color.GREEN);
        } else {
            shapeRenderer.setColor(Color.GRAY);
        }
        shapeRenderer.circle(x, y, RADIUS);
        shapeRenderer.end();

    }


    public void update(float delta) {

    }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        shape.dispose();
        GameManager.getWorld().destroyBody(body);
    }
}
