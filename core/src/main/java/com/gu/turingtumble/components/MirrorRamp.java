package com.gu.turingtumble.components;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.utils.GameManager;


public class MirrorRamp extends Ramp {

    public MirrorRamp(float x, float y, World world, Body slotBody, BodyEditorLoader loader) {
        super(x, y, world, slotBody, loader);
    }

    @Override
    protected void createRamp(float pos_x, float pos_y, World world, BodyEditorLoader loader) {
        rampBody = GameManager.createBody(world, BodyDef.BodyType.DynamicBody, pos_x, pos_y, "Green2");

        FixtureDef fd = new FixtureDef();
        fd.density = 2;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        loader.attachFixture(rampBody, "Green2", fd, RAMP_HEIGHT);
        rampModelOrigin = loader.getOrigin("Green2", RAMP_HEIGHT).cpy();
        rampBody.setUserData(this);
    }

    @Override
    protected void createSprite() {
        rampTexture = new Texture(Gdx.files.internal("assets/Green2.png"));
        rampTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        rampSprite = new Sprite(rampTexture);
        rampSprite.setSize(RAMP_WIDTH, RAMP_WIDTH * rampSprite.getHeight() / rampSprite.getWidth());
        rampSprite.setOriginCenter();
    }

    @Override
    protected void rotateRamp() {
        revoluteJoint.setLimits(-ROTATION, 0);
    }

    @Override
    protected void resetRamp() {
        revoluteJoint.setLimits(0, 0);
        isScheduledToReset = false;
        System.out.println("leave contact, count: " + contactBodies.size());
    }
}


