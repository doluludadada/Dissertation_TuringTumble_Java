package com.gu.turingtumble.levels;

import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Level6 extends Level {
    public Level6() {
        super();
        Map<String, Integer> limits = new HashMap<>();
        limits.put("Ramp", 2);
        limits.put("Crossover", 5);
        setComponentLimits(limits);
    }

    @Override
    public void setAllowedBallStopper() {
        GameState.getInstance().setAllowedBallStopper(0);
    }

    @Override
    public void basicComponent() {
        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(1, 5);
        GameManager.addComponent(3, 5);
        GameManager.addComponent(5, 5);
        GameManager.addComponent(7, 5);
        GameManager.addComponent(9, 5);


        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(1, 7);
        GameManager.addComponent(3, 7);
        GameManager.addComponent(5, 7);
        GameManager.addComponent(7, 7);
        GameManager.addComponent(9, 7);

    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1));
    }
}
