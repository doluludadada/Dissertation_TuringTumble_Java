package com.gu.turingtumble.utils;

/**
 *
 */
public enum GameConstant {
    SLOT_NUMBER_WIDTH(11),                                 //number of slot widths
    SLOT_NUMBER_HEIGHT(11),                                //number of slot height
    CELL_SIZE(60),                                   //gap size
    RED_BALL_COUNT(5),                               //
    BLUE_BALL_COUNT(5),                              //

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

    public int getValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

}
