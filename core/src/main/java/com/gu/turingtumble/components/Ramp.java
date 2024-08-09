package com.gu.turingtumble.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Timer;
import com.gu.turingtumble.utils.GameManager;

import java.util.HashSet;
import java.util.Set;


/**
 * Game component, Ramp
 */
public class Ramp implements GameComponents {
    //    MODEL
    protected Body rampBody;
    protected Vector2 rampModelOrigin;
    protected RevoluteJoint revoluteJoint;
    //    Render
    protected Sprite rampSprite;
    protected Texture rampTexture;
    //    Data
    protected static final float RAMP_WIDTH = 60f;
    protected static final float RAMP_HEIGHT = 60f;
    protected static final float ROTATION = (float) Math.toRadians(80);
    //    Functional
    protected Set<Body> contactBodies = new HashSet<>();
    protected boolean isScheduledToReset = false;


    public Ramp(float x, float y, World world, Body slotBody, BodyEditorLoader loader) {
        createRamp(x, y, world, loader);
        createSprite();
        createRevoluteJoint(world, slotBody);
    }

    protected void createRamp(float pos_x, float pos_y, World world, BodyEditorLoader loader) {
        rampBody = GameManager.createBody(world, BodyDef.BodyType.DynamicBody, pos_x, pos_y, "Green");

        // 2. Create a FixtureDef
        FixtureDef fd = new FixtureDef();
        fd.density = 0.5f;
        fd.friction = 0f;
        fd.restitution = 0f;
//        fd.isSensor = true;

        // 3. Create the body fixture automatically by using the loader.
        loader.attachFixture(rampBody, "Green", fd, RAMP_HEIGHT);
        rampModelOrigin = loader.getOrigin("Green", RAMP_HEIGHT).cpy();
        rampBody.setUserData(this);
    }

    protected void createSprite() {
        rampTexture = new Texture(Gdx.files.internal("Green.png"));
        rampTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        rampSprite = new Sprite(rampTexture);
        rampSprite.setSize(RAMP_WIDTH, RAMP_WIDTH * rampSprite.getHeight() / rampSprite.getWidth());
        rampSprite.setOriginCenter();
    }

    protected void createRevoluteJoint(World world, Body slotBody) {
        RevoluteJointDef jointDf = new RevoluteJointDef();
        // Create A（fixed point）
        jointDf.bodyA = slotBody;
        jointDf.localAnchorA.set(slotBody.getPosition().x, slotBody.getPosition().y);
        // Create B（ramp Body）
        jointDf.bodyB = rampBody;
        jointDf.localAnchorB.set(rampBody.getPosition().x, rampBody.getPosition().y);
        // Create Joint
        jointDf.initialize(rampBody, slotBody, slotBody.getPosition());
        // Set Data
        jointDf.enableLimit = true;
        jointDf.lowerAngle = 0;
        jointDf.upperAngle = (float) Math.toRadians(25);
        //Motor
        jointDf.enableMotor = false;
        jointDf.motorSpeed = 1.0f;
        jointDf.maxMotorTorque = 3.0f;

        revoluteJoint = (RevoluteJoint) world.createJoint(jointDf);
    }

    @Override
    public void draw(SpriteBatch batch, float x, float y) {
        Vector2 position = rampBody.getPosition().sub(rampModelOrigin);
        rampSprite.setPosition(position.x, position.y);
        rampSprite.setRotation((float) Math.toDegrees(rampBody.getAngle()));
        rampSprite.draw(batch);
    }


    @Override
    public void update(float delta) {

    }

    @Override
    public Body getBody() {
        return rampBody;
    }


    public void beginContact(Body body) {
        contactBodies.add(body);
        if (contactBodies.size() == 1) {
            rotateRamp();
            System.out.println("being contact, count: " + contactBodies.size());
        }
        isScheduledToReset = false;
    }

    public void endContact(Body body) {
        contactBodies.remove(body);
        if (contactBodies.isEmpty() && !isScheduledToReset) {
            scheduleResetRamp();
        }
        System.out.println("end contact, count: " + contactBodies.size());
    }

    protected void rotateRamp() {
        if (revoluteJoint != null) {
            float lowerLimit = 0;
            float upperLimit = ROTATION;
            if (!Float.isNaN(lowerLimit) && !Float.isNaN(upperLimit) && lowerLimit <= upperLimit) {
                revoluteJoint.setLimits(lowerLimit, upperLimit);
            } else {
                System.err.println("Invalid limits for revolute joint: " + lowerLimit + ", " + upperLimit);
            }
        }
    }

    protected void resetRamp() {
        if (revoluteJoint != null) {
            float lowerLimit = 0;
            float upperLimit = (float) Math.toRadians(25);
            if (!Float.isNaN(lowerLimit) && !Float.isNaN(upperLimit) && lowerLimit <= upperLimit) {
                revoluteJoint.setLimits(lowerLimit, upperLimit);
            } else {
                System.err.println("Invalid limits for revolute joint: " + lowerLimit + ", " + upperLimit);
            }
            isScheduledToReset = false;
        }
    }


    protected void scheduleResetRamp() {
        isScheduledToReset = true;
        Timer.schedule(new Timer.Task() {
            public void run() {
                if (contactBodies.isEmpty()) {
                    resetRamp();
                } else {
                    isScheduledToReset = false;
                }
            }
        }, 2f);
    }

    @Override
    public void dispose() {
        if (revoluteJoint != null) {
            revoluteJoint.getBodyB().getWorld().destroyJoint(revoluteJoint);
            revoluteJoint = null;
        }
        if (rampTexture != null) {
            rampTexture.dispose();
            rampTexture = null;
        }
        if (rampBody != null) {
            rampBody.getWorld().destroyBody(rampBody);
            rampBody = null;
        }
        if (rampSprite != null) {
            rampSprite = null;
        }
    }

}




