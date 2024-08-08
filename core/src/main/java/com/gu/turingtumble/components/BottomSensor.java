package com.gu.turingtumble.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

/**
 * Class representing a Bottom Sensor in the game.
 * The sensor is a rectangular area that detects ball contacts
 * and triggers specific actions based on the contact location.
 */
public class BottomSensor {
    private final Body sensorBody;
    private final World world;
    private final float width = 85f;  // Width of the sensor
    private final float height = 40f; // Height of the sensor

    /**
     * Constructor for BottomSensor.
     *
     * @param posX  X position of the sensor
     * @param posY  Y position of the sensor
     * @param world Box2D world where the sensor is created
     */
    public BottomSensor(float posX, float posY, World world) {
        this.world = world;
        this.sensorBody = createBody(posX, posY);
    }

    /**
     * Creates the physical body of the sensor in the Box2D world.
     *
     * @param posX X position of the sensor
     * @param posY Y position of the sensor
     * @return The created Box2D body
     */
    private Body createBody(float posX, float posY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posX, posY);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
        return body;
    }

    /**
     * Handles contact with the sensor.
     * Launches a new ball and updates the UI based on the contact location and ball color.
     *
     * @param ball       The ball body contacting the sensor
     * @param isEntering True if the ball is entering the sensor, false if leaving
     */
    public void handleContact(Body ball, boolean isEntering) {
        if (!isEntering) return;

        Ball ballData = (Ball) ball.getUserData();
        if (ballData == null) return;

        Vector2 position = ball.getPosition();
        Vector2 sensorPosition = sensorBody.getPosition();

        int ballColor = ballData.getBallColour().equals(Color.BLUE) ? 0 : 1;
        GameManager.getGameState().addCurrentOutput(ballColor);

        if (position.x < sensorPosition.x) {
            GameManager.getBlueBallStopper().launchBall();
        } else {
            GameManager.getRedBallStopper().launchBall();
        }

        MainGame.getUiManager().updateUI();
    }



    /**
     * Draws the sensor on the screen.
     *
     * @param shapeRenderer The ShapeRenderer used for drawing
     */
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Vector2 position = sensorBody.getPosition();
        float halfWidth = width / 2;
        float halfHeight = height / 2;

        // Draw blue half
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(position.x - halfWidth, position.y - halfHeight, halfWidth, height);

        // Draw red half
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(position.x, position.y - halfHeight, halfWidth, height);

        // Draw middle black line
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rectLine(position.x, position.y - halfHeight, position.x, position.y + halfHeight, 2);

        shapeRenderer.end();
    }

    /**
     * Gets the Box2D body of the sensor.
     *
     * @return The Box2D body of the sensor
     */
    public Body getBody() {
        return sensorBody;
    }
}
