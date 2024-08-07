package com.gu.turingtumble.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;


public class Crossover implements GameComponents {

    //    MODEL
    private Body crossoverBody;
    private Vector2 crossoverOrigin;
    //    Render
    private Sprite crossoverSprite;
    //    Data
    private static final float CROSSOVER_WIDTH = 60f;
    private static final float CROSSOVER_HEIGHT = 60f;


    public Crossover(float pos_x, float pos_y, World world, BodyEditorLoader loader) {
        createCrossover(pos_x - 30, pos_y - 23, world, loader);
        createSprite();
    }

    private void createCrossover(float pos_x, float pos_y, World world, BodyEditorLoader loader) {
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
        crossoverBody = world.createBody(bd);
        crossoverBody.setUserData(this);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(crossoverBody, "Orange", fd, CROSSOVER_HEIGHT);
        crossoverOrigin = loader.getOrigin("Orange", CROSSOVER_HEIGHT).cpy();
    }

    private void createSprite() {

        Texture crossoverTexture = new Texture(Gdx.files.internal("assets/Orange.png"));
        crossoverTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        crossoverSprite = new Sprite(crossoverTexture);
        crossoverSprite.setSize(CROSSOVER_WIDTH, CROSSOVER_WIDTH * crossoverSprite.getHeight() / crossoverSprite.getWidth());

    }

    @Override
    public void draw(SpriteBatch batch, float x, float y) {
        Vector2 position = crossoverBody.getPosition().sub(crossoverOrigin);
        crossoverSprite.setPosition(position.x, position.y);
        crossoverSprite.draw(batch);
//        System.out.println("crossover origin: " + crossoverOrigin.x + ", " + crossoverOrigin.y);
    }


    @Override
    public void update(float delta) {

    }

    @Override
    public Body getBody() {
        return crossoverBody;
    }
}
