package com.gu.turingtumble.levels;

import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Level7 extends Level {
    public Level7() {
        super();
        Map<String, Integer> limits = new HashMap<>();
        limits.put("Ramp", 6);
        setComponentLimits(limits);
    }

    @Override
    public void setAllowedBallStopper() {
        GameState.getInstance().setAllowedBallStopper(0);
    }

    @Override
    public void basicComponent() {
        GameManager.setSelectedComponent("Crossover");
        GameManager.addComponent(3, 4);
        GameManager.addComponent(3, 6);
        GameManager.addComponent(5, 1);
        GameManager.addComponent(6, 5);
        GameManager.addComponent(8, 3);
        GameManager.addComponent(9, 4);

    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    }
}
