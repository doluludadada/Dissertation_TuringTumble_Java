package com.gu.turingtumble.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface GameComponents {
    void draw(SpriteBatch batch, float x, float y);
    void update(float delta);
}
