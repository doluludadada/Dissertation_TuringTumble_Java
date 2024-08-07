package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Level3 extends Level {

    public Level3() {
        super();
        Map<String, Integer> limits = new HashMap<>();
        limits.put("Ramp", 6);
        setComponentLimits(limits);
    }

    @Override
    public void reset(MainGame game) {
        GameManager.resetLevel();
    }

    @Override
    public void basicComponent() {
        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(7, 9);
        GameManager.addComponent(9, 9);

        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(0, 8);
        GameManager.addComponent(6, 10);
        GameManager.addComponent(8, 10);
    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(0, 1, 1, 1, 1, 1, 1, 1));
    }
}

