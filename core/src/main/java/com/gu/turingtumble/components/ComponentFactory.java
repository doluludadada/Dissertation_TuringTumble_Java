package com.gu.turingtumble.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.utils.GameManager;


public class ComponentFactory {
    private static BodyEditorLoader loader;

    // 0. Create a loader for the file saved from the editor.
    static {
        loader = new BodyEditorLoader(Gdx.files.internal("assets/Green.json"));
    }

    public static GameComponents createComponent(String type, float x, float y) {
        System.out.println("Creating component of type: " + type + " at position: (" + x + ", " + y + ")");
        World world = GameManager.getWorld();

        Body slotBody = GameManager.getSlotBody(new Vector2(x, y));
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

        return component;
    }

}

