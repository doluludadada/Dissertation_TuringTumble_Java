package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;

public interface Level {

    void initialise();

    void reset(MainGame game);

    void basicComponent();

    boolean isComplete();

    boolean componentLimit(String componentType);

    int getLeftComponentCount(String componentType);

    void plusComponentCount(String componentType);

    void setVictoryCondition();

}
