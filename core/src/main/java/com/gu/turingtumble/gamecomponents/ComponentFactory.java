package com.gu.turingtumble.gamecomponents;

import com.gu.turingtumble.utils.GameManager;

public class ComponentFactory {
    public static GameComponents createComponent(String type, float x, float y) {
        switch (type) {
            case "Ramp":
                return new Ramp(x, y, GameManager.getWorld());
            case "Crossover":
                return new Crossover();
            case "Bit":
                return new Bit();
            case "Interceptor":
                return new Interceptor();
            case "Gear":
                return new Gear();
            case "GearBit":
                return new GearBit();
            default:
                throw new IllegalArgumentException("Unknown component type: " + type);
        }
    }
}

