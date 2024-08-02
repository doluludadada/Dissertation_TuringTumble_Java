package com.gu.turingtumble.levels;


import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

public class Level1 implements Level {
    private static final int MAX_RAMP_COUNT = 4;
    private int currentRampCount = 0;
    private static final int REQUIRED_BLUE_BALLS = 8;


    @Override
    public void initialise() {
        System.out.println("level1 initialized");
        basicComponent();
        currentRampCount = 0;                           //when do "reset" it will become 0 again
        setVictoryCondition();
    }

    @Override
    public void basicComponent() {
        System.out.println("test message putComponent");
        // 固定生成的 Ramp
        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(2, 4);
        GameManager.addComponent(4, 4);
        GameManager.addComponent(6, 4);
        GameManager.addComponent(8, 4);

        // 鏡像 Ramp
        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(1, 5);

    }

    @Override
    public void reset(MainGame game) {
        GameManager.resetLevel();

    }

    @Override
    public boolean isComplete() {
        return GameManager.getGameState().getGoalBlueBall() == 0;
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
        GameManager.getGameState().setGoalRedBall(0);
//        GameManager.getGameState().updateVictoryConditionUI();
    }


}
