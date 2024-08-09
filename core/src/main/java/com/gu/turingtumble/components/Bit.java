package com.gu.turingtumble.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.gu.turingtumble.utils.GameManager;


/**
 * Game component, Bit
 */
public class Bit implements GameComponents {


    // MODEL
    private Body bitBody;
    private RevoluteJoint revoluteJoint;
    private Vector2 bitModelOrigin;
    // Render
    private Sprite bitSprite;
    private Texture bitTexture;
    // Constants
    private static final float BIT_WIDTH = 65f;
    private static final float BIT_HEIGHT = 65f;


    public Bit(float x, float y, World world, Body slotBody, BodyEditorLoader loader) {
        createBit(x, y - 5, world, loader);
        createRevoluteJoint(x, y, world, slotBody);
        createSprite();

    }

    private void createBit(float pos_x, float pos_y, World world, BodyEditorLoader loader) {
        bitBody = GameManager.createBody(world, BodyDef.BodyType.DynamicBody, pos_x, pos_y, "Blue");

        // 2. Create a FixtureDef
        FixtureDef fd = new FixtureDef();
        fd.density = 0.4f;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        // 3. Create the body fixture automatically by using the loader.
        loader.attachFixture(bitBody, "Blue", fd, BIT_HEIGHT);
        bitModelOrigin = loader.getOrigin("Blue", BIT_HEIGHT).cpy();
    }

    private void createSprite() {
        bitTexture = new Texture(Gdx.files.internal("Blue.png"));
        bitTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bitSprite = new Sprite(bitTexture);
        bitSprite.setSize(BIT_WIDTH, BIT_WIDTH * bitSprite.getHeight() / bitSprite.getWidth());
        bitSprite.setOriginCenter();
    }

    private void createRevoluteJoint(float x, float y, World world, Body slotBody) {
        RevoluteJointDef jointDf = new RevoluteJointDef();
        // Create A（fixed point）
        jointDf.bodyA = slotBody;
        jointDf.localAnchorA.set(slotBody.getPosition().x, slotBody.getPosition().y);
        // Create B（bit body）
        jointDf.bodyB = bitBody;
        jointDf.localAnchorB.set(bitBody.getPosition().x, bitBody.getPosition().y);
        // Create Joint
        jointDf.initialize(slotBody, bitBody, slotBody.getPosition());
        // Set Date
        jointDf.lowerAngle = -0.3f * (float) Math.PI;
        jointDf.upperAngle = 0.3f * (float) Math.PI;
        jointDf.enableLimit = true;
        //Motor
        jointDf.enableMotor = false;
        jointDf.motorSpeed = 1.0f;  //Speed
        jointDf.maxMotorTorque = 10.0f;

        revoluteJoint = (RevoluteJoint) world.createJoint(jointDf);
    }


    @Override
    public void draw(SpriteBatch batch, float x, float y) {
        Vector2 position = bitBody.getPosition().sub(bitModelOrigin);
        bitSprite.setPosition(position.x, position.y);
        bitSprite.setRotation((float) Math.toDegrees(bitBody.getAngle()));
        bitSprite.draw(batch);
    }


    @Override
    public void update(float delta) {
        if (bitTexture != null) {
            bitTexture.dispose();
            bitTexture = null;
        }
    }

    @Override
    public Body getBody() {
        return bitBody;
    }

    @Override
    public void dispose() {
        if (revoluteJoint != null) {
            revoluteJoint.getBodyB().getWorld().destroyJoint(revoluteJoint);
        }
        if (bitTexture != null) {
            bitTexture.dispose();
            bitTexture = null;
        }
        if (bitSprite != null) {
            bitSprite = null;
        }
        if (bitBody != null) {
            bitBody.getWorld().destroyBody(bitBody);
        }
    }

}
