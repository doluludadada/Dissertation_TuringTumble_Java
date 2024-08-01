package com.gu.turingtumble.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.utils.GameManager;

public class BottomSensor {
    private final Body sensorBody;
    private final World world;
    public static final float SIZE = 80f; // 正方形的邊長

    public BottomSensor(float posX, float posY, World world) {
        this.world = world;
        this.sensorBody = createBody(posX, posY);
    }

    private Body createBody(float posX, float posY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(posX, posY);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SIZE / 2, SIZE / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
        return body;
    }

    public void handleContact(Body ball, boolean isEntering) {
        Vector2 position = ball.getPosition();
        Vector2 sensorPosition = sensorBody.getPosition();

        if (position.x < sensorPosition.x) {
            // 左半部分（藍色）
            if (isEntering) {
                GameManager.getBlueBallStopper().launchBall();
            }
        } else {
            // 右半部分（紅色）
            if (isEntering) {
                GameManager.getRedBallStopper().launchBall();
            }
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Vector2 position = sensorBody.getPosition();
        float halfSize = SIZE / 2;

        // 畫藍色部分
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(position.x - halfSize, position.y - halfSize, halfSize, SIZE);

        // 畫紅色部分
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(position.x, position.y - halfSize, halfSize, SIZE);

        // 畫中間的黑色線
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rectLine(position.x, position.y - halfSize, position.x, position.y + halfSize, 2);

        shapeRenderer.end();
    }

    public Body getBody() {
        return sensorBody;
    }
}
