package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

public class Level3 implements Level {
    private static final int MAX_RAMP_COUNT = 6;
    private int currentRampCount = 0;
    private static final int REQUIRED_BLUE_BALLS = 1;
    private static final int REQUIRED_RED_BALLS = 7;

    @Override
    public void initialise() {
        Level.super.initialise();
    }

    @Override
    public void reset(MainGame game) {

    }

    @Override
    public void basicComponent() {
        // Ramp
        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(7, 9);
        GameManager.addComponent(9, 9);


        // Mirror Ramp
        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(0, 8);
        GameManager.addComponent(6, 10);
        GameManager.addComponent(8, 10);
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
        GameManager.getGameState().setGoalBlueBall(REQUIRED_BLUE_BALLS);
        GameManager.getGameState().setGoalRedBall(REQUIRED_RED_BALLS);
    }
}
