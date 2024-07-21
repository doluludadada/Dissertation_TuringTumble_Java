package com.gu.turingtumble.gamecomponents;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.gu.turingtumble.utils.GameConstant;
import com.gu.turingtumble.utils.GameManager;


public class Ramp implements GameComponents {
    private Texture texture;
    private Body body;
    private Vector2 position;

    public Ramp(float x, float y, World world) {
        texture = new Texture("assets/Green.png");
        body = GameManager.createBody(x, y, GameConstant.CELL_SIZE.get(), GameConstant.CELL_SIZE.get(), world);
        position = new Vector2(x, y);
    }

    @Override
    public void draw(SpriteBatch batch, float x, float y) {
        batch.begin();
        batch.draw(texture, x - GameConstant.CELL_SIZE.get() / 2, y - GameConstant.CELL_SIZE.get() / 2, GameConstant.CELL_SIZE.get(), GameConstant.CELL_SIZE.get());
        batch.end();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }
}
