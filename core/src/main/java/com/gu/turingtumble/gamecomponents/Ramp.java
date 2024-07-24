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


    public Ramp(float x, float y, World world, BodyEditorLoader loader) {
        createRamp(x - 30, y - 30, world, loader);
        createSprite();
//        createJoint(rampBody.getPosition().x, rampBody.getPosition().y, world);
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

    private void createJoint(float x, float y, World world) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = rampBody;
        jointDef.bodyB = world.createBody(new BodyDef());  // dummy body to act as anchor
        jointDef.localAnchorA.set(rampModelOrigin);
        jointDef.localAnchorB.set(rampModelOrigin);
        jointDef.enableMotor = true;
        jointDef.motorSpeed = 0;
        jointDef.maxMotorTorque = 1000;
        joint = (RevoluteJoint) world.createJoint(jointDef);
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
        rampSprite.setPosition(position.x + 20, position.y - 60);
        rampSprite.setRotation(-45);
        rampSprite.draw(batch);
    }

    @Override
    public Vector2 getPosition() {
        return rampBody.getPosition();
    }

    @Override
    public void update(float delta) {

    }


    public void rotateRamp() {

    }

    public void resetRamp() {

    }


}




