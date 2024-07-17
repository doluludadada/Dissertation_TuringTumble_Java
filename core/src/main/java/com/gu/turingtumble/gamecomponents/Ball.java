//package com.gu.turingtumble.gamecomponents;
//
//
//
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.physics.box2d.*;
//import com.gu.turingtumble.utils.GameConstant;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//
//
//public class Ball implements GameComponents {
//    private Body body;
//    private Color ballColour;
//    private static final float RADIUS = GameConstant.CELL_SIZE.getValue() / 6f;
//    private ShapeRenderer shapeRenderer;
//
//
//    public Ball() {
//    }
//
//    public Ball(World world, Color colour, float x, float y) {
//
//
//        this.ballColour = colour;
//        this.shapeRenderer = new ShapeRenderer();
//
//
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(x, y);
//        body = world.createBody(bodyDef);
//
//
//        CircleShape shape = new CircleShape();      //define the circle
//        shape.setRadius(RADIUS);
//
//
//        FixtureDef fixtureDef = new FixtureDef();   //define the fixture
//        fixtureDef.shape = shape;
//        fixtureDef.density = 0.5f;
//        fixtureDef.friction = 0.4f;
//        fixtureDef.restitution = 0.6f;
//
//        body.createFixture(fixtureDef);
//        shape.dispose();
//    }
//
//    @Override
//    public void draw(SpriteBatch batch, float x, float y) {
//
//        batch.end();
//
//        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(ballColour);
//        shapeRenderer.circle(body.getPosition().x, body.getPosition().y, RADIUS);
//        shapeRenderer.end();
//
//        batch.begin();
//
//
//    }
//
//    public void dispose() {
//        shapeRenderer.dispose();
//    }
//
//    public Body getBody() {
//        return body;
//    }
//
//}
