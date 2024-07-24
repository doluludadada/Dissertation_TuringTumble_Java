package com.gu.turingtumble.gamecomponents;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public interface GameComponents {
    void draw(SpriteBatch batch, float x, float y);
    Vector2 getPosition();
    void update(float delta);
}
