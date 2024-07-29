package com.gu.turingtumble.levels;

public interface Level {
    void initialize();
    void reset();
    void onComplete();
}
