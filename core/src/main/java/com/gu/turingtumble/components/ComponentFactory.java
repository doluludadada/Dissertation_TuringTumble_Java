package com.gu.turingtumble.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.utils.GameManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentFactory {
    private static BodyEditorLoader loader;
    private static Map<GameComponents, Body> componentSlotBodyMap = new HashMap<>();


    // 0. Create a loader for the file saved from the editor.
    static {
        loader = new BodyEditorLoader(Gdx.files.internal("assets/Green.json"));
    }

    public static GameComponents createComponent(String type, float x, float y) {
        System.out.println("Creating component of type: " + type + " at position: (" + x + ", " + y + ")");
        World world = GameManager.getWorld();
        Body slotBody = createSlotBody(x, y, world);
        GameComponents component = null;

        switch (type) {
            case "Ramp":
                component = new Ramp(x, y, world, slotBody, loader);
                break;
            case "MirrorRamp":
                component = new MirrorRamp(x, y, world, slotBody, loader);
                break;
            case "Crossover":
                component = new Crossover(x, y, world, loader);
                break;
            case "Bit":
                component = new Bit(x, y, world, slotBody, loader);
                break;
            case "Interceptor":
                component = new Interceptor(x, y, world, loader);
                break;
            case "Gear":
                component = new Gear();
                break;
            case "GearBit":
                component = new GearBit();
                break;
            default:
                throw new IllegalArgumentException("Unknown component type: " + type);
        }

        if (component != null) {
            componentSlotBodyMap.put(component, slotBody);
        }

        return component;
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



    public static Body getSlotBodyForComponent(GameComponents component) {
        return componentSlotBodyMap.get(component);
    }

    public static void removeSlotBodyForComponent(GameComponents component) {
        componentSlotBodyMap.remove(component);
    }


}

