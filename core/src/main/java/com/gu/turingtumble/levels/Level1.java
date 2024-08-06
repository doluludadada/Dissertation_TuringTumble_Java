package com.gu.turingtumble.levels;


import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

import java.util.Arrays;

public class Level1 implements Level {
    private static final int MAX_RAMP_COUNT = 4;
    private int currentRampCount = 0;
//    private static final int REQUIRED_BLUE_BALLS = 8;


    @Override
    public void initialise() {
        System.out.println("level1 initialized");
        System.out.println(LevelManager.getCurrentLevelNumber());

        setVictoryCondition();
        currentRampCount = 0;                               //when do "reset" it will become 0 again
        basicComponent();
    }

    @Override
    public void basicComponent() {
        System.out.println("test message putComponent");
        // Ramp
        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(2, 4);
        GameManager.addComponent(4, 4);
        GameManager.addComponent(6, 4);
        GameManager.addComponent(8, 4);

        // Mirror Ramp
        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(1, 5);

    }

    @Override
    public void reset(MainGame game) {
        GameManager.resetLevel();

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
