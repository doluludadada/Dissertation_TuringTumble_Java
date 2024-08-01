package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;

public interface Level {
    void initialize();
    void reset(MainGame game);
    void onComplete();
}
