package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Level4 extends Level {

    public Level4() {
        super();
        Map<String, Integer> limits = new HashMap<>();
        limits.put("Ramp", 13);
        setComponentLimits(limits);
    }

    @Override
    public void reset(MainGame game) {
        GameManager.resetLevel();
    }

    @Override
    public void basicComponent() {
        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(0, 8);
        GameManager.addComponent(1, 9);
        GameManager.addComponent(2, 10);

        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(1, 3);
        GameManager.addComponent(2, 2);
    }

    @Override
    public void setAllowedBallStopper() {
        GameState.getInstance().setAllowedBallStopper(1);
    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(1, 0, 0, 0, 0, 0, 0, 0));
    }
}

