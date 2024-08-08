package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Level5 extends Level {
    public Level5() {
        super();
        Map<String, Integer> limits = new HashMap<>();
        limits.put("Ramp", 9);
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
        GameManager.addComponent(3, 7);
        GameManager.addComponent(5, 7);
        GameManager.addComponent(7, 7);
        GameManager.addComponent(9, 7);

        GameManager.setSelectedComponent("Crossover");
        GameManager.addComponent(2, 6);


        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(4, 8);
        GameManager.addComponent(6, 8);
        GameManager.addComponent(8, 8);
    }

    @Override
    public void setAllowedBallStopper() {
        GameState.getInstance().setAllowedBallStopper(0);
    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1));

    }
}
