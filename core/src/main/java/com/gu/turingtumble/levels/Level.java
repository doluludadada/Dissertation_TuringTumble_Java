package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

public interface Level {

    default void initialise(){
        System.out.println("level initialized");
        basicComponent();
        setVictoryCondition();
    };

    void reset(MainGame game);

    void basicComponent();

    default boolean isComplete() {
        return GameManager.getGameState().getGoalBlueBall() == 0 && GameManager.getGameState().getGoalRedBall() == 0;
    }

    boolean componentLimit(String componentType);

    int getLeftComponentCount(String componentType);

    void plusComponentCount(String componentType);

    default void setVictoryCondition() {
        GameManager.getGameState().setGoalBlueBall(0);
        GameManager.getGameState().setGoalRedBall(0);
    }

}
