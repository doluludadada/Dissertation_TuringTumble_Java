package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Level2 extends Level {

    public Level2() {
        super();
        Map<String, Integer> limits = new HashMap<>();
        limits.put("Ramp", 5);
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
        GameManager.addComponent(1, 5);
        GameManager.addComponent(2, 6);
        GameManager.addComponent(3, 7);
        GameManager.addComponent(4, 8);
        GameManager.addComponent(5, 9);
    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    }
}

