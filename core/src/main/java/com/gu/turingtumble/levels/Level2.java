package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

import java.util.Arrays;

public class Level2 implements Level {
    private static final int MAX_RAMP_COUNT = 5;
    private static final int REQUIRED_BLUE_BALLS = 8;
    private int currentRampCount = 0;


    @Override
    public void initialise() {
        setVictoryCondition();
        basicComponent();
        currentRampCount = 0;
    }

    @Override
    public void reset(MainGame game) {

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
    public boolean componentLimit(String componentType) {
        if ("Ramp".equals(componentType)) {
            return currentRampCount < MAX_RAMP_COUNT;
        }
        return true;
    }

    @Override
    public int getLeftComponentCount(String componentType) {
        if ("Ramp".equals(componentType)) {
            return MAX_RAMP_COUNT - currentRampCount;
        }
        return 0;
    }

    @Override
    public void plusComponentCount(String componentType) {
        if ("Ramp".equals(componentType)) {
            currentRampCount++;
        }
    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    }
}
