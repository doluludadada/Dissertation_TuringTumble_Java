package com.gu.turingtumble.gamecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.utils.GameManager;

public class ComponentFactory {
    private static BodyEditorLoader loader;

    // 0. Create a loader for the file saved from the editor.
    static {
        loader = new BodyEditorLoader(Gdx.files.internal("assets/Green.json"));
    }

    public static GameComponents createComponent(String type, float x, float y) {
        World world = GameManager.getWorld();
        Body slotBody = createSlotBody(x, y, world);

        if (GameManager.isMirrorSelected()) {
            switch (type) {
                case "Ramp":
                    return new MirrorRamp(x, y, world, slotBody, loader);
                default:
                    throw new IllegalArgumentException("Unsupported component type for mirror: " + type);
            }
        } else {
            switch (type) {
                case "Ramp":
                    return new Ramp(x, y, world, slotBody, loader);
                case "Crossover":
                    return new Crossover(x, y, world, loader);
                case "Bit":
                    return new Bit(x, y, world, slotBody, loader);
                case "Interceptor":
                    return new Interceptor(x, y, world, loader);
                case "Gear":
                    return new Gear();
                case "GearBit":
                    return new GearBit();
                default:
                    throw new IllegalArgumentException("Unknown component type: " + type);
            }
        }
    }


    private static Body createSlotBody(float x, float y, World world) {
        BodyDef slotDef = new BodyDef();
        slotDef.type = BodyDef.BodyType.KinematicBody;
        slotDef.position.set(x, y);
        Body slotBody = world.createBody(slotDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        slotBody.createFixture(fixtureDef);
        shape.dispose();
        return slotBody;
    }


}

