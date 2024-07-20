package com.gu.turingtumble.utils;

/**
 *
 */
public enum GameConstant {
    SLOT_NUMBER_WIDTH(11),                                          // number of slot widths
    SLOT_NUMBER_HEIGHT(11),                                         // number of slot height
    CELL_SIZE(60),                                                  // gap size
    RED_BALL_COUNT(5),                                              //
    BLUE_BALL_COUNT(5),                                             //
    GAME_WIDTH(800),                                                //
    UI_WIDTH(400),                                                  //
    WINDOW_WIDTH(GAME_WIDTH.get() + UI_WIDTH.get()),                //
    WINDOW_HEIGHT(980),                                             //
    ;

    private final int intValue;
    private final double doubleValue;

    GameConstant(int value) {
        this.intValue = value;
        this.doubleValue = value;
    }

    GameConstant(double value) {
        this.intValue = 0;
        this.doubleValue = value;
    }

    public int get() {
        return intValue;
    }

    public double getDouble() {
        return doubleValue;
    }

}
