package com.gu.turingtumble.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.gu.turingtumble.utils.GameManager;

public class BallStopper {
    // Model
    private final Body stopperBody;
    private final World world;
    // Data
    public static final float RADIUS = 24f;
    // Functional
    private boolean shouldResetBallPosition;
    private Body capturedBall;


    public BallStopper(float posX, float posY, World world) {
        this.world = world;
        this.stopperBody = createBody(posX, posY);
        this.capturedBall = null;
        this.shouldResetBallPosition = false;
    }

    private Body createBody(float posX, float posY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posX, posY);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
        return body;
    }


    public void handleContact(Body ball) {
        if (capturedBall == null) {
            capturedBall = ball;
            shouldResetBallPosition = true;
//            setSensor(false);
        }
    }

    public void update() {
        if (shouldResetBallPosition && capturedBall != null) {
            shouldResetBallPosition = false;
            setSensor(false); // Set sensor to false to prevent other balls from entering
            System.out.println("Ball captured");
        }

        // Keep the captured ball at the stopper's position
        if (capturedBall != null && stopperBody != null) {
            capturedBall.setTransform(stopperBody.getPosition(), capturedBall.getAngle());
            capturedBall.setLinearVelocity(Vector2.Zero);
            capturedBall.setAngularVelocity(0);
        }
    }

    public void launchBall() {
        if (capturedBall != null) {
            capturedBall.setLinearVelocity(new Vector2(0, -50f));
//            setSensor(true); // Set sensor to true to allow the next ball to be captured
            System.out.println("Ball launched from: " + stopperBody.getPosition());
            capturedBall = null;
            GameManager.giveBallEnergy();

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    setSensor(true);
                }
            }, 0.01f); // 10毫秒延遲

        }
    }

    private void setSensor(boolean isSensor) {
        for (Fixture fixture : stopperBody.getFixtureList()) {
            fixture.setSensor(isSensor);
        }
        System.out.println("Setting stopper sensor to: " + isSensor);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(getColor());
        Vector2 position = stopperBody.getPosition();
        shapeRenderer.circle(position.x, position.y, RADIUS);
        shapeRenderer.end();
    }

    private Color getColor() {
        if (capturedBall != null) {
            return Color.YELLOW;
        } else {
            return Color.GREEN;
        }
    }

    public Body getBody() {
        return stopperBody;
    }


}








