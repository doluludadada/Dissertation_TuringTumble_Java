package com.gu.turingtumble.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.gu.turingtumble.utils.GameManager;

import java.util.List;

public class BallStopper {
    // Model
    private final Body stopperBody;
    private final World world;
    // Data
    public static final float RADIUS = 24f;
    // Functional
//    private boolean shouldResetBallPosition;
    private Body capturedBall;


    public BallStopper(float posX, float posY, World world) {
        this.world = world;
        this.stopperBody = createBody(posX, posY);
        this.capturedBall = null;
//        this.shouldResetBallPosition = false;
    }

    private Body createBody(float posX, float posY) {
        BodyDef def = new BodyDef();
        def.allowSleep = false;
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(posX, posY);
        Body body = world.createBody(def);
        def.allowSleep = false;

        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 10.0f;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
        return body;
    }


    public void handleContact(Body ball) {
        if (capturedBall == null) {
            capturedBall = ball;
//            shouldResetBallPosition = true;
        }
    }

    public void update() {
//        if (shouldResetBallPosition && capturedBall != null) {
//            shouldResetBallPosition = false;
//            System.out.println("Ball captured");
//        }

        // Keep the captured ball at the stopper's position
        if (capturedBall != null && stopperBody != null) {
            capturedBall.setTransform(stopperBody.getPosition(), capturedBall.getAngle());
            capturedBall.setLinearVelocity(Vector2.Zero);
            capturedBall.setAngularVelocity(0);
        }
    }

    public void launchBall() {
        if (capturedBall != null) {
            capturedBall.setLinearVelocity(new Vector2(0, -10f));
            GameManager.giveBallEnergy();
            System.out.println("Ball launched from: " + stopperBody.getPosition());
            capturedBall = null;
            wakeUpOtherBalls();
        }
    }

//    private void setSensor(boolean isSensor) {
//        for (Fixture fixture : stopperBody.getFixtureList()) {
//            fixture.setSensor(isSensor);
//        }
//        System.out.println("Setting stopper sensor to: " + isSensor);
//    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw outer border
        shapeRenderer.setColor(Color.BLACK);
        Vector2 position = stopperBody.getPosition();
        shapeRenderer.circle(position.x, position.y, RADIUS + 2); // Adjust border thickness

        // Draw stopper
        shapeRenderer.setColor(getColor());
        shapeRenderer.circle(position.x, position.y, RADIUS);

        shapeRenderer.end();
    }

    private Color getColor() {
        int allowedStopper = GameManager.getGameState().getAllowedBallStopper();
            if (allowedStopper == 1 && this == GameManager.getRedBallStopper()) {
            return Color.RED; // Highlight red stopper if allowed
        } else if (allowedStopper == 0 && this == GameManager.getBlueBallStopper()) {
            return Color.BLUE; // Highlight blue stopper if allowed
        } else {
            return Color.BLACK;
        }
    }


    public Body getBody() {
        return stopperBody;
    }

    public void reset() {
        capturedBall = null;
//        shouldResetBallPosition = false;
//        setSensor(false);
    }


    private void wakeUpOtherBalls() {
        for (Ball b : GameManager.getBlueBalls()) {
            b.wakeUp();
        }
        for (Ball b : GameManager.getRedBalls()) {
            b.wakeUp();
        }
    }


}








