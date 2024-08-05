package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

public class Level4 implements Level{

    private static final int MAX_RAMP_COUNT = 13;
    private int currentRampCount = 0;
    private static final int REQUIRED_BLUE_BALLS = 7;
    private static final int REQUIRED_RED_BALLS = 1;

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
        return false;
    }

    @Override
    public int getLeftComponentCount(String componentType) {
        return 0;
    }

    @Override
    public void plusComponentCount(String componentType) {

    }

    @Override
    public void setVictoryCondition() {
        Level.super.setVictoryCondition();
    }
}
