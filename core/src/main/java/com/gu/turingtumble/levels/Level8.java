package com.gu.turingtumble.levels;


import com.gu.turingtumble.utils.GameManager;

import java.util.Arrays;
import java.util.HashMap;

public class Level8 extends Level {
    public Level8() {
        super();
        HashMap<String, Integer> obj = new HashMap<>();
        obj.put("Ramp", 14);
        setComponentLimits(obj);
    }

    @Override
    public void setAllowedBallStopper() {
        GameManager.getGameState().setAllowedBallStopper(0);
    }

    @Override
    public void basicComponent() {
        GameManager.setSelectedComponent("Bit");
        GameManager.addComponent(2, 6);

        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(1, 5);

        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(0, 8);
        GameManager.addComponent(1, 7);
    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1));
    }
}
