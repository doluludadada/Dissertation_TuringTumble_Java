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

public class Interceptor implements GameComponents {
    //    MODEL
    private Body interceptorBody;
    private Vector2 interceptorOrigin;
    //    Render
    private Sprite interceptorSprite;
    //    Data
    private static final float INTERCEPTOR_WIDTH = 70f;
    private static final float INTERCEPTOR_HEIGHT = 70f;


    public Interceptor(float pos_x, float pos_y, World world, BodyEditorLoader loader) {
        createInterceptor(pos_x - 35, pos_y - 10, world, loader);
        createSprite();
    }

    private void createInterceptor(float pos_x, float pos_y, World world, BodyEditorLoader loader) {
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
        interceptorBody = world.createBody(bd);
        interceptorBody.setUserData(this);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(interceptorBody, "black", fd, INTERCEPTOR_HEIGHT);
        interceptorOrigin = loader.getOrigin("black", INTERCEPTOR_HEIGHT).cpy();
    }

    private void createSprite() {
        Texture crossoverTexture = new Texture(Gdx.files.internal("black.png"));
        crossoverTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        interceptorSprite = new Sprite(crossoverTexture);
        interceptorSprite.setSize(INTERCEPTOR_WIDTH, INTERCEPTOR_WIDTH * interceptorSprite.getHeight() / interceptorSprite.getWidth());
    }

    @Override
    public void draw(SpriteBatch batch, float x, float y) {
        Vector2 position = interceptorBody.getPosition().sub(interceptorOrigin);
        interceptorSprite.setPosition(position.x, position.y);
        interceptorSprite.draw(batch);
        System.out.println("crossover origin: " + interceptorOrigin.x + ", " + interceptorOrigin.y);
    }


    @Override
    public void update(float delta) {

    }

    @Override
    public Body getBody() {
        return interceptorBody;
    }

    @Override
    public void dispose() {
        if (interceptorSprite != null) {
            interceptorSprite = null;
        }
    }
}
