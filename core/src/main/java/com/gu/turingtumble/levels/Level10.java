package com.gu.turingtumble.levels;

import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Level10 extends Level{
    public Level10() {
        super();
        Map<String, Integer> limits = new HashMap<>();
        limits.put("Ramp",22);
        setComponentLimits(limits);
    }

    @Override
    public void setAllowedBallStopper() {
        GameManager.getGameState().setAllowedBallStopper(0);
    }

    @Override
    public void basicComponent() {
        GameManager.setSelectedComponent("Bit");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(0, 8);
        GameManager.setSelectedComponent("Crossover");
        GameManager.addComponent(2, 6);
    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1));
    }
}
