package com.gu.turingtumble.gamecomponents;

import com.badlogic.gdx.Gdx;
import com.gu.turingtumble.utils.GameManager;

public class ComponentFactory {
    private static BodyEditorLoader loader;

    // 0. Create a loader for the file saved from the editor.
    static {
        loader = new BodyEditorLoader(Gdx.files.internal("assets/Green.json"));
    }

    public static GameComponents createComponent(String type, float x, float y) {
        switch (type) {
            case "Ramp":
                return new Ramp(x, y, GameManager.getWorld(), loader);
            case "Crossover":
                return new Crossover(x, y, GameManager.getWorld(), loader);
            case "Bit":
                return new Bit();
            case "Interceptor":
                return new Interceptor(x, y, GameManager.getWorld(), loader);
            case "Gear":
                return new Gear();
            case "GearBit":
                return new GearBit();
            default:
                throw new IllegalArgumentException("Unknown component type: " + type);
        }
    }

}

