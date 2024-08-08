package com.gu.turingtumble.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;


public interface GameComponents {
    void draw(SpriteBatch batch, float x, float y);
    void update(float delta);
    Body getBody();
    void dispose();
}
