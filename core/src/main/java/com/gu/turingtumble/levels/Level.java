package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

import java.util.HashMap;
import java.util.Map;


public abstract class Level {
    private static final Map<String, Integer> COMPONENT_LIMITS = new HashMap<>();
    private final Map<String, Integer> currentComponentCounts = new HashMap<>();


    protected Level() {
        resetComponentCounts();
    }

    protected static void setComponentLimits(Map<String, Integer> limits) {
        COMPONENT_LIMITS.clear();
        COMPONENT_LIMITS.putAll(limits);
    }

    protected void resetComponentCounts() {
        for (String componentType : COMPONENT_LIMITS.keySet()) {
            currentComponentCounts.put(componentType, 0);
        }
    }

    public void initialise() {
        System.out.println("level initialized");
        GameState.getInstance().reset();
        GameManager.setBlueBallNum(8);
        GameManager.setRedBallNum(8);
        setVictoryCondition();
        resetComponentCounts();
        basicComponent();
        setAllowedBallStopper();
    }

    protected abstract void setAllowedBallStopper();

    protected void reset(MainGame game){
        GameManager.resetLevel();
    }

    protected abstract void basicComponent();

    public boolean isComplete() {
        return GameState.getInstance().isComplete();
    }

    public boolean componentLimit(String componentType) {
        return currentComponentCounts.getOrDefault(componentType, 0) < COMPONENT_LIMITS.getOrDefault(componentType, 0);
    }

    public int getLeftComponentCount(String componentType) {
        return COMPONENT_LIMITS.getOrDefault(componentType, 0) - currentComponentCounts.getOrDefault(componentType, 0);
    }

    public void plusComponentCount(String componentType) {
        currentComponentCounts.put(componentType, currentComponentCounts.getOrDefault(componentType, 0) + 1);
    }

    protected abstract void setVictoryCondition();

    public void dispose() {
        GameManager.clearComponents();
//        GameManager.clearBalls();
//        GameManager.clearBallStoppersAndSensor();
//        GameManager.clearAll();
    }


}

