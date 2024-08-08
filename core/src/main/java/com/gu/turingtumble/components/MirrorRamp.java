package com.gu.turingtumble.components;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.gu.turingtumble.utils.GameManager;


public class MirrorRamp extends Ramp implements GameComponents {

    public MirrorRamp(float x, float y, World world, Body slotBody, BodyEditorLoader loader) {
        super(x, y, world, slotBody, loader);
    }

    @Override
    protected void createRamp(float pos_x, float pos_y, World world, BodyEditorLoader loader) {
        rampBody = GameManager.createBody(world, BodyDef.BodyType.DynamicBody, pos_x, pos_y, "Green2");

        FixtureDef fd = new FixtureDef();
        fd.density = 2f;
        fd.friction = 10f;
        fd.restitution = 0f;

        loader.attachFixture(rampBody, "Green2", fd, RAMP_HEIGHT);
        rampModelOrigin = loader.getOrigin("Green2", RAMP_HEIGHT).cpy();
        rampBody.setUserData(this);
    }

    @Override
    protected void createRevoluteJoint(World world, Body slotBody) {
        RevoluteJointDef jointDf = new RevoluteJointDef();
        // Create A（fixed point）
        jointDf.bodyA = slotBody;
        jointDf.localAnchorA.set(slotBody.getPosition().x, slotBody.getPosition().y);
        // Create B（bit body）
        jointDf.bodyB = rampBody;
        jointDf.localAnchorB.set(rampBody.getPosition().x, rampBody.getPosition().y);
        // Create Joint
        jointDf.initialize(slotBody, rampBody, slotBody.getPosition());
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
    protected void createSprite() {
        rampTexture = new Texture(Gdx.files.internal("assets/Green2.png"));
        rampTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        rampSprite = new Sprite(rampTexture);
        rampSprite.setSize(RAMP_WIDTH, RAMP_WIDTH * rampSprite.getHeight() / rampSprite.getWidth());
        rampSprite.setOriginCenter();
    }
}


