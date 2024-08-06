package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

import java.util.Arrays;

public class Level4 implements Level {

    private static final int MAX_RAMP_COUNT = 13;
    private int currentRampCount = 0;

    @Override
    public void initialise() {
        setVictoryCondition();
        currentRampCount = 0;
        basicComponent();
    }

    @Override
    public void reset(MainGame game) {

    }

    @Override
    public void basicComponent() {
        // Ramp
        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(0, 8);
        GameManager.addComponent(1, 9);
        GameManager.addComponent(2, 10);


        // Mirror Ramp
        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(1, 3);
        GameManager.addComponent(2, 2);
    }

    @Override
    public boolean isComplete() {
        return Level.super.isComplete();
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
        GameManager.getGameState().setRequireGoal(Arrays.asList(1, 0, 0, 0, 0, 0, 0, 0));
    }
}
