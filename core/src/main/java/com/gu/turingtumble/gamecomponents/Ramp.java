package com.gu.turingtumble.gamecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;


/**
 * Game component, Ramp
 */
public class Ramp implements GameComponents {
    //    MODEL
    private Body rampBody;
    private Vector2 rampModelOrigin;
    private RevoluteJoint joint;
    //    Render
    private Sprite rampSprite;
    private Texture rampTexture;
    //    Data
    private static final float RAMP_WIDTH = 70f;
    private static final float RAMP_HEIGHT = 70f;
    private static final float ROTATION = (float) Math.toRadians(-45);
    private static final float ROTATION_SPEED = 5f;
    //    Functional
    private float currentRotation = 0;
    private boolean isRotating = false;
    private boolean rotateClockwise = true;
    private float pos_x, pos_y;

    /**
     TODO Ramp固定在slot上遇到撞擊根據力道最大向右傾斜45度
     TODO 撞擊後重新繪圖，將圖形跟模型右轉45度
     TODO 當球離開後後就回復一開始狀態
     */


    public Ramp(float x, float y, World world, BodyEditorLoader loader) {
        this.pos_x = x;
        this.pos_y = y;
        createRamp(x - 30, y - 30, world, loader);
        createSprite();
    }


    private void createRamp(float pos_x, float pos_y, World world, BodyEditorLoader loader) {
        // 1. Create a BodyDef
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.KinematicBody;
        bd.position.set(pos_x, pos_y);

        // 2. Create a FixtureDef
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        // 3. Create a Body
        rampBody = world.createBody(bd);
        rampBody.setUserData(this);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(rampBody, "Green", fd, RAMP_HEIGHT);
        rampModelOrigin = loader.getOrigin("Green", RAMP_HEIGHT).cpy();
    }



    private void createSprite() {

        rampTexture = new Texture(Gdx.files.internal("assets/Green.png"));
        rampTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        rampSprite = new Sprite(rampTexture);
        rampSprite.setSize(RAMP_WIDTH, RAMP_WIDTH * rampSprite.getHeight() / rampSprite.getWidth());

    }

    @Override
    public void draw(SpriteBatch batch, float x, float y) {
        Vector2 position = rampBody.getPosition().sub(rampModelOrigin);
        if (currentRotation == ROTATION) {
            rampSprite.setPosition(x - 11, y - 90);
            rampBody.setTransform(x - 40, y, currentRotation);
        } else {
            rampSprite.setPosition(position.x, position.y);
        }
        rampSprite.setRotation((float) Math.toDegrees(currentRotation));
        rampSprite.draw(batch);

    }

    @Override
    public Vector2 getPosition() {
        return rampBody.getPosition();
    }

    /*
    TODO:球碰撞前原點座標為x-30,y-30(貼合圖形跟slot)
    TODO:球碰撞後模型右轉45度 且原點座標為x+20, y-60(貼合圖形跟slot)
    TODO:球離開Ramp後則自動回復為一開始狀態
     */
    @Override
    public void update(float delta) {
        if (isRotating) {
            if (rotateClockwise) {
                currentRotation += ROTATION_SPEED * delta;
                if (currentRotation >= ROTATION) {
                    currentRotation = ROTATION;
                    isRotating = false;
                }
            } else {
                currentRotation -= ROTATION_SPEED * delta;
                if (currentRotation <= 0) {
                    currentRotation = 0;
                    isRotating = false;
                }
            }
//            rampBody.setTransform(pos_x - 37, pos_y - 40, currentRotation);
        }
    }

    public void rotateRamp() {
        if (!isRotating) {
            isRotating = true;
            rotateClockwise = true;
//            updateCollisionModel();
        }

    }

    public void resetRamp() {
        if (isRotating || currentRotation > 0) {
            isRotating = true;
            rotateClockwise = false;
//            resetRampModel();
        }
    }

}




