package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

public interface Level {

    default void initialise(){
        System.out.println("level initialized");
        basicComponent();
        setVictoryCondition();
    };

    void reset(MainGame game);

    void basicComponent();

    default boolean isComplete() {
        return GameState.isComplete();
    }

    boolean componentLimit(String componentType);

    int getLeftComponentCount(String componentType);

    void plusComponentCount(String componentType);

    default void setVictoryCondition() {
    }

}
